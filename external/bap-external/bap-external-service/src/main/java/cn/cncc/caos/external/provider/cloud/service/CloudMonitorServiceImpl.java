package cn.cncc.caos.external.provider.cloud.service;

import cn.cncc.caos.external.client.cloud.api.ICloudAuthService;
import cn.cncc.caos.external.client.cloud.api.ICloudMonitorService;
import cn.cncc.caos.external.client.cloud.dto.TokenCacheItem;
import cn.cncc.caos.external.client.cloud.vo.SeriesDataParamVO;
import cn.cncc.mojito.rpc.common.web.HttpStatusCode;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author wb_zyang
 * @Description 云平台监控对接服务实现
 * @date 2024/11/28
 * @since v1.0.0
 */
@Slf4j
@Service
@MojitoSchema(schemaId = "inner_api/cloudMonitorService")
public class CloudMonitorServiceImpl implements ICloudMonitorService {

    @Autowired
    private ICloudAuthService cloudAuthService;

    @Autowired
    private RestTemplate httpsRestTemplate;

    @Value("#{${cloud.base-url}}")
    Map<String, String> cloudBaseUrl;

    @Value("${cloud.endpoint-url}")
    String endPointUrl;

    /**
     * 查询资源实力信息
     */
    private static final String QUERY_RESOURCE_INSTANCE = "/rest/cmdb/v1/instances/%s";

    /**
     * V2版本的时间序列查询接口
     */
    private static final String QUERY_TIME_SERIES = "/v2/%s/series";

    /**
     * V2版本时间序列数据样本
     */
    private static final String QUERY_PERFORMANCE_DATA = "/v2/%s/samples";

