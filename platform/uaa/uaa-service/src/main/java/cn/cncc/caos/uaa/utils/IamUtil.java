package cn.cncc.caos.uaa.utils;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求IAM（统一身份管理认证平台）工具类
 */
@Slf4j
public class IamUtil {
    private final ServerConfigHelper serverConfigHelper;
    private final RestTemplate restTemplate;
    public IamUtil(RestTemplate restTemplate, ServerConfigHelper serverConfigHelper) {
        this.restTemplate = restTemplate;
        this.serverConfigHelper = serverConfigHelper;
    }

    public String iamGetToken(String code) throws IOException {
        String getTokenUrl = serverConfigHelper.getValue("iam.get_token.url");
        String clientSecret = serverConfigHelper.getValue("iam.client.secret");
        UriComponentsBuilder uriComponentsBuilder = getUriComponentsBuilder(getTokenUrl);
        uriComponentsBuilder.queryParam("code", code);
        uriComponentsBuilder.queryParam("client_secret", clientSecret);
        uriComponentsBuilder.queryParam("grant_type", "authorization_code");
        Map<String, Object> resMap = iamPost(null, uriComponentsBuilder);
        if (!resMap.containsKey("access_token")) {
            throw new BapParamsException("请求IAM获取Token接口未返回access_token信息");
        }
        return (String) resMap.get("access_token");
    }


    public String iamGetUserInfo(String accessToken) throws IOException {
        String getUserInfoUrl = serverConfigHelper.getValue("iam.get_user_info.url");
        UriComponentsBuilder uriComponentsBuilder = getUriComponentsBuilder(getUserInfoUrl);
        uriComponentsBuilder.queryParam("access_token", accessToken);
        Map<String, Object> resMap = iamGet(uriComponentsBuilder);
        if (resMap.containsKey("spRoleList")) {
            return (String) ((List<?>) resMap.get("spRoleList")).get(0);
        }
        throw new BapParamsException("请求IAM获取用户信息接口未返回spRoleList信息");
    }


    private UriComponentsBuilder getUriComponentsBuilder(String url) {
        String baseUrl = serverConfigHelper.getValue("iam.url");
        String clientId = serverConfigHelper.getValue("iam.client.id");
        return UriComponentsBuilder.fromHttpUrl(String.format("%s%s", baseUrl, url))
                .queryParam("client_id", clientId);
    }

    /**
     * 请求IAM（GET请求）
     *
     * @param uriComponentsBuilder
     * @return
     * @throws IOException
     */
    private Map<String, Object> iamGet(UriComponentsBuilder uriComponentsBuilder) throws IOException {
        String uri = uriComponentsBuilder.toUriString();
        log.info("iam url: {}", uriComponentsBuilder.toUriString());
        // 发送GET请求
        String response = restTemplate.getForObject(uri, String.class);
        return iamResponse(response);
    }

    /**
     * 请求IAM（Post请求）
     *
     * @return
     * @throws IOException
     */
    private Map<String, Object> iamPost(Map<String, String> body, UriComponentsBuilder uriComponentsBuilder) throws IOException {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 构建请求实体
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);
        // 路径转换
        String uri = uriComponentsBuilder.toUriString();
        log.info("iam url: {}", uri);
        // 发送 POST 请求
        String response = restTemplate.postForObject(uri, requestEntity, String.class);
        return iamResponse(response);
    }

    /**
     * IAM请求返回参数验证和转换
     *
     * @param response
     * @return
     * @throws IOException
     */
    private Map<String, Object> iamResponse(String response) throws IOException {
        log.info("response: {}", response);
        Map<String, Object> resMap = JacksonUtil.jsonToObj(response, HashMap.class);
        if (CollectionUtils.isEmpty(resMap)) {
            throw new BapParamsException("请求IAM接口异常");
        }
        if (resMap.containsKey("errcode")) {
            throw new BapParamsException(String.format("请求IAM接口异常, errcode: %s, msg: %s", resMap.get("errcode"), resMap.get("msg")));
        }
        return resMap;
    }

}
