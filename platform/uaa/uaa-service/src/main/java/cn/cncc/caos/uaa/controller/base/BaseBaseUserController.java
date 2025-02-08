package cn.cncc.caos.uaa.controller.base;

import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.KDConstant;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.dao.BaseUserDynamicSqlSupport;
import cn.cncc.caos.uaa.db.daoex.BaseUserMapperEx;
import cn.cncc.caos.uaa.helper.KDHelper;
import cn.cncc.caos.uaa.service.BaseSyncService;
import cn.cncc.caos.uaa.service.DepService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import cn.cncc.caos.uaa.service.UserService;
import cn.cncc.caos.uaa.utils.EncryptAndDecryptUtil;
import cn.cncc.caos.uaa.utils.PushPortalInfoUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUserAndRoleName;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

//@RestController
@Slf4j
public abstract class BaseBaseUserController  {

  @Resource
  private ServerConfigHelper serverConfigHelper;
  @Autowired
  private BaseUserMapperEx baseUserMapperEx;
  //private BaseUserMapper baseUserMapper;
  @Autowired
  private UserService userService;
  @Autowired
  private DepService depService;
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
  @Autowired
  private BaseSyncService baseSyncService;

  private String operTypeAdd = "userAdd";
  private String operTypeUpdate = "userUpdate";
  private String operTypeDelete = "userDelete";
  private String operTypeRestPwd = "restUserPwd";

  private final String IOPS_OPERATOR = "门户推送";

