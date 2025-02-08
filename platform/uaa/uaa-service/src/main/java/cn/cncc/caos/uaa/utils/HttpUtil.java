package cn.cncc.caos.uaa.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpUtil {
  public static HttpHeaders buildHttpHeaders() {
    HttpHeaders httpHeaders = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
    httpHeaders.setContentType(type);
    httpHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
    return httpHeaders;
  }

  public static HttpHeaders buildBaseHttpHeaders() {
    HttpHeaders httpHeaders = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
    httpHeaders.setContentType(type);
    return httpHeaders;
  }

   public static HttpHeaders buildTokenHttpHeaders(String token) {
     HttpHeaders httpHeaders = buildBaseHttpHeaders();
     httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
     return httpHeaders;
   }

  public static HttpHeaders buildUrlencodedHttpHeaders() {
    HttpHeaders httpHeaders = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded");
    httpHeaders.setContentType(type);
    return httpHeaders;
  }

  public static HttpHeaders buildHttpHeaders(String appId, String secret) {
    HttpHeaders headers = buildHttpHeaders();
    headers.add("App-Id", appId);
    headers.add("App-Secret", secret);
    return headers;
  }

  public static HttpHeaders buildJinkeHttpHeaders(String appId, String appSecret, String apiName) {
    HttpHeaders headers = buildHttpHeaders();
    headers.add("code", appId);
    headers.add("key", appSecret);
    headers.add("m", "api");
    long timeMillis = System.currentTimeMillis();
    String nowTime = timeMillis/1000+"";
    headers.add("time", nowTime);
    headers.add("f", apiName);
    String strBeforeMd5 = appId + "+" + appSecret + "+" + nowTime;
    String token = DigestUtils.md5Hex(strBeforeMd5);
    headers.add("token", token);
    return headers;
  }

  public static String buildJinkeUrl(String restServer, String api, String appId, String appSecret, String apiName) {
    long timeMillis = System.currentTimeMillis();
    long nowTime = timeMillis / 1000;
    String strBeforeMd5 = appId + appSecret + nowTime;
    String token = DigestUtils.md5Hex(strBeforeMd5);
    return restServer + api + "?m=api&f=" + apiName + "&token=" + token + "&code=" + appId + "&time=" + nowTime;
  }

  public static HttpHeaders buildTKHttpHeaders(String token) {
    HttpHeaders httpHeaders = buildHttpHeaders();
    httpHeaders.add("tk", token);
    return httpHeaders;
  }

  public static HttpHeaders buildDCIMHttpHeaders(String accountId, String topAccountId, String userId) {
    HttpHeaders headers = buildHttpHeaders();
    headers.add("accountId", accountId);
    headers.add("topAccountId", topAccountId);
    headers.add("userId", userId);
    return headers;
  }
}
