package cn.cncc.caos.uaa.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.utils.StringUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseDep;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.service.DepService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推送门户信息工具类
 */
@Component
@Slf4j
public class PushPortalInfoUtil {

    @Resource
    private ServerConfigHelper serverConfigHelper;

    @Autowired
    private DepService depService;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OperationHistoryService operationHistoryService;

    /**
     * IOPS门户获取Token信息
     */
    public String iopsGetToken(String value) throws IOException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", value);
        this.pushCommonParam(map);
        Map<String, Object> iopsTokenMap = portalApply(map, serverConfigHelper.getValue("app.portal.path.get-token"));
        if (CollectionUtils.isEmpty(iopsTokenMap)) {
            log.error("iopsTokenMap is empty");
            throw new BapParamsException("iops get-token error");
        } else if (ObjectUtils.isEmpty(iopsTokenMap.get("access_token"))) {
            log.error("access_token");
            throw new BapParamsException("iops get-token error");
        }
        return (String) iopsTokenMap.get("access_token");
    }

    /**
     * 推送门户修改用户信息
     */
    @Async("portalUserPwdPushAsyncExecutor")
    public void pushUserChange(BaseUser baseUser, int type) throws JsonProcessingException {
        Map<String, Object> operateContent = new HashMap<>();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String path = serverConfigHelper.getValue("app.portal.path.user-change");
        try {
            BaseDep baseDep = depService.getById(baseUser.getDepId());
            map.add("changecode", type == 1 ? "CC00" : "CC01");
            map.add("loginid", baseUser.getUserName());
            map.add("uname", baseUser.getRealName());
            map.add("tel", baseUser.getPhone());
            map.add("status", baseUser.getIsValid() == 1 ? "US01" : "US00");
            map.add("deptcode", baseDep.getDepCode());
            if (!StringUtils.isEmpty(baseUser.getEmail())) {
                map.add("email", baseUser.getEmail());
            }
            String locationName = baseUser.getLocationName();
            if (!StringUtils.isEmpty(locationName)) {
                switch (baseUser.getLocationName()) {
                    case "北京":
                        locationName = "BJ";
                        break;
                    case "上海":
                        locationName = "SH";
                        break;
                    case "无锡":
                        locationName = "WX";
                }
                map.add("areacode", locationName);
            }
            this.pushCommonParam(map);
            Map<String, Object> retMap = portalApply(map, path);
            operateContent.put("retMap", retMap);
            operateContent.put("result", "推送成功");
        } catch (Exception e) {
            operateContent.put("retMap", e.getMessage());
            operateContent.put("result", "推送异常");
            log.error("", e);
        }  finally {
            BaseUser operBaseUser = new BaseUser();
            operBaseUser.setRealName("推送门户");
            operateContent.put("portalOnOff", "on".equals(serverConfigHelper.getValue("app.portal.on-off")));
            operateContent.put("param", map.toSingleValueMap());
            operateContent.put("path", path);
            operationHistoryService.insertHistoryOper(operBaseUser, "推送门户修改用户信息", JacksonUtil.objToJson(operateContent));
        }
    }

    /**
     * 推送门户修改用户密码信息
     */
    @Async("portalUserPwdPushAsyncExecutor")
    public Map<String, Object> pushUpdateUserPwd(BaseUser baseUser) throws JsonProcessingException {
        Map<String, Object> operateContent = new HashMap<>();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String path = serverConfigHelper.getValue("app.portal.path.pwd-change");
        try {
            if (StringUtil.isEmpty(baseUser.getPassword())) {
                throw new BapParamsException("修改密码不能为空");
            }
            map.add("loginid", baseUser.getUserName());
            map.add("pwd", baseUser.getPassword());
            this.pushCommonParam(map);
            Map<String, Object> retMap = portalApply(map, path);
            operateContent.put("retMap", retMap);
            operateContent.put("result", "推送成功");
            return retMap;
        } catch (Exception e) {
            operateContent.put("retMap", e.getMessage());
            operateContent.put("result", "推送异常");
            log.error("", e);
            throw new BapParamsException("推送门户修改用户密码信息失败");
        } finally {
            BaseUser operBaseUser = new BaseUser();
            operBaseUser.setRealName("推送门户");
            operateContent.put("portalOnOff", "on".equals(serverConfigHelper.getValue("app.portal.on-off")));
            operateContent.put("param", map.toSingleValueMap());
            operateContent.put("path", path);
            operationHistoryService.insertHistoryOper(operBaseUser, "推送门户修改用户密码信息", JacksonUtil.objToJson(operateContent));
        }
    }


    /**
     * 根据TOKEN获取门户对应用户信息
     */
    public String iopsGetUserInfo(String accessToken) {
        try {
            String path = serverConfigHelper.getValue("app.portal.path.get-info");
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("access_token", accessToken);
            this.pushCommonParam(map);
            Map<String, Object> resultMap = portalApply(map, path);

            if (CollectionUtils.isEmpty(resultMap)) {
                log.error("iopsUserInfoMap is empty");
                throw new BapParamsException("iops get-token error");
            } else if (ObjectUtils.isEmpty(resultMap.get("info"))) {
                log.error("user info is empty");
                throw new BapParamsException("iops get-token error");
            }
//            Map userInfo = JacksonUtil.objToObj(resultMap.get("info"), Map.class); TODO
            Map userInfo = (Map) resultMap.get("info");
            if (ObjectUtils.isEmpty(userInfo.get("loginid"))) {
                log.error("loginid is empty");
                throw new BapParamsException("iops get-token error");
            }
            return (String) userInfo.get("loginid");
        } catch (Exception e) {
            log.error("", e);
            throw new BapParamsException("获取门户用户信息失败");
        }
    }

    public Map<String, Object> portalApply(MultiValueMap<String, String> map, String path) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String url = String.format("%s/%s", serverConfigHelper.getValue("app.portal.url"), path);
        log.info("iops url: {}", url);
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
            log.info(String.format("key:%s, value:%s", stringListEntry.getKey(), stringListEntry.getValue()));
        }
        Map<String, Object> retMap = new HashMap<>();
        boolean onOff = "on".equals(serverConfigHelper.getValue("app.portal.on-off"));
        if (onOff) {
            String result = restTemplate.postForObject(url, request, String.class);
            log.info("iops result：{}", result);
            retMap = JacksonUtil.jsonToObj(result, Map.class);
            if ("0000".equals(retMap.get("msgCode"))) {
                return retMap;
            } else {
                log.error("apply portal error: {}", result);
                throw new BapParamsException("apply portal error: " + result);
            }
        } else {
            log.info("onOff: {}, not push iops", false);
            return retMap;
        }
    }

    public void pushCommonParam(MultiValueMap<String, String> user) {
        user.add("client_id", serverConfigHelper.getValue("app.portal.client.id"));
        user.add("client_secret", serverConfigHelper.getValue("app.portal.client.secret"));
    }

    /**
     * 同步获取门户修订用户信息
     */
    public Map<String, Object> syncUserGet(Integer pageNum, Integer pageSize, Date syncTime) {
        try {
            String path = serverConfigHelper.getValue("app.portal.path.user-query");
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//            map.add("synctime", DateUtils.format(syncTime, DateUtil.DateToString(syncTime, DateUtil.ptn_18)));
            map.add("synctime", DateUtil.DateToString(syncTime, DateUtil.ptn_18));
            map.add("pageNum", String.valueOf(pageNum));
            map.add("pageSize", String.valueOf(pageSize));
            this.pushCommonParam(map);
            Map<String, Object> retMap = portalApply(map, path);
            return retMap;
        } catch (Exception e) {
            log.error("", e);
            throw new BapParamsException("拉取门户用户信息失败");
        }
    }

    /**
     * 同步获取门户修订部门信息
     */
    public Map<String, Object> syncDepGet(int pageNum, Integer pageSize, Date syncTime) {
        try {
            String path = serverConfigHelper.getValue("app.portal.path.dep-query-all");
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("synctime", DateUtil.DateToString(syncTime, DateUtil.ptn_18));
            map.add("pageNum", String.valueOf(pageNum));
            map.add("pageSize", String.valueOf(pageSize));
            this.pushCommonParam(map);
            Map<String, Object> retMap = portalApply(map, path);
            return retMap;
        } catch (Exception e) {
            log.error("", e);
            throw new BapParamsException("拉取门户部门信息失败");
        }
    }
}
