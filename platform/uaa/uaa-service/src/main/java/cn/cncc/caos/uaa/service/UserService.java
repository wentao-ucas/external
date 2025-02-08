package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BasePermission;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUserAndRoleName;
import cn.cncc.caos.uaa.db.dao.BaseUserDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseUserMapper;
import cn.cncc.caos.uaa.db.daoex.BaseRoleMapperEx;
import cn.cncc.caos.uaa.db.daoex.BaseUserExtendMapperEx;
import cn.cncc.caos.uaa.db.daoex.BaseUserMapperEx;
import cn.cncc.caos.uaa.enums.CompanyType;
import cn.cncc.caos.uaa.utils.EncryptAndDecryptUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;

@Service
@Slf4j
public class UserService {
  @Resource
  private BaseUserMapper baseUserMapper;
  @Resource
  private BaseUserMapperEx baseUserMapperEx;
  @Autowired
  private BaseUserExtendMapperEx baseUserExtendMapperEx;
  @Autowired
  private DepService depService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private OperationHistoryService operationHistoryService;
  //  @Autowired
//  private RestTemplate restTemplate;
//  @Autowired
//  private Environment env;
  @Autowired
  private BaseRoleMapperEx baseRoleMapperEx;
  @Autowired
  private BasePermissionService basePermissionService;


  public void batchUpdate(List<BaseUser> baseUserList) {
//    baseUserDataSyncExecutor.batchUpdate(baseUserList);
  }


  public void batchUpdateIops(List<BaseUser> baseUserList) {
//    baseUserDataSyncExecutor.batchUpdateIops(baseUserList);
  }

  public BaseUser changePhone(Integer id, String phone) {
    BaseUser user = baseUserMapper.selectByPrimaryKey(id);
    if (user == null) {
      throw new BapParamsException("user not exist " + id);
    }
    user.setPhone(phone);
    user.setUpdateTime(new Date());
    //加密
    EncryptAndDecryptUtil.encryptBaseUser(user);
    baseUserMapper.updateByPrimaryKey(user);
    return user;
  }

  public List<BaseUser> getUserByPhone(String phoneNumber) {
    return baseUserMapper.selectByPhone(phoneNumber);
  }

  public BaseUser getUserByRealName(String realName) {
    List<BaseUser> execute = baseUserMapper.selectByExample().
        where(BaseUserDynamicSqlSupport.realName, isEqualTo(realName)).build().execute();
    if (CollectionUtils.isEmpty(execute)) {
      return null;
    }
    return execute.get(0);
  }