  @Autowired
  private OperationHistoryService operationHistoryService;

//  @RequestMapping(value = {"/inner_api/user/get_user_by_name", "/open_api/user/get_user_by_name"}, method = RequestMethod.GET)
  public JwResponseData<BaseUser> getUserByUserName(@RequestParam(value = "userName") String userName) {
    try {
      // 调用FeignClient查询用户
      BaseUser baseUserData = baseUserMapperEx.getUserByUserName(userName);
      if (baseUserData == null)
        return JwResponseData.error(JwResponseCode.DB_DATA_NOT_EXIST);
      //解密
      EncryptAndDecryptUtil.decryptBaseUser(baseUserData);
      String parentDepNames = depService.getParentDepNames(baseUserData.getDepId());
      baseUserData.setDepName(parentDepNames);
      return JwResponseData.success("getUserByUserName获取成功", baseUserData);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

//  @RequestMapping(value = {"/open_api/user/get_users_by_role_name_list", "/inner_api/user/get_users_by_role_name_list"}, method = RequestMethod.POST)
  public JwResponseData<List<BaseUser>> getUserListByRoleNameList(@RequestBody String roleNameListInput) {
    try {
      // 调用FeignClient查询用户
      roleNameListInput = roleNameListInput.replace(" ", "");

      String[] roleNameStringList = roleNameListInput.split(",");
      List<String> roleNameList = Arrays.asList(roleNameStringList);
      List<BaseUserAndRoleName> list = baseUserMapperEx.selectUsersByRoleNameList(roleNameList);
      //获取到list后，新建list，遍历rolenamelist，然后再循环中遍历baseuserandrolenamelist，
      List<BaseUser> baseUserList = new ArrayList<>();
      if (!(CollectionUtils.isEmpty(roleNameList) || CollectionUtils.isEmpty(list))) {
        for (String s : roleNameList) {
          for (BaseUserAndRoleName baseUserAndRoleName : list) {
            if (s.equalsIgnoreCase(baseUserAndRoleName.getRoleName())) {
              BaseUser baseUser = new BaseUser();
              BeanUtils.copyProperties(baseUserAndRoleName, baseUser);
              baseUserList.add(baseUser);
            }
          }
        }
      }
      for (BaseUser baseUser : baseUserList) {
        //解密
        EncryptAndDecryptUtil.decryptBaseUser(baseUser);
        baseUser.setPassword("******");
      }
      log.info("get_users_by_role_id_list" + JacksonUtil.objToJson(baseUserList));
      return JwResponseData.success("getUsersByRoleIdList获取用户列表成功", baseUserList);
    } catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

//  @RequestMapping(value = {"/inner_api/user/get_user_by_real_name", "/open_api/user/get_user_by_real_name"}, method = RequestMethod.GET)
  public JwResponseData<BaseUser> getUserByRealName(@RequestParam(value = "realName") String realName) {
    try {
      // 调用FeignClient查询用户
      BaseUser baseUserData = userService.getUserByRealName(realName);
      //解密
      if (baseUserData == null)
        return JwResponseData.success("获取用户成功", baseUserData);
      EncryptAndDecryptUtil.decryptBaseUser(baseUserData);
      return JwResponseData.success("getUserByUserName获取成功", baseUserData);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  public JwResponseData<BaseUser> getUserByRealNameWithDep(@RequestParam(value = "realName") String realName) {
    try {
      // 调用FeignClient查询用户
      BaseUser baseUserData = userService.getUserByRealName(realName);
      //解密
      if (baseUserData == null)
        return JwResponseData.success("获取用户成功", baseUserData);
      EncryptAndDecryptUtil.decryptBaseUser(baseUserData);
      Map<Object, Object> map = depService.getDepMap();
      Object depName = map.get(baseUserData.getDepId().toString());
      baseUserData.setDepName(depName == null ? "" : depName.toString());
      return JwResponseData.success("getUserByUserName获取成功", baseUserData);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  public List<Map> getUsersNameFromUsersList(List<BaseUser> list, boolean flag) {

    Map<Object, Object> map = depService.getDepMap();

    List jsonArray = new ArrayList();
    for (BaseUser one : list) {
      //解密
      EncryptAndDecryptUtil.decryptBaseUser(one);
      Map j = new HashMap();
      j.put("id", one.getId());
      j.put("userName", one.getUserName());
      j.put("realName", one.getRealName());
      j.put("locationName", one.getLocationName());
      if (flag) {
        j.put("phone", one.getPhone());
      } else {
        j.put("phone", "");
      }
      Integer depId = one.getDepId();
      j.put("depId", depId);
      Object depName = map.get(depId.toString());
      j.put("depName", depName == null ? "" : depName);
      j.put("companyName", one.getCompanyName() == null ? "" : one.getCompanyName());
      jsonArray.add(j);
    }
    return jsonArray;
  }

  @ResponseBody
//  @GetMapping({"/open_api/user/get_users_name_all", "/inner_api/user/get_users_name_all"})
  public JwResponseData<List> getAllUsersNoPhone(@RequestParam(value = "searchString", required = false, defaultValue = "") String searchString) {
    try {
      List<BaseUser> list = baseUserMapperEx.selectByExample()
              .where(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
              .and(BaseUserDynamicSqlSupport.isAdmin, isEqualTo(0), and(BaseUserDynamicSqlSupport.isPublicUser, isEqualTo(0)))
              .orderBy(BaseUserDynamicSqlSupport.depId).build().execute();

      return JwResponseData.success("获得所有用户列表成功", getUsersNameFromUsersList(list, false));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
//  @GetMapping({"/open_api/user/get_users_name_all", "/inner_api/user/get_users_name_all"})
  public JwResponseData<List> getAllUsers(@RequestParam(value = "searchString", required = false, defaultValue = "") String searchString) {
    try {
      List<BaseUser> list = baseUserMapperEx.selectByExample()
              .where(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
              .and(BaseUserDynamicSqlSupport.isAdmin, isEqualTo(0), and(BaseUserDynamicSqlSupport.isPublicUser, isEqualTo(0)))
              .orderBy(BaseUserDynamicSqlSupport.depId).build().execute();

      return JwResponseData.success("获得所有用户列表成功", getUsersNameFromUsersList(list, true));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  protected JwResponseData<String> getUserPhonesByUserRealNames(String useRealNames) {
    try {
      // 调用FeignClient查询用户
      List<BaseUser> list = baseUserMapperEx.selectByExample()
              .where(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
              .and(BaseUserDynamicSqlSupport.realName, isIn(Arrays.asList(useRealNames.split(",")))).build().execute();
      List<String> phoneList = new ArrayList<>();
      for (BaseUser baseUser : list) {
        //解密
        EncryptAndDecryptUtil.decryptBaseUser(baseUser);
        if (StringUtils.isNotEmpty(baseUser.getPhone()) && !baseUser.getPhone().equals(KDConstant.PHONE_DEFAULT))
          phoneList.add(baseUser.getPhone());
      }
      String phonesResult = StringUtils.join(phoneList.toArray(), ",");
      return JwResponseData.success("getUserByUserName获取成功", phonesResult);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  protected JwResponseData<List> getUsersByRoleId(@RequestParam(value = "roleId", required = true) Integer roleId) {
    try {
      // 调用FeignClient查询用户
      List<BaseUser> list = baseUserMapperEx.selectUsersByRoleId(roleId);
      return JwResponseData.success("getUsersByRoleId获取用户列表成功", getUsersNameFromUsersList(list, true));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
}

