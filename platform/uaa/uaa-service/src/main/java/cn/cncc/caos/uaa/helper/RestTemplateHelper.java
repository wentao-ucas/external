package cn.cncc.caos.uaa.helper;

import cn.cncc.caos.common.core.exception.BapLogicException;
import cn.cncc.caos.uaa.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RestTemplateHelper {

  @Autowired
  @Qualifier("httpsRestTemplate")
  private RestTemplate remoteHttpsRestTemplate;

  public Map<String, Object> postToken(String userName, String loginUrl, String ip, RestTemplate remoteHttpsRestTemplate) {
    String url = ip + loginUrl + "?username=" + userName;
//设置请求参数
    HttpHeaders httpHeaders = HttpUtil.buildBaseHttpHeaders();
    String authStr = "open_client".concat(":").concat("open_secret");
    String authStrEnc = Base64.getEncoder()
        .encodeToString(authStr.getBytes());
    httpHeaders.set("Authorization", "Basic ".concat(authStrEnc));

    HttpEntity<Object> formEntity = new HttpEntity<>(httpHeaders);
    Map<String, Object> response = remoteHttpsRestTemplate.postForObject(url, formEntity, Map.class);
    if (response == null) {
      log.error("request cncc login url res is null");
      throw new BapLogicException("获取token失败");
    }

    Object code = response.get("code");
    if (code != null && !code.equals(1)) {
      log.error("request cncc login url error, msg:" + response.get("msg"));
      throw new BapLogicException("获取token失败，msg:" + response.get("msg"));
    }

    // 返回cnccIp
    response.put("cnccUrl", ip);
    log.info("==========postToken===============" + response);
    return response;
  }

  public Map<String, Object> requestCNCCApi(String loginUrl, String ip, String url, HttpEntity<Object> formEntity, Map<String, Object> params, String userName) {
    Map<String, Object> res = new HashMap<>();
    try {
      ResponseEntity<Map> response = remoteHttpsRestTemplate.exchange(ip + url, HttpMethod.GET, formEntity, Map.class, params);
      res = response.getBody();
    } catch (Exception e) {
      log.error("", e);
      if (e instanceof HttpClientErrorException.Unauthorized) {
        Map<String, Object> userDetails = this.postToken(userName, loginUrl, ip, remoteHttpsRestTemplate);
        String cnccToken = (String) userDetails.get("accessToken");
        HttpHeaders httpHeaders = HttpUtil.buildTokenHttpHeaders(cnccToken);
        formEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Map> response = remoteHttpsRestTemplate.exchange(ip + url, HttpMethod.GET, formEntity, Map.class, params);
        res = response.getBody();
        res.put("userDetails", userDetails);
      }
    }
    return res;
  }
}
