package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.dao.BaseUserDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.HistoryOperMapper;
import cn.cncc.caos.uaa.db.daoex.BaseRoleMapperEx;
import cn.cncc.caos.uaa.db.daoex.BaseUserMapperEx;
import cn.cncc.caos.uaa.db.pojo.HistoryOper;
import cn.cncc.caos.uaa.helper.KDHelper;
import cn.cncc.caos.uaa.utils.EncryptAndDecryptUtil;
import cn.cncc.caos.platform.uaa.client.api.UserInfoRes;
import cn.cncc.caos.platform.uaa.client.api.pojo.BasePermission;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRoleAuth;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.utils.IDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
@Service
public class BaseUserService {
    Logger log = LoggerFactory.getLogger(BaseUserService.class);

    @Resource
    private ServerConfigHelper serverConfigHelper;
    @Autowired
    private BaseUserMapperEx baseUserMapperEx;
    @Autowired
    private DepService depService;
    @Autowired
    private HistoryOperMapper historyOperMapper;
    @Autowired
    private BaseRoleAuthService baseRoleAuthService;
    @Autowired
    private BasePermissionService basePermissionService;

    @Autowired
    private BaseRuleService baseRuleService;
    @Autowired
    private BaseRoleMapperEx baseRoleMapperEx;

//    @Resource
//    private TokenEndpoint tokenEndpoint;
    @Resource
    private KDHelper kdHelper;

    public JwResponseData<BaseUser> getUserByUserName(String userName){
        // 调用FeignClient查询用户
        BaseUser baseUserData = baseUserMapperEx.getUserByUserName(userName);
        if (baseUserData == null)
            return JwResponseData.error(JwResponseCode.DB_DATA_NOT_EXIST);
        //解密
        EncryptAndDecryptUtil.decryptBaseUser(baseUserData);
        String parentDepNames = depService.getParentDepNames(baseUserData.getDepId());
        baseUserData.setDepName(parentDepNames);
        return JwResponseData.success("getUserByUserName获取成功", baseUserData);
    }

    public JwResponseData<String> addHistoryLogout(String token,String realName){
        HistoryOper historyOper = new HistoryOper();
        historyOper.setId(IDUtil.getStringNextId("H" + serverConfigHelper.getValue("sync.id.prefix")));
        historyOper.setOperTime(new Date());
        historyOper.setRealName(realName);
        historyOper.setOperType("logout");
        historyOperMapper.insert(historyOper);
        return JwResponseData.success("退出成功");
    }

    public JwResponseData<Map<String, Object>> getUserDetails(@RequestParam(value = "userName", required = true) String userName) {
        return JwResponseData.success("getUserByUserName获取成功", getUserDetailByUserName(userName));
    }

