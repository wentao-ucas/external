package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.*;
import cn.cncc.caos.common.core.utils.StringUtil;
import cn.cncc.caos.uaa.KDConstant;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.controller.base.BaseBaseUserComplexController;
import cn.cncc.caos.uaa.db.daoex.BaseUserMapperEx;
import cn.cncc.caos.uaa.enums.InstanceTypeEnum;
import cn.cncc.caos.uaa.exception.DataAuthException;
import cn.cncc.caos.uaa.helper.KDHelper;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.service.*;
import cn.cncc.caos.uaa.utils.*;
import cn.cncc.caos.platform.uaa.client.api.DataAuthResponseCode;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class BaseUserComplexController extends BaseBaseUserComplexController {

  @Resource
  private ServerConfigHelper serverConfigHelper;
  //外部公用接口
  @Autowired
  private BaseUserMapperEx baseUserMapperEx;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  BaseUserService baseUserService;
  @Autowired
  private PushPortalInfoUtil pushPortalInfoUtil;
  @Resource
  private KDHelper kdHelper;
  @Resource
  private HistoryOperService historyOperService;
  private String operTypeUpdate = "userUpdatebasePermissionService";
  @Autowired
  private OperationHistoryService operationHistoryService;
  @Autowired
  @Qualifier("httpsRestTemplate")
  private RestTemplate remoteHttpsRestTemplate;
  @Resource
  private TokenEndpoint tokenEndpoint;

  @RequestMapping(value = "/open_api/get_user_by_name", method = RequestMethod.GET)
  JwResponseData<Map<String, Object>> getUserByUserNameOauth(@RequestParam(value = "userName", required = true) String userName) {
    try {
      //外部系统使用本方法，查询用户信息
      // 调用FeignClient查询用户
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      log.info("authentication.getName()" + authentication.getName());
      BaseUser baseUser;
      try {
        baseUser = JacksonUtil.jsonToObj(authentication.getName(), BaseUser.class);
      } catch (IOException e) {
        log.error("json to obj error", e);
        return JwResponseData.error(JwResponseCode.COMPLETE_USER_ERROR);
      }
      if (StringUtils.isEmpty(baseUser.getUserName()))
        return JwResponseData.error(JwResponseCode.COMPLETE_USER_ERROR);
      if (!userName.equals(baseUser.getUserName())) {
//        throw new DataAuthException(DataAuthResponseCode.NOT_AUTHORIZED_GET_INFO_FROM_OTHER_USER);
        return JwResponseData.error(JwResponseCode.COMPLETE_USER_ERROR);
      }
      return JwResponseData.success("getUserByUserName获取成功", baseUserService.getUserDetailByUserName(userName));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }



  @ResponseBody
  @RequestMapping(value = "/sso_login", method = RequestMethod.POST)
  JwResponseData<Map<String, Object>> getUserByUserNameOauth(@RequestBody Map jsonObject) {
    // @RequestParam(value = "userName", required = true) String userName,@RequestParam(value = "password", required = true) String password
    // 调用FeignClient查询用户
    if (!jsonObject.containsKey("userName") || !jsonObject.containsKey("password"))
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs("用户名或密码不能为空"));

    String userName = jsonObject.get("userName").toString();
    if (StringUtils.isNotEmpty(userName)) {
      userName = userName.trim();
    }
    log.info("userName" + userName);
    String password = jsonObject.get("password").toString();
    try {
      password = RSAUtil.decrypt(password);
      jsonObject.put("password", password);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }

//    log.info("password" + password);

    BaseUser baseUserData = baseUserMapperEx.getUserByUserName(userName);
    if (baseUserData == null) {
      return JwResponseData.error(DataAuthResponseCode.LOGIN_USER_NOT_EXIST);
//      throw new DataAuthException(DataAuthResponseCode.LOGIN_USER_NOT_EXIST);
    } else if (baseUserData.getIsValid() == null || !baseUserData.getIsValid().equals(1)) {
      return JwResponseData.error(DataAuthResponseCode.LOGIN_USER_LOCKED);
//      throw new DataAuthException(DataAuthResponseCode.LOGIN_USER_LOCKED);
    }
    //解密
    EncryptAndDecryptUtil.decryptBaseUser(baseUserData);
    String redisKey = "false_numbers_" + userName;
    //根据key去redis查询，如果存在value为0.返回锁定
    Integer errorNum = (Integer) redisTemplate.opsForValue().get(redisKey);
    if (errorNum != null && errorNum < 1) {
      return JwResponseData.error(DataAuthResponseCode.LOGIN_USER_LOCKED_TEMP);
    }

    String phone = baseUserData.getPhone();
    String key = "yzm_" + phone + "_" + userName;
    log.info("是否校验短信验证码,codeSwitch:{}", serverConfigHelper.getValue("code.switch"));
    if (serverConfigHelper.getBooleanValue("code.switch")) {
      if (StringUtils.isNotEmpty(phone) && !KDConstant.PHONE_DEFAULT.equals(phone)) {
        //判断验证码是否正确
        //根据手机号去redis查验证码
        String code = jsonObject.get("code").toString();
        String codeFromRedis = (String) redisTemplate.opsForValue().get(key);
        //判断code是否存在
        if (StringUtils.isEmpty(codeFromRedis))
          return JwResponseData.error(DataAuthResponseCode.VERIFICATION_CODE_NOT_EXIST);

        //验证码存在，则判断是否正确
        if (!codeFromRedis.equals(code))
          return JwResponseData.error(DataAuthResponseCode.VERIFICATION_CODE_FALSE);
      }
    }

    String passwordInDatabase = baseUserData.getPassword();
    //if(!passwordInDatabase.equals(password)) {
    if (!passwordEncoder.matches(password, passwordInDatabase)) {
      //添加冻结用户功能，首先向redis中获取密码错误次数，有-1.没有set，如果登录次数超5次，冻结

      Integer count = (Integer) redisTemplate.opsForValue().get(redisKey);
      Integer lockTime = serverConfigHelper.getIntegerValue("userLock.lockTime");
      Integer errorCounts = serverConfigHelper.getIntegerValue("userLock.errorCounts");
      if (count == null) {
        redisTemplate.opsForValue().set(redisKey, errorCounts - 1, lockTime, TimeUnit.SECONDS);
        return JwResponseData.error(DataAuthResponseCode.LOGIN_USER_NOT_EXIST);
      } else {
        if (count > 1) {
          redisTemplate.opsForValue().set(redisKey, count - 1, lockTime, TimeUnit.SECONDS);
          return JwResponseData.error(DataAuthResponseCode.LOGIN_USER_NOT_EXIST);
        } else {
          redisTemplate.opsForValue().set(redisKey, count - 1, lockTime, TimeUnit.SECONDS);
          return JwResponseData.error(DataAuthResponseCode.LOGIN_USER_LOCKED_TEMP);
        }
      }
//      throw new DataAuthException(DataAuthResponseCode.LOGIN_USER_PASSWORD_ERROR);
    }

    Map authResultJson = new HashMap();
    try {
      HashMap<String, String> map = new HashMap<>();
      map.put("username", userName);
      map.put("password", baseUserData.getPassword());
      map.put("grant_type", "password");
      map.put("scope", "open_scope");
      map.put("client_id", "open_client");
      map.put("client_secret", "open_secret");
      log.info("0. " + userName);
//      HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
//      JiuBoDouAuthenticationToken jiuBoDouAuthenticationToken = new JiuBoDouAuthenticationToken(map.get("client_id"),password,grantedAuthorities);
//      jiuBoDouAuthenticationToken.setDetails(map);
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
              new org.springframework.security.core.userdetails.User(
                      map.get("client_id"),
                      map.get("client_secret"),
                      true,
                      true,
                      true, true, new HashSet<>()),
              null,
              new HashSet<>());
      ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>)baseUserService.login(
              usernamePasswordAuthenticationToken, map, tokenEndpoint);
      authResultJson.put("access_token", responseEntity.getBody());
    } catch (Exception e) {
      try {
        log.info("======authResultJson Exception====" + JacksonUtil.objToJson(e));
      } catch (JsonProcessingException ex) {
        log.error("", e);
      }
      log.error("", e);
      return JwResponseData.error(DataAuthResponseCode.LOGIN_USER_CALL_AUTH_OAUTH_FAIL);
    }

    String accessToken = "none";
    if (authResultJson.containsKey("access_token"))
      accessToken = String.valueOf(authResultJson.get("access_token"));

    try {
      log.info("authResultJson" + JacksonUtil.objToJson(authResultJson));
    } catch (JsonProcessingException e) {
      log.error("", e);
    }

    Map<String, Object> json = baseUserService.getUserDetailByUserName(userName);
    log.info("1. " + userName);
    json.put("accessToken", accessToken);
    if (password.equals(serverConfigHelper.getValue("defaultUserPassword")))
      json.put("isPasswordInit", 1);
    else
      json.put("isPasswordInit", 0);

    // 系统标识：支付系统、非支付系统
    String instance = serverConfigHelper.getValue("system.instance");
    json.put("systemType", instance);
    json.put("instance", instance);
    json.put("jwEnv", kdHelper.getJwEnv());

    //登录成功后增加登录记录
    historyOperService.insertHistoryUserLogin(baseUserData, "login");
    log.info("2. " + userName);
    //登录成功之后删除redis中的指定key
    redisTemplate.delete(redisKey);
    log.info("3. " + userName);
    redisTemplate.delete(key);
    log.info("4. " + userName);

    if (instance.equals(InstanceTypeEnum.CNCC.type) || KDUtil.isNpc(kdHelper.getJwEnv()))
      return JwResponseData.success("getUserByUserName获取成功", json);

    // 总行实例，调用支付获取token接口，
    Map<String, Object> getTokenRes = this.postToken(userName);
    Map<String, Object> res = new HashMap<>();
    res.put(InstanceTypeEnum.CNCC.type, getTokenRes);
    res.put(InstanceTypeEnum.CFID.type, json);
    return JwResponseData.success("getUserByUserName获取成功", res);
  }

  private Map<String, Object> postToken(String userName) {
    try {
      String cnccUrl = serverConfigHelper.getValue("cncc.login.url");
      String ip = serverConfigHelper.getValue("cncc.ip");

      String url = ip + cnccUrl + "?username=" + userName;
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
        return null;
      }

      Object code = response.get("code");
      if (code != null && !code.equals(1)) {
        log.error("request cncc login url error, msg:" + response.get("msg"));
        return null;
      }

      // 返回cnccIp
      String cnccIp = serverConfigHelper.getValue("cncc.ip");
      response.put("cnccUrl", cnccIp);
      log.info("==========postToken===============" + response);
      return response;
    } catch (Exception e) {
      log.error("", e);
      return null;
    }
  }

  @ResponseBody
  @RequestMapping(value = "/iam_login", method = RequestMethod.GET)
  public JwResponseData<Map<String, Object>> iamLogin(@RequestParam(value = "code", required = false, defaultValue = "code") String code) {
    try {
      log.info("iam login, code: {}", code);
      ValidationTools.checkForNull(code, String.format("type: {%s} 参数异常", code));
      IamUtil iamUtil = new IamUtil(remoteHttpsRestTemplate, serverConfigHelper);
      String accessToken = iamUtil.iamGetToken(code);
      String userName = iamUtil.iamGetUserInfo(accessToken);
      Map<String, Object> userInfoMap = super.baseLogin(userName, "iam-login");
      return JwResponseData.success("成功", userInfoMap);
    } catch (BapParamsException e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping(value = "/iops_login", method = RequestMethod.GET)
  public JwResponseData<Map<String, Object>> iopsLogin(@RequestParam(value = "type", required = false, defaultValue = "code") String type,
                                              @RequestParam(value = "value", required = false, defaultValue = "074806ed0bcd459fb126571bd362a147%7Cshiops1") String value) {
    return super.iopsLogin(type, value);
  }

  @ResponseBody
  @RequestMapping(value = "/open_api/update_myself", method = RequestMethod.POST)
  public JwResponseData<String> updateOneUser(@RequestBody Map jsonObject) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      log.info("authentication.getName()" + authentication.getName());
      BaseUser baseUser = new BaseUser();
      try {
        baseUser = JacksonUtil.jsonToObj(authentication.getName(), BaseUser.class);
      } catch (IOException e) {
        log.error("json to obj error", e);
      }
      if (StringUtils.isEmpty(baseUser.getUserName()))
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      if (!jsonObject.get("userName").toString().equals(baseUser.getUserName())) {
        throw new DataAuthException(DataAuthResponseCode.NOT_AUTHORIZED_GET_INFO_FROM_OTHER_USER);
      }
      if (!baseUser.getId().equals(Integer.parseInt(jsonObject.get("id").toString())))
        throw new DataAuthException(DataAuthResponseCode.NOT_AUTHORIZED_GET_INFO_FROM_OTHER_USER);

      BaseUser bu = new BaseUser();
      bu.setId(Integer.parseInt(jsonObject.get("id").toString()));

      if (jsonObject.containsKey("oldPassword")) {
        // 校验旧密码是否正确
        JwResponseData<String> BIND_ERROR = this.judgeOldPassword(jsonObject, baseUser);
        if (BIND_ERROR != null) return BIND_ERROR;
      }
      if (jsonObject.containsKey("password")) {
        if (!jsonObject.containsKey("oldPassword")) {
          log.error("oldPassword is empty");
          return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs("原密码不能为空"));
        }
        String password = jsonObject.get("password").toString();
        password = RSAUtil.decrypt(password);
        jsonObject.put("password", password);

        cn.cncc.caos.uaa.utils.StringUtil.checkPasswordValid(bu.getUserName(), password);
        bu.setPassword(passwordEncoder.encode(jsonObject.get("password").toString()));
        log.info("password is update userName:" + bu.getUserName());
      }
      if (jsonObject.containsKey("phone")) {
        cn.cncc.caos.uaa.utils.StringUtil.checkPhoneValid(bu.getUserName(), jsonObject.get("phone").toString());
        bu.setPhone(jsonObject.get("phone").toString());
      }
      if (jsonObject.containsKey("email")) {
        cn.cncc.caos.uaa.utils.StringUtil.checkEmailValid(bu.getUserName(), jsonObject.get("email").toString());
        bu.setEmail(jsonObject.get("email").toString());
      }
      if (jsonObject.containsKey("locationName"))
        bu.setLocationName(jsonObject.get("locationName").toString());

      bu.setUpdateTime(TimeUtil.getCurrentTime());
      //加密
      EncryptAndDecryptUtil.encryptBaseUser(bu);
      baseUserMapperEx.updateByPrimaryKeySelective(bu);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update myself end operationUser:" + user.getRealName() + ",content:" + JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user, operTypeUpdate, JacksonUtil.objToJson(jsonObject));

      BaseUser pushBaseUser = baseUserMapperEx.getUserById(baseUser.getId());
      // 支付环境生产进行推送，办公网同步生产进行推送
      if ("cncc".equals(serverConfigHelper.getValue("system.instance")) && KDUtil.isNpc(kdHelper.getJwEnv())) {
        if (jsonObject.containsKey("password")) {
          pushPortalInfoUtil.pushUpdateUserPwd(pushBaseUser);
        }
        pushPortalInfoUtil.pushUserChange(pushBaseUser, 2);
      } else {
      }

      return JwResponseData.success("更新用户成功");
    } catch (BapParamsException jpe) {
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(jpe.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  private JwResponseData<String> judgeOldPassword(Map jsonObject, BaseUser baseUser) throws Exception {
    String oldPassword = jsonObject.get("oldPassword").toString();
    oldPassword = RSAUtil.decrypt(oldPassword);
    BaseUser baseUserFromDb = baseUserMapperEx.selectByPrimaryKey(baseUser.getId());
    if (baseUserFromDb == null) {
      log.error("get user by id is null id:" + baseUser.getId());
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs("当前用户不存在"));
    }

    if (!passwordEncoder.matches(oldPassword, baseUserFromDb.getPassword())) {
      log.error("oldPassword is not equal");
      return JwResponseData.error(JwResponseCode.UPDATE_PASSWORD_ERROR);
    }
    return null;
  }

  @RequestMapping(value = "/open_api/user/details/by/token", method = RequestMethod.GET)
  @Operation(summary = "根据token获取用户信息接口")
  public JwResponseData<Map<String, Object>> getUserByToken(@RequestParam(value = "userName") String userName) {
    BaseUser user = UserHolder.getUser();
    if (!user.getUserName().equals(userName)) {
      log.error("userName is not currLoginUser");
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs("无法获取其他用户信息"));
    }

    Map<String, Object> userDetailByUserName = baseUserService.getUserDetailByUserName(user.getUserName());
    userDetailByUserName.put("isPasswordInit", 0);

    // 系统标识：支付系统、非支付系统
    String instance = serverConfigHelper.getValue("system.instance");
    userDetailByUserName.put("systemType", instance);
    userDetailByUserName.put("instance", instance);
    userDetailByUserName.put("jwEnv", kdHelper.getJwEnv());

    return JwResponseData.success("获取成功", userDetailByUserName);
  }

}
