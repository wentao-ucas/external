package cn.cncc.caos.uaa.controller.base;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.helper.KDHelper;
import cn.cncc.caos.uaa.service.*;
import cn.cncc.caos.uaa.utils.PushPortalInfoUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.utils.ValidationTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
public abstract class BaseBaseUserComplexController {
    @Resource
    private KDHelper kdHelper;
    @Resource
    private UserService userService;
    @Resource
    private TokenEndpoint tokenEndpoint;
    @Resource
    private BaseUserService baseUserService;
    @Resource
    private ServerConfigHelper serverConfigHelper;
    @Resource
    private PushPortalInfoUtil pushPortalInfoUtil;
    @Resource
    private HistoryOperService historyOperService;


    public JwResponseData<Map<String, Object>> iopsLogin(@RequestParam(value = "type", required = false, defaultValue = "code") String type,
                                                @RequestParam(value = "value", required = false, defaultValue = "074806ed0bcd459fb126571bd362a147%7") String value) {
        try {
            log.info("iops login, code: {}、value: {}", type, value);
            ValidationTools.checkForNull(type, String.format("type: {%s} 参数异常", type));
            ValidationTools.checkForNull(value, String.format("value: {%s} 参数异常", value));
            String accessToken = pushPortalInfoUtil.iopsGetToken(value);
            log.info("iops access_token: {}", accessToken);
            String userName = pushPortalInfoUtil.iopsGetUserInfo(accessToken);
            Map<String, Object> userInfoMap = baseLogin(userName, "iops-login");
            return JwResponseData.success("成功", userInfoMap);
        } catch (BapParamsException e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
        }
    }

    public Map<String, Object> baseLogin(String userName, String type) {
        // 获取精卫用户信息
        BaseUser baseUser = userService.getUserByUserName(userName);
        if (baseUser == null) {
            throw new BapParamsException("精卫不存在此用户");
        }
        if (baseUser.getIsValid() != 1) {
            throw new BapParamsException("用户已锁定");
        }
        String password = baseUser.getPassword();
        HashMap<String, String> oauthTokenReqMap = new HashMap<>();
        oauthTokenReqMap.put("username", userName);
        oauthTokenReqMap.put("password", password);
        oauthTokenReqMap.put("grant_type", "password");
        oauthTokenReqMap.put("scope", "open_scope");
        oauthTokenReqMap.put("client_id", "open_client");
        oauthTokenReqMap.put("client_secret", "open_secret");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        oauthTokenReqMap.get("client_id"),
                        oauthTokenReqMap.get("client_secret"),
                        true,
                        true,
                        true, true, new HashSet<>()),
                null,
                new HashSet<>());
        Object login = baseUserService.login(usernamePasswordAuthenticationToken, oauthTokenReqMap, tokenEndpoint);
        ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) login;
        String accessToken = String.valueOf(responseEntity.getBody());
        Map<String, Object> userInfoMap = baseUserService.getUserDetailByUserName(userName);
        userInfoMap.put("accessToken", accessToken);
        String defaultUserPassword = serverConfigHelper.getValue("defaultUserPassword");
        if (password.equals(defaultUserPassword)) {
            userInfoMap.put("isPasswordInit", 1);
        } else {
            userInfoMap.put("isPasswordInit", 0);
        }
        // 系统标识：支付系统、非支付系统
        String instance = serverConfigHelper.getValue("system.instance");
        userInfoMap.put("systemType", instance);
        userInfoMap.put("instance", instance);
        userInfoMap.put("jwEnv", kdHelper.getJwEnv());
        //登录成功后增加登录记录
        historyOperService.insertHistoryUserLogin(baseUser, type);
        return userInfoMap;
    }

}