    public Map<String, Object> getUserDetailByUserName(String userName) {
        Map<String, Object> json = new HashMap<String, Object>();
        try {
            BaseUser baseUserData = baseUserMapperEx.getUserByUserName(userName);
            //解密
            EncryptAndDecryptUtil.decryptBaseUser(baseUserData);
//        List<BaseRole> baseRoleListJwResponseData = baseRoleMapperEx.getRoleByUserName(userName);
            List<Map<String, Object>> baseMapListSystemAndRole = baseRoleMapperEx.getSystemAndRoleMapByUserId(baseUserData.getId());


            // Map json = new HashMap();
            json.put("id", baseUserData.getId());
            json.put("userName", baseUserData.getUserName());
            json.put("realName", baseUserData.getRealName());
            json.put("isValid", baseUserData.getIsValid());
            String depName = depService.getParentDepNames(baseUserData.getDepId());
            json.put("depName", depName == null ? "" : depName);

            json.put("locationName", baseUserData.getLocationName());
            json.put("isPublicUser", baseUserData.getIsPublicUser());
            json.put("depId", depName == null ? "" : baseUserData.getDepId());
            json.put("email", (baseUserData.getEmail() == null || baseUserData.getEmail().equals("null")) ? "" : baseUserData.getEmail());
            json.put("phone", (baseUserData.getPhone() == null || baseUserData.getPhone().equals("null")) ? "" : baseUserData.getPhone());
            json.put("companyName", (baseUserData.getCompanyName() == null || baseUserData.getCompanyName().equals("null")) ? "" : baseUserData.getCompanyName());
            json.put("dutyRole", (baseUserData.getDutyRole() == null || baseUserData.getDutyRole().equals("null")) ? "" : baseUserData.getDutyRole());

            StringBuilder roleNames = new StringBuilder();
            StringBuilder roleDescs = new StringBuilder();

            Map<String, List<Map<String, Object>>> resultMapSystemRole = new HashMap<>();

            //获取用户的角色
            StringBuilder roleAuthNames = new StringBuilder();
            StringBuilder roleAuthDescs = new StringBuilder();
            StringBuilder roleAuthIds = new StringBuilder();
            List<BaseRoleAuth> roleByUserId = baseRoleAuthService.getRoleByUserId(baseUserData.getId());
            if (!CollectionUtils.isEmpty(roleByUserId)) {
                for (BaseRoleAuth baseRoleAuth : roleByUserId) {
                    roleAuthNames.append(baseRoleAuth.getRoleName()).append(",");
                    roleAuthDescs.append(baseRoleAuth.getRoleDesc()).append(",");
                    roleAuthIds.append(baseRoleAuth.getId()).append(",");
                }
                if (roleAuthNames.toString().contains(",")) {
                    roleAuthNames = new StringBuilder(roleAuthNames.substring(0, roleAuthNames.length() - 1));
                    roleAuthDescs = new StringBuilder(roleAuthDescs.substring(0, roleAuthDescs.length() - 1));
                    roleAuthIds = new StringBuilder(roleAuthIds.substring(0, roleAuthIds.length() - 1));
                }
            }
            json.put("roleAuthNames", roleAuthNames.toString());
            json.put("roleAuthDescs", roleAuthDescs.toString());
            json.put("roleAuthIds", roleAuthIds.toString());

            Map<String, Map<String, Map<String, String>>> sysPermissionMap = basePermissionService.getSysPermissionByRole(baseUserData.getId());
            json.put("permissions", sysPermissionMap);

            Map<String, Map<String, Map<String, String>>> sysRuleMap = baseRuleService.getSysRule();
            json.put("rules", sysRuleMap);
            //规则
            for (Map<String, Object> map : baseMapListSystemAndRole) {
                roleNames.append(map.get("roleName")).append(",");
                roleDescs.append(map.get("roleDesc")).append(",");
                String sysRoleKey = "roles@" + map.get("sysName").toString();
                if (resultMapSystemRole.containsKey(sysRoleKey)) {
                    List<Map<String, Object>> list = resultMapSystemRole.get(sysRoleKey);
                    list.add(map);
                } else {
                    List<Map<String, Object>> list = new ArrayList<>();
                    list.add(map);
                    resultMapSystemRole.put(sysRoleKey, list);
                }
                log.info("map.get(\"roleKey\")" + map.get("roleKey"));
                if (map.get("roleKey") != null && !map.get("roleKey").equals("")) {
                    String roleIdKey = map.get("roleKey").toString() + "Id";
                    String roleId = "";
                    Object roleIdObj = json.get(roleIdKey);
                    if (roleIdObj != null)
                        roleId = (String) roleIdObj;

                    if (roleId.equals("")) {
                        roleId += map.get("id").toString();
                    } else {
                        roleId += "," + map.get("id").toString();
                    }
                    json.put(roleIdKey, roleId);

                    String roleNameKey = map.get("roleKey").toString() + "Name";
                    String roleName = "";
                    Object roleNameObj = json.get(roleNameKey);
                    if (roleNameObj != null)
                        roleName = (String) roleNameObj;

                    if (roleName.equals("")) {
                        roleName += map.get("roleName").toString();
                    } else {
                        roleName += "," + map.get("roleName").toString();
                    }
                    json.put(roleNameKey, roleName);
//                    json.put(map.get("roleKey").toString() + "Id", map.get("id").toString());
//                    json.put(map.get("roleKey").toString() + "Name", map.get("roleName").toString());
                }
            }
            if (!roleNames.toString().equals("")) {
                roleNames = new StringBuilder(roleNames.substring(0, roleNames.length() - 1));
                roleDescs = new StringBuilder(roleDescs.substring(0, roleDescs.length() - 1));
            }
            json.put("sysRoleMap", resultMapSystemRole);
            json.put("roleNames", roleNames.toString());
            json.put("roleDescs", roleDescs.toString());
        } catch (Exception e) {
            log.error("", e);
        }
        return json;
    }

