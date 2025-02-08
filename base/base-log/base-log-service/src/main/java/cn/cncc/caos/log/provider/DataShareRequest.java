package cn.cncc.caos.log.provider;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class DataShareRequest {
//  public static final String HTTP = "http://172.22.120.37:9082";
  @Value("${diepes.datashare.restserver}")
  private String restserver;
  @Value("${diepes.datashare.username}")
  private String userName;
  @Value("${diepes.datashare.password}")
  private String password;
  @Value("${diepes.datashare.api.udfquery}")
  private String apiUdfQuery;
  @Value("${diepes.datashare.api.login}")
  private String apiLogin;
  @Value("${diepes.datashare.api.scroll}")
  private String apiScroll;

  public BdosResData query(String dsl, Map param) {
//    String url = "/bdosservice/query/udfquery";
    String token = gettoken();
    RPCParam rpcparam = new RPCParam();
    rpcparam.setApikey("UDFQUERY");
    rpcparam.setDscode("DSCBDOS002");
    rpcparam.setDsl(dsl);
    rpcparam.setParam(param);

    String response = HttpUtil.post(restserver + apiUdfQuery, JSON.toJSONString(rpcparam),
        token, HttpUtil.SOCKET_TIMEOUT, HttpUtil.DEFAULT_ENCODING);

    if (StringUtils.isEmpty(response)) {
      throw new RuntimeException(" [ RPC - DATASHARE - 无可用节点 ] ");
    }


    BdosResData resData = JSON.parseObject(response, BdosResData.class);
    if (!"BDOS0000".equals(resData.getCode())) {
      throw new RuntimeException("[ RPC -- ERROR ] response code : " + resData.getCode() + " msg : " + resData.getMessage());
    }
    return resData;
  }

  private String gettoken() {
//    String url = "/bdosservice/login";

    Map<String, String> param = new HashMap<String, String>();
    param.put("username", userName);
    param.put("password", password);
    String response = HttpUtil.post(restserver + apiLogin, JSON.toJSONString(param), null,
        HttpUtil.SOCKET_TIMEOUT, HttpUtil.DEFAULT_ENCODING);
    if (StringUtils.isEmpty(response)) {
      throw new RuntimeException(" [ RPC - DATASHARE - 无可用节点 ] ");
    }
    // 将response 解析，提取token
    BdosResData resData = JSON.parseObject(response, BdosResData.class);
    Map<String, String> data = (Map<String, String>) resData.getData();
    String token = data.get("token");

    return token;
  }

  public BdosResData getLogScroll(Map param) {

//    String url = "/bdosservice/query/scroll";
    //获取token
    String token = gettoken();
    String response = HttpUtil.post(restserver + apiScroll, JSON.toJSONString(param), token, HttpUtil.SOCKET_TIMEOUT, HttpUtil.DEFAULT_ENCODING);
    if (StringUtils.isEmpty(response)) {
      throw new RuntimeException(" [ RPC - DATASHARE - 无可用节点 ] ");
    }
    BdosResData resData = JSON.parseObject(response, BdosResData.class);

    return resData;

  }
}
