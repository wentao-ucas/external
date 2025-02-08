package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.*;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.controller.base.BaseBaseUserController;
import cn.cncc.caos.uaa.db.dao.BaseUserDynamicSqlSupport;
import cn.cncc.caos.uaa.db.daoex.BaseUserMapperEx;
import cn.cncc.caos.uaa.db.pojo.BaseUserRoleAuth;
import cn.cncc.caos.uaa.enums.CompanyType;
import cn.cncc.caos.uaa.helper.KDHelper;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.service.*;
import cn.cncc.caos.uaa.utils.EncryptAndDecryptUtil;
import cn.cncc.caos.uaa.utils.IdHelper;
import cn.cncc.caos.uaa.utils.KDUtil;
import cn.cncc.caos.uaa.utils.PushPortalInfoUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUserRelRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

@RestController
@Slf4j
public class BaseUserController extends BaseBaseUserController {

  @Resource
  private ServerConfigHelper serverConfigHelper;
  @Autowired
  private BaseUserMapperEx baseUserMapperEx;
  @Autowired
  private UserService userService;
//  @Autowired
//  private Environment env;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private PushPortalInfoUtil pushPortalInfoUtil;
//  @Value("${system.instance}")
//  private String systemInstance;
  @Resource
  private KDHelper kdHelper;

  @Resource
  private IdHelper idHelper;

  @Autowired
  private BaseSyncService baseSyncService;

  @Resource
  private BaseUserRoleAuthService baseUserRoleAuthService;

  @Resource
  private BaseUserRelRoleService baseUserRelRoleService;

  private static String operTypeAdd = "userAdd";
  private static String operTypeUpdate = "userUpdate";
  private static String operTypeDelete = "userDelete";
  private static String operTypeRestPwd = "restUserPwd";
  private static String operTypeCopyRoleAuth = "copyRoleAuth";
  private static String operTypeCopyRole = "copyRole";
  private static String operTypeDeleteRelRoleAuth = "deleteRelRoleAuth";

  private final String IOPS_OPERATOR = "门户推送";

  @Autowired
  private OperationHistoryService operationHistoryService;

  //====================================================微服务 start=========================================
  // @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
  //====================================================微服务 start=========================================

  //====================================================微服务 start=========================================
    /*@RequestMapping(value = "/api/user/get_user_by_name/{userName}", method = RequestMethod.GET)
    JwResponseData<BaseUser> getUserByUserName(@PathVariable("userName") String userName){
        // 调用FeignClient查询用户
        BaseUser baseUserData = baseUserMapperEx.getUserByUserName(userName);
        return JwResponseData.success("getUserByUserName获取成功",baseUserData);
    }*/

  // open_api/user/get_user_by_name给外部工具使用
  // 注意，提供wiki使用
  @RequestMapping(value = {"/open_api/user/get_user_by_name"}, method = RequestMethod.GET)
  public JwResponseData<BaseUser> getUserByUserName(@RequestParam(value = "userName") String userName) {
    return super.getUserByUserName(userName);
  }

  @RequestMapping(value = {"/open_api/user/get_user_by_real_name"}, method = RequestMethod.GET)
  public JwResponseData<BaseUser> getUserByRealName(@RequestParam(value = "realName") String realName) {
    return super.getUserByRealNameWithDep(realName);
  }