    public JwResponseData<UserInfoRes> getUserInfoByUserNameFromAuth(String userName) {
        UserInfoRes res = new UserInfoRes();

        List<BaseUser> userList = baseUserMapperEx.selectByExample()
                .where(BaseUserDynamicSqlSupport.userName, isEqualTo(userName))
                .build().execute();
        if (CollectionUtils.isEmpty(userList)) {
            log.error("getUserByUserName is empty, userName:" + userName);
            return JwResponseData.error(JwResponseCode.DB_DATA_NOT_EXIST);
        }
        BaseUser baseUser = userList.get(0);
        String parentDepNames = depService.getParentDepNames(baseUser.getDepId());
        baseUser.setDepName(parentDepNames);
        res.setBaseUser(baseUser);

        List<BaseRole> roleByUserId = baseRoleMapperEx.getRoleByUserId(baseUser.getId());
        res.setRoles(roleByUserId);

        List<BaseRoleAuth> roleAuthByUserId = baseRoleAuthService.getRoleByUserId(baseUser.getId());
        res.setRoleAuths(roleAuthByUserId);

        List<BasePermission> permissionByUserId = basePermissionService.getPermissionByUserId(baseUser.getId());
        res.setPermissions(permissionByUserId);
        return JwResponseData.success("获取成功", res);

    }

    public Object login(Principal principal, @RequestParam Map<String, String> map, TokenEndpoint tokenEndpoint
    ) {
        try {
            if (!StringUtils.isEmpty(map.get("password")))
                return tokenEndpoint.postAccessToken(principal, map);

            String userName = map.get("username");
            map = new HashMap<>();
            map.put("username", userName);
            JwResponseData<BaseUser> getUserRes = this.getUserByUserName(userName);//dataAuthFeignClient.getUserByUserName(userName);
            if (!getUserRes.isSuccess()) {
                log.error("getUserByUserName request error");
                return getUserRes;
            }
            BaseUser data = getUserRes.getData();
            if (data == null){
                log.error("curr user is not exist userName:" + userName);
                return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs("当前用户不存在 name:" + userName));
            }

            String password = data.getPassword();
            // 通过当前用户名查询密码，加密的
            map.put("password", password);
            map.put("grant_type", "password");
            map.put("scope", "open_scope");
            map.put("client_id", "open_client");
            map.put("client_secret", "open_secret");
            ResponseEntity<OAuth2AccessToken> oAuth2AccessTokenResponseEntity = tokenEndpoint.postAccessToken(principal, map);
            String value = oAuth2AccessTokenResponseEntity.getBody().getValue();

            JwResponseData<Map<String, Object>> userByUserNameOauth = this.getUserDetails(userName);//dataAuthFeignClient.getUserDetails(userName);
            if (!userByUserNameOauth.isSuccess()) {
                log.error("request getUserByUserNameOauth error, userName:" + userName);
                return JwResponseData.error(JwResponseCode.SERVER_ERROR);
            }

            Map<String, Object> userDetails = userByUserNameOauth.getData();
            userDetails.put("accessToken", value);
            userDetails.put("instance", serverConfigHelper.getValue("system.instance"));
            userDetails.put("systemType", serverConfigHelper.getValue("system.instance"));
            userDetails.put("jwEnv", kdHelper.getJwEnv());
            return userDetails;
        } catch (HttpRequestMethodNotSupportedException e) {
            log.error("", e);
            return null;
        }
    }
}