  public void getUserByIds(Set<Integer> assigneeIds, Map<Integer, BaseUser> idUserMap) {
    List<BaseUser> baseUserList = baseUserMapper.selectByExample()
        .where(BaseUserDynamicSqlSupport.id, isIn(assigneeIds))
        .and(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
        .build().execute();
    if (CollectionUtils.isEmpty(baseUserList))
      return;
    baseUserList.forEach(baseUser -> {
      String decryptPhone = EncryptAndDecryptUtil.decryptString(baseUser.getPhone());
      baseUser.setPhone(decryptPhone);
      idUserMap.put(baseUser.getId(), baseUser);
    });
  }

  public void getUserAndRoleNameByGroups(Set<String> assigneeGroups, Map<String, List<BaseUser>> groupUserListMap) {
    List<String> groupList = new ArrayList<>(assigneeGroups);
    List<BaseUserAndRoleName> baseUserAndRoleNames = baseUserMapperEx.selectUsersByRoleNameList(groupList);
    if (CollectionUtils.isEmpty(baseUserAndRoleNames))
      return;
    for (BaseUserAndRoleName baseUserAndRoleName : baseUserAndRoleNames) {
      BaseUser baseUser = new BaseUser();
      BeanUtils.copyProperties(baseUserAndRoleName, baseUser);
      String phone = baseUserAndRoleName.getPhone();
      String decryptString = EncryptAndDecryptUtil.decryptString(phone);
      baseUser.setPhone(decryptString);
      String roleName = baseUserAndRoleName.getRoleName();
      if (groupUserListMap.containsKey(roleName)) {
        groupUserListMap.get(roleName).add(baseUser);
      } else {
        List<BaseUser> baseUserList = new ArrayList<>();
        baseUserList.add(baseUser);
        groupUserListMap.put(roleName, baseUserList);
      }
    }
  }

  /**
   * @Description 根据部门id查询所有部门成员.
   * @Param depId 部门id.
   * @Return java.util.List<cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser> 所有成员信息.
   **/
  public List<BaseUser> getUserByDepId(Integer depId) {
    List<BaseUser> userList = baseUserMapperEx.selectByExample()
        .where(BaseUserDynamicSqlSupport.depId, isEqualTo(depId))
        .and(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
        .build().execute();
    return userList;
  }

  /**
   * 根据部门id查询所有部门成员, 返回Map
   */
  public List<Map<String, Object>> getUserMapByDepId(Integer depId) {
    List<Map<String, Object>> userList = baseUserMapperEx.getUserMapByDepId(depId);
    // 整理返回结果
    this.userMapRetJoin(userList);
    return userList;
  }

  /**
   * 根据部门id查询所有部门成员, 返回Map
   */
  public List<Map<String, Object>> getUserMapByDepIdList(List<Integer> depIdList) {
    List<Map<String, Object>> userList = baseUserMapperEx.getUserMapByDepIdList1(depIdList);
    return userList;
  }

  /**
   * 根据部门id查询所有部门成员, 返回Map
   */
  public List<Map<String, Object>> getUserMapByDepIdList1(List<Integer> depIdList) {
//    List<Map<String, Object>> userList = baseUserMapperEx.getUserMapByDepIdList(depIdList);
    List<Map<String, Object>> userList = baseUserMapperEx.getUserMapByDepIdList1(depIdList);
    return userList;
  }

  /**
   * @Description 根据申请人姓名, 是否主值等信息查询对应满足条件列表.
   * @Param applyName 申请人真实姓名.
   * @Param isPri 是否主值 Y:主值 N:非主值.
   * @Return java.util.List<Map < String, Object>> 查询返回条件列表.
   **/
  public List<Map<String, Object>> getUserListByManagerName(String applyName, String isPri) {
    List<Map<String, Object>> list = new ArrayList<>();
    // 获取申请人相关信息
    BaseUser applyUser = baseUserMapperEx.getBaseUserByRealName(applyName);

    if ("Y".equals(isPri)) {
      log.info("当前用户:{},有主值班经理资格", applyName);
      // scene==1 值班辅助系统-值班经理场景
      list = baseUserExtendMapperEx.getManagerUserInfoWithPri(1, 1, applyUser.getLocationName(), applyUser.getRealName());
    } else {
      log.info("当前用户:{},没有主值班经理资格", applyName);
      // scene==1 值班辅助系统-值班经理场景
      list = baseUserExtendMapperEx.getManagerUserInfo(1, 1, applyUser.getLocationName(), applyUser.getRealName());
    }
    // 整理返回结果
    this.userMapRetJoin(list);
    return list;
  }

  // 查询给定列表姓名,是否具有值班经理主值资格,如果有则返回对应名称
  public List<String> getPriUserListByRealNameList(List<String> realNameList) {
    List<String> priList = baseUserExtendMapperEx.getPriUserListByRealNameList(realNameList);
    return priList;
  }


  public List<Map<String, Object>> getCfidUserListByManagerName(String flag) {
    List<Map<String, Object>> list = new ArrayList<>();
    if ("1".equals(flag)) { // 非支付适配值班经理资源池
      log.info("非支付-值班经理换班-适用值班经理资源池用户,适用标识:{}", flag);
      list = baseUserExtendMapperEx.getCfidManagerUserInfoWithFlag(1, 1);
    } else { // 非支付不适配值班经理资源池信息适用非支付所有用户
      log.info("非支付-值班经理换班-不适用值班经理资源池用户,适用标识:{}", flag);
      list = baseUserExtendMapperEx.getCfidManagerUserInfo(1);
    }
    // 整理返回结果
    this.userMapRetJoin(list);
    return list;
  }

  private void userMapRetJoin(List<Map<String, Object>> list) {
    // 整理返回结果
    Map<Object, Object> depMap = depService.getDepMap();
    for (Map<String, Object> map : list) {
      String decPhone = EncryptAndDecryptUtil.decryptString((String) map.get("phone"));
      map.put("phone", decPhone);
      Object depName = depMap.get(String.valueOf(map.get("depId")));
      map.put("depName", depName == null ? "" : depName);
      map.put("companyName", map.get("companyName") == null ? "" : map.get("companyName"));
    }
  }

  /**
   * @Description 获取审批人联系信息.
   * @Return java.util.List<java.util.Map < java.lang.String, java.lang.String>> 审批人联系结果.
   **/
  public List<Map<String, String>> getUserMobileList() throws JsonProcessingException {
    // 获取申请人相关信息  1:审批人联系信息
    List<Map<String, String>> userMobileList = baseUserExtendMapperEx.getUserMobileList(1, 1);
    log.info("获取审批人联系方式信息结果为:{}", JacksonUtil.objToJson(userMobileList));
    for (Map map : userMobileList) {
      String phone = (String) map.get("phone");
      // 解密电话号
      String decryptPhone = EncryptAndDecryptUtil.decryptString(phone);
      map.put("phone", decryptPhone);
    }
    return userMobileList;
  }

  public BaseUser getUserByUserName(String userName) {
    BaseUser baseUser = baseUserMapperEx.getUserByUserName(userName);
    return baseUser;
  }

  public BaseUser getUserByUserNameIsNullError(String userName) {
    BaseUser baseUser = baseUserMapperEx.getUserByUserName(userName);
    if (baseUser == null) {
      throw new BapParamsException(String.format("账号不存在, userName: %s", userName));
    }
    return baseUser;
  }

  public Map<String, BaseUser> getUserByRealNames(List<String> req) {
    List<BaseUser> list = baseUserMapper.selectByExample()
        .where(BaseUserDynamicSqlSupport.realName, isIn(req))
        .and(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
        .build().execute();
    if (CollectionUtils.isEmpty(list))
      return null;
    Map<String, BaseUser> result = new HashMap<>();
    for (BaseUser baseUser : list) {
      BaseUser user = new BaseUser();
      BeanUtils.copyProperties(baseUser, user);
      String parentDepNames = depService.getParentDepNames(baseUser.getDepId());
      user.setDepName(parentDepNames);
      result.put(baseUser.getRealName(), user);
    }
    return result;
  }


  /**
   * 对外提供修改用户信息接口
   *
   * @param baseUser
   * @return
   */
  public BaseUser apiUpdateOneUser(BaseUser baseUser, String operator) {
    BaseUser selectUser = getUserByUserNameIsNullError(baseUser.getUserName());
    if (!selectUser.getRealName().equals(baseUser.getRealName())) {
      BaseUser userByRealName = getUserByRealName(baseUser.getRealName());
      if (userByRealName != null) {
        throw new BapParamsException(String.format("修改用户名已存在,不可重复, realName: %s", baseUser.getRealName()));
      }
    }
    baseUser.setId(selectUser.getId());
    baseUser.setUpdateTime(new Date());
    EncryptAndDecryptUtil.encryptBaseUser(baseUser);
    baseUserMapperEx.updateByPrimaryKeySelective(baseUser);
    this.insertHistoryOper("apiUpdateOneUser", operator, baseUser.toString());
    return baseUser;
  }

  /**
   * 修改用户密码
   *
   * @param userName
   * @param password
   */
  public void updateOneUserPass(String userName, String password, String operator) {
    BaseUser selectUser = this.getUserByUserNameIsNullError(userName);
    BaseUser updateUser = new BaseUser();
    updateUser.setId(selectUser.getId());
    updateUser.setPassword(passwordEncoder.encode(password));
    updateUser.setUpdateTime(new Date());
    baseUserMapperEx.updateByPrimaryKeySelective(updateUser);
    this.insertHistoryOper("updateOneUserPass", operator, userName);
  }

  public void apiDeleteOneUser(String userName, String operator) {
    BaseUser selectUser = getUserByUserNameIsNullError(userName);
    BaseUser updateUser = new BaseUser();
    updateUser.setId(selectUser.getId());
    updateUser.setIsValid(0);
    updateUser.setUpdateTime(TimeUtil.getCurrentTime());
    baseUserMapperEx.updateByPrimaryKeySelective(updateUser);
    this.insertHistoryOper("apiDeleteOneUser", operator, userName);
  }

  public void apiAddOneUser(BaseUser baseUser, String operator) {
    BaseUser userByUserName = getUserByUserName(baseUser.getUserName());
    if (!ObjectUtils.isEmpty(userByUserName) && userByUserName.getIsValid() != 0) {
      throw new BapParamsException("账号已存在,不可重复");
    }
    BaseUser userByRealName = getUserByRealName(baseUser.getRealName());
    if (userByRealName != null && userByRealName.getIsValid() != 0) {
      throw new BapParamsException("姓名已存在,不可重复");
    }
    String dutyRole = CompanyType.companyTypeToName(baseUser.getCompanyName());
    baseUser.setDutyRole(dutyRole);
    baseUser.setPassword(passwordEncoder.encode(baseUser.getPassword()));
    EncryptAndDecryptUtil.encryptBaseUser(baseUser);
    // 无效用户表示删除用户，需要重新启用
    if (!ObjectUtils.isEmpty(userByUserName.getIsValid()) && userByUserName.getIsValid().equals(0)) {
      baseUser.setId(userByUserName.getId());
      baseUserMapperEx.updateByPrimaryKey(baseUser);
    } else {
      baseUserMapperEx.insertAndGetId(baseUser);
    }
    this.insertHistoryOper("apiAddOneUser", operator, baseUser.toString());
  }

  private void insertHistoryOper(String operType, String operator, String content) {
    //增加操作记录
    BaseUser user = new BaseUser();
    user.setRealName(operator);
    user.setUserName(operator);
    operationHistoryService.insertHistoryOper(user, operType, content);
  }


  @Transactional(readOnly = true)
  public String candidateGroups(String userRealName) {
    BaseUser baseUser = this.getUserByRealName(userRealName);
    StringBuffer candidateGroups = new StringBuffer();
    List<Map<String, Object>> baseMapListSystemAndRole = baseRoleMapperEx.getSystemAndRoleMapByUserId(baseUser.getId());
    for (Map<String, Object> map : baseMapListSystemAndRole) {
      Object roleName = map.get("roleName");
      candidateGroups = candidateGroups.append(roleName).append(",");
    }
    List<BasePermission> permissionByUserId = basePermissionService.getPermissionByUserId(baseUser.getId());
    for (BasePermission basePermission : permissionByUserId) {
      candidateGroups.append(basePermission.getName()).append(",");
    }
    return candidateGroups.toString();
  }


  public BaseUser getUserByRealNameAndDepId(Integer depId, String realName) {
    List<BaseUser> execute = baseUserMapper.selectByExample()
        .where(BaseUserDynamicSqlSupport.depId, isEqualTo(depId))
        .and(BaseUserDynamicSqlSupport.realName, isEqualTo(realName))
        .and(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
        .build().execute();

    if (CollectionUtils.isEmpty(execute))
      return null;
    return execute.get(0);
  }

  public List<BaseUser> getUserByDepIdAndLocalhostName(List<Integer> depIdList, String localhostName) {
    return baseUserMapper.selectByExample()
        .where(BaseUserDynamicSqlSupport.depId, isIn(depIdList))
        .and(BaseUserDynamicSqlSupport.locationName, isEqualTo(localhostName))
        .and(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
        .build().execute();
  }

  public List<BaseUser> getUserByDepIdsAndLocalhostNames(List<Integer> depIdList, List<String> localhostNames) {
    return baseUserMapper.selectByExample()
        .where(BaseUserDynamicSqlSupport.depId, isIn(depIdList))
        .and(BaseUserDynamicSqlSupport.locationName, isIn(localhostNames))
        .and(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
        .build().execute();
  }

  public List<Map<String, Object>> getAllUserAllInfo() {
    List<BaseUser> list = baseUserMapperEx.selectByExample()
        .where(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
        .and(BaseUserDynamicSqlSupport.isAdmin, isEqualTo(0))
        .and(BaseUserDynamicSqlSupport.isPublicUser, isEqualTo(0))
        .orderBy(BaseUserDynamicSqlSupport.depId)
        .build().execute();
    Map<Object, Object> map = depService.getDepMap();
    List<Map<String, Object>> respList = new ArrayList();
    for (BaseUser one : list) {
      EncryptAndDecryptUtil.decryptBaseUser(one);
      Map<String, Object> item = new HashMap();
      item.put("id", one.getId());
      item.put("realName", one.getRealName());
      item.put("locationName", one.getLocationName() == null ? "" : one.getLocationName());
      item.put("phone", one.getPhone() == null ? "" : one.getPhone());
      item.put("email", one.getEmail() == null ? "" : one.getEmail());
      Object depName = map.get(one.getDepId().toString());
      item.put("depName", depName == null ? "" : depName);
      respList.add(item);
    }
    return respList;
  }

}