  @ResponseBody
  @PreAuthorize("hasAnyAuthority('user-relation-role-manage','system-external-rel','user-group-relation-user-manage')")
  @RequestMapping(value = {"/api/admin/user/get_users_name_all"}, method = RequestMethod.GET)
  public JwResponseData<List> getAllAdminUsers(@RequestParam(value = "searchString", required = false, defaultValue = "") String searchString) {
    try {
      List<BaseUser> list = baseUserMapperEx.selectByExample().where(BaseUserDynamicSqlSupport.isValid, isEqualTo(1)).orderBy(BaseUserDynamicSqlSupport.depId).build().execute();

      return JwResponseData.success("获得所有用户列表成功", super.getUsersNameFromUsersList(list, true));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = {"/open_api/user/all/info/get"}, method = RequestMethod.GET)
  public JwResponseData<List<Map<String, Object>>> getAllUserAllInfo() {
    try {
      List<Map<String, Object>> resp = userService.getAllUserAllInfo();
      return JwResponseData.success("获得所有用户信息列表成功", resp);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping(value = {"/open_api/user/get_users_name_all"}, method = RequestMethod.GET)
  public JwResponseData<List> getAllUsersNoPhone(@RequestParam(value = "searchString", required = false, defaultValue = "") String searchString) {
   return super.getAllUsersNoPhone(searchString);
  }

  @ResponseBody
  @PreAuthorize("hasAnyAuthority('daily-work-reminder-list','sms-send')")
  @RequestMapping(value = {"/open_api/user/get_users_phone_all"}, method = RequestMethod.GET)
  public JwResponseData<List> getAllUsers(@RequestParam(value = "searchString", required = false, defaultValue = "") String searchString) {
    return super.getAllUsers(searchString);
  }

  @ResponseBody
  @RequestMapping(value = {"/open_api/user/get_users_list_by_dep_id"}, method = RequestMethod.GET)
  public JwResponseData<List> getUsersByDepId(@RequestParam(value = "depId", required = false, defaultValue = "") String depId) {
    try {
      List<BaseUser> list = baseUserMapperEx.selectByExample()
          .where(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
          .and(BaseUserDynamicSqlSupport.depId, isEqualTo(Integer.parseInt(depId)))
          .and(BaseUserDynamicSqlSupport.isAdmin, isEqualTo(0), and(BaseUserDynamicSqlSupport.isPublicUser, isEqualTo(0)))
          .orderBy(BaseUserDynamicSqlSupport.depId).build().execute();

      return JwResponseData.success("获得所有用户列表成功", getUsersNameFromUsersList(list, false));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }



  @PreAuthorize("hasAnyAuthority('daily-work-reminder-list','sms-send')")
  @RequestMapping(value = "/open_api/user/get_users_by_role_id_list", method = RequestMethod.GET)
  public JwResponseData<List<BaseUser>> getUserListByRoleIdString(@RequestParam(value = "roleIdList", required = true) String roleIdListInput) {
    try {
      // 调用FeignClient查询用户
      roleIdListInput = roleIdListInput.replace(" ", "");

      String[] roleIdStringList = roleIdListInput.split(",");
      List<Integer> roleIdList = new ArrayList<>();
      for (String roleIdString : roleIdStringList) {
        roleIdList.add(Integer.parseInt(roleIdString));
      }
      List<BaseUser> list = baseUserMapperEx.selectUsersByRoleIdList(roleIdList);
      for (BaseUser baseUser : list) {
        //解密
        EncryptAndDecryptUtil.decryptBaseUser(baseUser);
        baseUser.setPassword("******");
      }
      log.info("get_users_by_role_id_list" + JacksonUtil.objToJson(list));
      return JwResponseData.success("getUsersByRoleIdList获取用户列表成功", list);
    } catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @RequestMapping(value = {"/open_api/user/get_users_by_role_name_list"}, method = RequestMethod.POST)
  public JwResponseData<List<BaseUser>> getUserListByRoleNameList(@RequestBody String roleNameListInput){
    return super.getUserListByRoleNameList(roleNameListInput);
  }

  @PreAuthorize("hasAnyAuthority('user-group-relation-user-manage','system-external-rel','operation_requirement')")
  @RequestMapping(value = "/api/admin/user/get_users_name_by_role_id", method = RequestMethod.GET)
  public JwResponseData<List> getUsersByRoleId(@RequestParam(value = "roleId", required = true) Integer roleId) {
    return super.getUsersByRoleId(roleId);
  }

//    @RequestMapping(value = "/api/admin/user/get_users_name_by_role_id", method = RequestMethod.GET)
//    JwResponseData<List<Map<String,Object>>> getUsersByRoleId(@RequestParam(value = "roleId", required = true) Integer roleId){
//        // 调用FeignClient查询用户
//        List<Map<String,Object>> list = baseUserMapperEx.selectUserNamesAndDepByRoleId(roleId);
//        return JwResponseData.success("getUsersByRoleId获取用户列表成功",list);
//    }

  //====================================================微服务 end=========================================
  //@PreAuthorize("hasRole('ROLE_SUPER_ADMINDD')")

  @PreAuthorize("hasAuthority('user-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/user/add_one", method = RequestMethod.POST)
  public JwResponseData<String> addOneUserJSONObject(@RequestBody Map jsonObject) {
    try {
      // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      BaseUser bu = new BaseUser();
      bu.setUserName(jsonObject.get("userName").toString());
      bu.setRealName(jsonObject.get("realName").toString());
      bu.setPassword(passwordEncoder.encode(serverConfigHelper.getValue("defaultUserPassword")));
      log.info("add one user set password");
      bu.setPhone(jsonObject.get("phone").toString());
      bu.setEmail(jsonObject.get("email").toString());
      bu.setImageUrl(jsonObject.get("imageUrl").toString());
      bu.setIsOnline(0);
      bu.setLocationName(jsonObject.get("locationName").toString());
      bu.setIsValid(Integer.parseInt(jsonObject.get("isValid").toString()));
      bu.setIsAdmin(Integer.parseInt(jsonObject.get("isAdmin").toString()));
      Date date = TimeUtil.getCurrentTime();
      bu.setCreateTime(date);
      bu.setUpdateTime(date);
      bu.setDepId(Integer.parseInt(jsonObject.get("depId").toString()));
      bu.setCompanyName(jsonObject.get("companyName").toString());
      bu.setIsPublicUser(0);
      CompanyType companyType = CompanyType.getCompanyTypeByName(bu.getCompanyName());
      if (companyType != null) {
        switch (companyType) {
          case SHIYE:
          case ZONG_ZHONG_XIN:
            bu.setDutyRole(CompanyType.SHIYE.name);
            break;
          case YINQIN:
          case JIN_XIN:
          case JIN_KE:
            bu.setDutyRole(CompanyType.YINQIN.name);
            break;
        }
      }
      //加密
      EncryptAndDecryptUtil.encryptBaseUser(bu);
      int resultId = baseUserMapperEx.insertAndGetId(bu);
      //根据username去查询，获取id组装，然后同步
      log.info("add user, " + JacksonUtil.objToJson(bu));
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      operationHistoryService.insertHistoryOper(user, operTypeAdd, JacksonUtil.objToJson(jsonObject));

      // 支付环境生产进行推送，办公网同步生产进行推送
      if ("cncc".equals(serverConfigHelper.getValue("system.instance")) && KDUtil.isNpc(kdHelper.getJwEnv())) {
        pushPortalInfoUtil.pushUserChange(bu, 1);
      } else {
      }

      return JwResponseData.success("新增用户成功");
    } catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping(value = "/open_api/admin/user/phone", method = RequestMethod.PUT)
  public JwResponseData<String> updateUserPhone(@RequestBody Map object) throws Exception {
    //TODO 校验权限
    try{
      Integer id = Integer.parseInt(String.valueOf(object.get("id")));
      String phone = String.valueOf(object.get("phone"));
      //增加操作记录
      BaseUser baseUser = UserHolder.getUser();
      if (baseUser == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("updateUserPhone operationUser:" + baseUser.getRealName() + ",content:" + JacksonUtil.objToJson(object));
      if (id == null || !StringUtil.isValidMobile(phone)) {
        return JwResponseData.error(JwResponseCode.BIND_ERROR, "确保id, phone参数正常");
      }
      try {
        BaseUser user = userService.changePhone(id, phone);
      } catch (Exception e) {
        return JwResponseData.error(JwResponseCode.BIND_ERROR, e.getMessage());
      }
      operationHistoryService.insertHistoryOper(baseUser, operTypeUpdate, JacksonUtil.objToJson(object));
      return JwResponseData.success("手机号设置成功");
    }catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }

  }


  @PreAuthorize("hasAuthority('user-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/user/update_one", method = RequestMethod.POST)
  public JwResponseData<String> updateOneUser(@RequestBody Map jsonObject) {
    try {
      BaseUser bu = new BaseUser();
      bu.setId(Integer.parseInt(jsonObject.get("id").toString()));
      bu.setUserName(jsonObject.get("userName").toString());
      bu.setRealName(jsonObject.get("realName").toString());
      // bu.setPassword(jsonObject.get("password").toString());
      String phone = jsonObject.get("phone").toString();
      cn.cncc.caos.uaa.utils.StringUtil.checkPhoneValid(jsonObject.get("userName").toString(), phone);
      bu.setPhone(phone);

      String email = jsonObject.get("email").toString();
      cn.cncc.caos.uaa.utils.StringUtil.checkEmailValid(jsonObject.get("userName").toString(), email);
      bu.setEmail(email);

      bu.setImageUrl(jsonObject.get("imageUrl").toString());
      bu.setIsValid(Integer.parseInt(jsonObject.get("isValid").toString()));
      bu.setIsAdmin(Integer.parseInt(jsonObject.get("isAdmin").toString()));
      bu.setLocationName(jsonObject.get("locationName").toString());
      bu.setUpdateTime(TimeUtil.getCurrentTime());
      bu.setDepId(Integer.parseInt(jsonObject.get("depId").toString()));
      bu.setCompanyName(jsonObject.get("companyName").toString());
      //加密
      EncryptAndDecryptUtil.encryptBaseUser(bu);
      bu.setUpdateTime(new Date());
      baseUserMapperEx.updateByPrimaryKeySelective(bu);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update user end operationUser:" + user.getRealName() + ",content:" + JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user, operTypeUpdate, JacksonUtil.objToJson(jsonObject));

      BaseUser pushBaseUser = baseUserMapperEx.getUserById(bu.getId());
      // 支付环境生产进行推送，办公网同步生产进行推送
      if ("cncc".equals(serverConfigHelper.getValue("system.instance")) && KDUtil.isNpc(kdHelper.getJwEnv())) {
        pushPortalInfoUtil.pushUserChange(pushBaseUser, 2);
      } else {
      }

      return JwResponseData.success("更新用户成功");
    } catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (BapParamsException jpe) {
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(jpe.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('user-manage')")
  @ResponseBody
  @RequestMapping(value = {"/api/admin/user/reset_one_user_pwd"
//            ,"/inner_api/user/reset_one_user_pwd"
  }, method = RequestMethod.POST)
  public JwResponseData<String> resetOneUserPWD(@RequestBody Map jsonObject) {
    try {
      BaseUser bu = new BaseUser();
      bu.setId(Integer.parseInt(jsonObject.get("id").toString()));
      bu.setPassword(passwordEncoder.encode(serverConfigHelper.getValue("defaultUserPassword")));
      log.info("password is update reset");
      bu.setUpdateTime(TimeUtil.getCurrentTime());
      baseUserMapperEx.updateByPrimaryKeySelective(bu);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("restPwd end operationUser:" + user.getRealName() + ",content:" + JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user, operTypeRestPwd, JacksonUtil.objToJson(jsonObject));

      // 支付环境生产进行推送，办公网同步生产进行推送
      BaseUser pushBaseUser = baseUserMapperEx.getUserById(bu.getId());
      if ("cncc".equals(serverConfigHelper.getValue("system.instance"))) {
        if (KDUtil.isNpc(kdHelper.getJwEnv())) {
          pushPortalInfoUtil.pushUpdateUserPwd(pushBaseUser);
        } else {
        }
      }
      return JwResponseData.success("重置用户密码成功");
    } catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping(value = {"/inner_api/user/reset_all_user_pwd_test_env"}, method = RequestMethod.POST)
  public JwResponseData<String> resetAllUserPWDTestEnv(@RequestBody Map jsonObject) {
    try {
      List<BaseUser> list = baseUserMapperEx.selectByExample().build().execute();
      for (BaseUser baseUser : list) {
        baseUser.setPassword(passwordEncoder.encode("111111"));
        baseUserMapperEx.updateByPrimaryKeySelective(baseUser);
      }
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      operationHistoryService.insertHistoryOper(user, operTypeRestPwd, JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("重置所有用户密码成功");
    } catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

//    //inner api 为了刷新所有用户密码
//    @ResponseBody
//    @PostMapping({"/inner_api/user/reset_all_user_pwd"})
//    public JwResponseData<String> resetAllUserPWD(@RequestBody Map jsonObject) {
//        List<BaseUser> list = baseUserMapperEx.selectByExample().build().execute();
//        for(BaseUser baseUser: list) {
//            if(baseUser.getPassword().length()<=15)
//                baseUser.setPassword(passwordEncoder.encode(baseUser.getPassword()));
//            baseUserMapperEx.updateByPrimaryKeySelective(baseUser);
//        }
//        return JwResponseData.success("重置所有用户密码成功");
//    }

  @PreAuthorize("hasAuthority('user-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/user/delete_one", method = RequestMethod.POST)
  public JwResponseData<String> deleteOneUserJSONObject(@RequestBody Map jsonObject) {
    try {
      BaseUser bu = new BaseUser();
      bu.setId(Integer.parseInt(jsonObject.get("id").toString()));
      bu.setIsValid(0);
      bu.setUpdateTime(TimeUtil.getCurrentTime());
      baseUserMapperEx.updateByPrimaryKeySelective(bu);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("delete user end operationUser:" + user.getRealName() + ",content:" + JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user, operTypeDelete, JacksonUtil.objToJson(jsonObject));

      // 支付环境生产进行推送，办公网同步生产进行推送
      BaseUser pushBaseUser = baseUserMapperEx.getUserById(bu.getId());
      if ("cncc".equals(serverConfigHelper.getValue("system.instance")) && KDUtil.isNpc(kdHelper.getJwEnv())) {
        pushPortalInfoUtil.pushUserChange(pushBaseUser, 2);
      } else {
      }
      return JwResponseData.success("删除用户成功");
    } catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping(value = {"/inner_api/user/encrept_all_user_phone"}, method = RequestMethod.PUT)
  @Operation(summary = "加密所有手机号")
  public JwResponseData<Object> encreptAllUserPhone() {
    //查询所有的baseuser
    List<BaseUser> baseUserList = baseUserMapperEx.selectAll();
    //遍历加密
    for (BaseUser baseUser : baseUserList) {
      EncryptAndDecryptUtil.encryptBaseUser(baseUser);
      baseUserMapperEx.updateByPrimaryKey(baseUser);
    }
    return JwResponseData.success("加密成功");
    //批量更新
//    baseUserMapperEx.updateBaseUserList(baseUserList);
  }


  @ResponseBody
  @RequestMapping(value = {"/inner_api/user/decrept_all_user_phone"}, method = RequestMethod.PUT)
  @Operation(summary = "解密所有手机号")
  public JwResponseData<Object> decreptAllUserPhone() {
    //查询所有的baseuser
    List<BaseUser> baseUserList = baseUserMapperEx.selectAll();
    //遍历解密
    for (BaseUser baseUser : baseUserList) {
      EncryptAndDecryptUtil.decryptBaseUser(baseUser);
      baseUserMapperEx.updateByPrimaryKey(baseUser);
    }
    return JwResponseData.success("解密成功");
    //批量更新
//    baseUserMapperEx.updateBaseUserList(baseUserList);
  }

//  @RequestMapping(value = { "/open_api/user/get_user_by_real_name"}, method = RequestMethod.GET)
//  public JwResponseData<BaseUser> getUserByRealName(@RequestParam(value = "realName") String realName) {
//    return super.getUserByRealName(realName);
//  }


  /**
   * @Description 获取审批节点审批人联系人.
   * @Param null.
   * @Return cn.cncc.caos.common.core.response.jw.JwResponseData<java.util.List                                                                                                                               <                                                                                                                               java.util.Map                                                                                                                               <                                                                                                                               java.lang.String                                                                                                                               ,                                                                                                                               java.lang.String>>> 审批人联系信息结果集.
   **/
  @PreAuthorize("hasAnyAuthority('process_platform')")
  @Operation(summary = "获取审批操作人联系信息")
  @RequestMapping(value = {"/open_api/user/get_users_mobile_list"}, method = RequestMethod.GET)
  public JwResponseData<List<Map<String, String>>> getUserMobileList() {
    try {
      List<Map<String, String>> userMobileList = userService.getUserMobileList();

      log.info("get_users_mobile_list:{}", JacksonUtil.objToJson(userMobileList));
      return JwResponseData.success("getUserMobileList获取审批人联系方式成功", userMobileList);
    } catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping(value = "/inner_api/admin/user/iops/get", method = RequestMethod.GET)
  public JwResponseData<Map<String, Object>> iopsGet() {
    try {
      baseSyncService.scheduleTaskUserSync();
    } catch (Exception e) {
      e.printStackTrace();
      return JwResponseData.error(JwResponseCode.SERVER_ERROR.fillArgs("获取信息失败"));
    }
    return null;
  }

  @RequestMapping(value = {"/open_api/user/get_user_phone_list_by_real_name_list"}, method = RequestMethod.GET)
  public JwResponseData<String> getUserPhonesByUserRealNames(@RequestParam(value = "useRealNames") String useRealNames) {
    return super.getUserPhonesByUserRealNames(useRealNames);
  }

  @PreAuthorize("hasAuthority('user-manage')")
  @RequestMapping(value = "/open_api/user/copy/group", method = RequestMethod.POST)
  public JwResponseData<String> copyUserGroupAndRole(@RequestParam(value = "sourceId") Integer sourceId,
                                                   @RequestParam(value = "targetId") Integer targetId) {
    try {
      BaseUser user = UserHolder.getUser();

      // 删除targetId已关联的角色、用户组
      baseUserRoleAuthService.deleteByUser(targetId);
      baseUserRelRoleService.deleteByUser(targetId);
      operationHistoryService.insertHistoryOper(user, operTypeDeleteRelRoleAuth, Integer.toString(targetId));

      List<BaseUserRoleAuth> baseUserRoleAuthList = baseUserRoleAuthService.selectRoleAuthByUser(sourceId);
      List<BaseUserRoleAuth> targetBaseUserRoleAuthList = new ArrayList<>();
      for (BaseUserRoleAuth baseUserRoleAuth : baseUserRoleAuthList) {
        BaseUserRoleAuth baseUserRoleAuthCopy = new BaseUserRoleAuth();
        BeanUtils.copyProperties(baseUserRoleAuth, baseUserRoleAuthCopy);
        baseUserRoleAuthCopy.setUserId(targetId);
        baseUserRoleAuthCopy.setId(idHelper.generateUserRoleAuthId());
        Date currentTime = TimeUtil.getCurrentTime();
        baseUserRoleAuthCopy.setCreateTime(currentTime);
        baseUserRoleAuthCopy.setUpdateTime(currentTime);
        targetBaseUserRoleAuthList.add(baseUserRoleAuthCopy);
      }
      if (!CollectionUtils.isEmpty(targetBaseUserRoleAuthList)) {
        // 入库
        baseUserRoleAuthService.batchInsert(targetBaseUserRoleAuthList);
        // 操作记录
        operationHistoryService.insertHistoryOper(user, operTypeCopyRoleAuth, JacksonUtil.objToJson(targetBaseUserRoleAuthList));
      }

      List<BaseUserRelRole> baseUserRelRoleList = baseUserRelRoleService.selectRoleByUser(sourceId);
      List<BaseUserRelRole> targetBaseUserRelRoleList = new ArrayList<>();
      for (BaseUserRelRole baseUserRelRole : baseUserRelRoleList) {
        BaseUserRelRole baseUserRelRoleCopy = new BaseUserRelRole();
        BeanUtils.copyProperties(baseUserRelRole, baseUserRelRoleCopy);
        baseUserRelRoleCopy.setUpdateTime(TimeUtil.getCurrentTime());
        baseUserRelRoleCopy.setUserId(targetId);
        baseUserRelRoleCopy.setId(null);
        targetBaseUserRelRoleList.add(baseUserRelRoleCopy);
      }
      if (!CollectionUtils.isEmpty(targetBaseUserRelRoleList)) {
        // 入库
        baseUserRelRoleService.batchInsert(targetBaseUserRelRoleList);
        // 操作记录
        operationHistoryService.insertHistoryOper(user, operTypeCopyRole, JacksonUtil.objToJson(targetBaseUserRelRoleList));
      }
      return JwResponseData.success("复制成功");
    } catch (JsonProcessingException e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
}