    @Override
    public List<LinkedHashMap> getResourceInstance(String authId, String resourceType, Map<String, Object> conditionMap) {
        String token = null;
        try {
            if (cloudAuthService.isValid(authId)) {
                TokenCacheItem tokenItem = cloudAuthService.getToken(authId);
                token = tokenItem.getHuaweitoken();
                HttpEntity httpEntry = getHttpEntity(token);
                String condition = "{\"constraint\":[{\"simple\":{\"name\":\"%s\",\"value\":\"%s\",\"operator\":\"equal\"}}]}";
                condition = String.format(condition, conditionMap.get("name"), conditionMap.get("value"));

                String url = cloudBaseUrl.get(authId) + String.format(QUERY_RESOURCE_INSTANCE, resourceType) + "?condition={condition}";

                Map paramMap = new HashMap<>();
                paramMap.put("condition", condition);

                ResponseEntity<Map> response = httpsRestTemplate.exchange(url, HttpMethod.GET, httpEntry, Map.class, paramMap);
                Map bodyMap = response.getBody();
                return (List<LinkedHashMap>) bodyMap.get("objList");
            } else {
                log.error("Exception occurred while obtaining valid token");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.error("查询对象实例异常. ", e);
        }

        return Collections.emptyList();
    }

    @Override
    public List<String> getTimeSeries(String authId, String resourceType, String projectId, Map<String, Object> conditionMap) {
        String token = getToken(authId);
        if (token == null) {
            return Collections.emptyList();
        }

        String seriesUrl = endPointUrl + String.format(QUERY_TIME_SERIES, projectId);
        List<Map<String, String>> dimensions = new ArrayList<>();
        for (Map.Entry<String, Object> entry : conditionMap.entrySet()) {
            Map<String, String> dimensionMap = new HashMap<>();
            dimensionMap.put("name", entry.getKey());
            dimensionMap.put("value", (String) entry.getValue());
            dimensions.add(dimensionMap);
        }

        Map<String, Object> seriesMap = new HashMap<>();
        if ("CLOUD_CCE_POD".equals(resourceType)) {
            seriesMap.put("namespace", "PAAS.CONTAINER");
        } else if ("CLOUD_CCE_NODE".equals(resourceType)) {
            seriesMap.put("namespace", "PAAS.NODE");
        }

        seriesMap.put("dimensions", dimensions);

        List<Map> seriesMapList = new ArrayList<>();
        seriesMapList.add(seriesMap);

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("series", seriesMapList);

        try {
            HttpEntity httpEntity = getHttpEntity(bodyMap, token);
            Map responseBodyMap = doHttpRequest(httpEntity, seriesUrl, HttpMethod.POST);
            List<Map<String, Object>> seriesList = (List<Map<String, Object>>) responseBodyMap.get("series");
            List<String> metricNameList = new ArrayList<>();
            for (Map<String, Object> series : seriesList) {
                metricNameList.add((String) series.get("metric_name"));
            }
            return metricNameList;
        } catch (Exception e) {
            log.error("查询时间序列异常.", e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<LinkedHashMap> getTimeSeriesData(String authId, String resourceType, String projectId, List<String> metricList,
                                                 Map<String, Object> objectAttribute, SeriesDataParamVO paramVO) {
        String token = getToken(authId);
        if (token == null) {
            return null;
        }

        String url = endPointUrl + String.format(QUERY_PERFORMANCE_DATA, projectId);
        List<Map> metricsList = new ArrayList<>();
        for (String metricName : metricList) {
            Map<String, Object> metricMap = new HashMap<>();
            if ("CLOUD_CCE_POD".equals(resourceType)) {
                metricMap.put("namespace", "PAAS.CONTAINER");
            } else if ("CLOUD_CCE_NODE".equals(resourceType)) {
                metricMap.put("namespace", "PAAS.NODE");
            }
            metricMap.put("metric_name", metricName);

            List<Map<String, String>> dimensions = new ArrayList<>();
            for (Map.Entry<String, Object> entry : objectAttribute.entrySet()) {
                Map<String, String> dimensionMap = new HashMap<>();
                dimensionMap.put("name", entry.getKey());
                dimensionMap.put("value", (String) entry.getValue());
                dimensions.add(dimensionMap);
            }
            metricMap.put("dimensions", dimensions);
            metricsList.add(metricMap);
        }
        paramVO.setSamples(metricsList);

        try {
            HttpEntity httpEntity = getHttpEntity(paramVO, token);
            ResponseEntity<Map> response = httpsRestTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
            return (List<LinkedHashMap>) response.getBody().get("samples");
        } catch (Exception e) {
            log.error("查询指标调用接口失败.", e);
        }
        return Collections.emptyList();
    }

    private String getToken(String authId) {
        if (cloudAuthService.isValid(authId)) {
            TokenCacheItem tokenItem = cloudAuthService.getToken(authId);
            return tokenItem.getHuaweitoken();
        } else {
            log.error("Exception occurred while obtaining valid token");
            return null;
        }
    }


    private Map doHttpRequest(HttpEntity httpEntity, String url, HttpMethod httpMethod) throws Exception {
        ResponseEntity<Map> response = null;
        try {
            response = httpsRestTemplate.exchange(url, httpMethod, httpEntity, Map.class);
        } catch (RestClientException e) {
            log.error("获取token错!", e);
            throw e;
        }

        if (response.getStatusCode().value() != HttpStatusCode.HTTP_OK) {
            throw new Exception("调用华为查询接口返回异常. url: " + url);
        }
        return response.getBody();
    }

    private HttpEntity getHttpEntity(String token) {
        HttpHeaders HttpHeaders = new HttpHeaders();
        HttpHeaders.add("Content-Type", "application/json");
        HttpHeaders.add("Accept", "application/json");
        if (token != null && !token.isEmpty()) {
            HttpHeaders.add("X-Auth-Token", token);
        }
        return new HttpEntity(HttpHeaders);
    }

    private HttpEntity getHttpEntity(Object body, String token) {
        HttpHeaders HttpHeaders = new HttpHeaders();
        HttpHeaders.add("Content-Type", "application/json");
        HttpHeaders.add("Accept", "application/json");
        if (token != null && !token.isEmpty()) {
            HttpHeaders.add("X-Auth-Token", token);
        }
        return new HttpEntity(body, HttpHeaders);
    }
}
