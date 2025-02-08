package cn.cncc.caos.external.provider.cloud;

import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.external.client.cloud.api.ICloudMonitorService;
import cn.cncc.caos.external.client.cloud.vo.SeriesDataParamVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.Assert.assertEquals;

@Slf4j
public class CloudMonitorServiceTest extends BaseTest {

    @Autowired
    private ICloudMonitorService serviceMonitorService;

    @Test
    public void testGetResourceInstanceForPod() throws Exception {
        String authId = "huawei_beijing_opm_0";
        String resourceType = "CLOUD_CCE_POD";
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("name", "name");
        conditionMap.put("value", "consumer-mojito-pqkv2a-79ddbb4c99-vsnst");
        List<LinkedHashMap> resourceDetailList = serviceMonitorService.getResourceInstance(authId, resourceType, conditionMap);
        log.warn("result:{}", JacksonUtil.objToJson(resourceDetailList));
        assertEquals(1, resourceDetailList.size());
        LinkedHashMap resourceDetail = resourceDetailList.get(0);
        assertEquals("4A30040B9BC136EDBCBC06EBC8C5EBFE", resourceDetail.get("id"));
        assertEquals("63CFEA9C2F393FF0A96F77CAFB3E7648", resourceDetail.get("ref_nodeId"));
    }

    @Test
    public void testGetResourceInstanceForNode() throws Exception {
        String authId = "huawei_beijing_opm_0";
        String resourceType = "CLOUD_CCE_NODE";
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("name", "id");
        conditionMap.put("value", "63CFEA9C2F393FF0A96F77CAFB3E7648");
        List<LinkedHashMap> resourceDetailList = serviceMonitorService.getResourceInstance(authId, resourceType, conditionMap);
        log.warn("result:{}", JacksonUtil.objToJson(resourceDetailList));
        LinkedHashMap resourceDetail = resourceDetailList.get(0);
        assertEquals("63CFEA9C2F393FF0A96F77CAFB3E7648", resourceDetail.get("id"));
        assertEquals("pub-cce-2024-1x4tj", resourceDetail.get("name"));
        assertEquals("3B4334C823C73F1F96D1AABF427C1080", resourceDetail.get("ref_clusterId"));
    }

    /**
     * 测试查询Node下所有的POD
     *
     * @throws Exception
     */
    @Test
    public void testGetResourceInstanceForNodePod() throws Exception {
        String authId = "huawei_beijing_opm_0";
        String resourceType = "CLOUD_CCE_POD";
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("name", "ref_nodeId");
        conditionMap.put("value", "63CFEA9C2F393FF0A96F77CAFB3E7648");
        List<LinkedHashMap> resourceDetailList = serviceMonitorService.getResourceInstance(authId, resourceType, conditionMap);
        log.warn("result:{}", JacksonUtil.objToJson(resourceDetailList));
        assertEquals(3, resourceDetailList.size());
        assertEquals("consumer-mojito-pqkv2a-79ddbb4c99-vsnst", resourceDetailList.get(0).get("name"));
        assertEquals("icagent-4tx2c", resourceDetailList.get(1).get("name"));
        assertEquals("everest-csi-driver-g9mbk", resourceDetailList.get(2).get("name"));
    }

    @Test
    public void testGetSeriesDataForPod() throws Exception {
        String opmAuthId = "huawei_beijing_opm_0";
        String opsAuthId = "huawei_beijing_ops_0";
        String resourceType = "CLOUD_CCE_POD";
        Map<String, Object> resourceQueryMap = new HashMap<>();
        resourceQueryMap.put("name", "name");
        resourceQueryMap.put("value", "consumer-mojito-pqkv2a-79ddbb4c99-vsnst");
        List<LinkedHashMap> podObjectList = serviceMonitorService.getResourceInstance(opmAuthId, resourceType, resourceQueryMap);
        LinkedHashMap podObject = podObjectList.get(0);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("podName", "consumer-mojito-pqkv2a-79ddbb4c99-vsnst");

        // 查询指标列表
        List<String> seriesMetricList = serviceMonitorService.getTimeSeries(opsAuthId, resourceType, (String) podObject.get("projectId"), objectMap);
        assertEquals(21, seriesMetricList.size());

        // 查询指标数
        SeriesDataParamVO paramVO = new SeriesDataParamVO();
        paramVO.setPeriod(60);
        paramVO.setTimeRange("-1.-1.59");
        paramVO.setStatistics(Arrays.asList("maximum", "minimum", "sum"));

        // 21个指标，超过20个了， 移除多的。
        seriesMetricList.remove(seriesMetricList.size() - 1);

        List<LinkedHashMap> seriesDataList = serviceMonitorService.getTimeSeriesData(opsAuthId, resourceType, (String) podObject.get("projectId"), seriesMetricList, objectMap, paramVO);
        assertEquals(20, seriesDataList.size());
        assertEquals(60, ((List) seriesDataList.get(0).get("data_points")).size());
    }

    @Test
    public void testGetSeriesDataForNode() throws Exception {
        String opmAuthId = "huawei_beijing_opm_0";
        String opsAuthId = "huawei_beijing_ops_0";
        String resourceType = "CLOUD_CCE_NODE";
        Map<String, Object> resourceQueryMap = new HashMap<>();
        resourceQueryMap.put("name", "id");
        resourceQueryMap.put("value", "63CFEA9C2F393FF0A96F77CAFB3E7648");
        List<LinkedHashMap> nodeObjectList = serviceMonitorService.getResourceInstance(opmAuthId, resourceType, resourceQueryMap);
        LinkedHashMap nodeObject = nodeObjectList.get(0);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("nodeIP", nodeObject.get("privateIP"));
        objectMap.put("clusterId", nodeObject.get("clusterId"));

        // 查询指标列表
        List<String> seriesMetricList = serviceMonitorService.getTimeSeries(opsAuthId, resourceType, (String) nodeObject.get("projectId"), objectMap);
        assertEquals(55, seriesMetricList.size());

        // 查询指标数
        SeriesDataParamVO paramVO = new SeriesDataParamVO();
        paramVO.setPeriod(60);
        paramVO.setTimeRange("-1.-1.59");
        paramVO.setStatistics(Arrays.asList("maximum", "minimum", "sum"));

        // 每次取20个
        List<String> metricNames = seriesMetricList.subList(0, 20);

        List<LinkedHashMap> seriesDataList = serviceMonitorService.getTimeSeriesData(opsAuthId, resourceType, (String) nodeObject.get("projectId"), metricNames, objectMap, paramVO);
        assertEquals(20, seriesDataList.size());
        assertEquals(60, ((List) seriesDataList.get(0).get("data_points")).size());
    }
}