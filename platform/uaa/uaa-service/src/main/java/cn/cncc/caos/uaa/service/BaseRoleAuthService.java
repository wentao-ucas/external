package cn.cncc.caos.uaa.service;


import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.dao.*;
import cn.cncc.caos.uaa.db.daoex.BaseRoleAuthMapperEx;
import cn.cncc.caos.uaa.model.role.auth.BaseRoleAuthReq;
import cn.cncc.caos.uaa.model.role.auth.BaseRoleAuthUpdateReq;
import cn.cncc.caos.platform.uaa.client.api.DataAuthResponseCode;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRoleAuth;
import cn.cncc.caos.uaa.utils.IdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Slf4j
@Service
public class BaseRoleAuthService {

  @Autowired
  private BaseRoleAuthMapper baseRoleAuthMapper;

  @Autowired
  private BaseRoleAuthPermissionMapper baseRoleAuthPermissionMapper;

  @Autowired
  private BaseUserRoleAuthMapper baseUserRoleAuthMapper;


  @Autowired
  private BaseRoleAuthMapperEx baseRoleAuthMapperEx;

  @Autowired
  private IdHelper idHelper;


  @Autowired
  private ServerConfigHelper serverConfigHelper;
//  @Autowired
//  private Environment env;

  public void addRoleAuth(BaseRoleAuthReq baseRoleAuthReq) {
    BaseRoleAuth baseRoleAuth = new BaseRoleAuth();
    BeanUtils.copyProperties(baseRoleAuthReq,baseRoleAuth);
    baseRoleAuth.setId(idHelper.generateRoleAuthId());
    baseRoleAuth.setCreateTime(TimeUtil.getCurrentTime());
    baseRoleAuth.setUpdateTime(TimeUtil.getCurrentTime());
    baseRoleAuthMapper.insert(baseRoleAuth);
    log.info("insert baseRoleAuth success");
  }

  public void updateRoleAuth(BaseRoleAuthUpdateReq baseRoleAuthUpdateReq) {
    BaseRoleAuth baseRoleAuth = new BaseRoleAuth();
    BeanUtils.copyProperties(baseRoleAuthUpdateReq,baseRoleAuth);
    baseRoleAuth.setUpdateTime(TimeUtil.getCurrentTime());
    baseRoleAuthMapper.updateByPrimaryKeySelective(baseRoleAuth);
    log.info("update baseRoleAuth success");
  }

  public JwResponseData<Object> deleteRoleAuth(Map jsonObject) {
    String roleIdInput = jsonObject.get("id").toString();
    long count = baseUserRoleAuthMapper.countByExample()
        .where(BaseUserRoleAuthDynamicSqlSupport.status, isEqualTo((byte) 1))
        .and(BaseUserRoleAuthDynamicSqlSupport.roleId, isEqualTo(roleIdInput)).build().execute();
    if (count > 0) {
      return JwResponseData.error(DataAuthResponseCode.ROLE_NOT_DELETE_BECAUSE_HAS_USER);
    }

    Long roleRuthPermissionCount = baseRoleAuthPermissionMapper.countByExample()
        .where(BaseRoleAuthPermissionDynamicSqlSupport.roleId, isEqualTo(roleIdInput))
        .and(BaseRoleAuthPermissionDynamicSqlSupport.status, isEqualTo((byte) 1))
        .build().execute();
    if (roleRuthPermissionCount > 0) {
      return JwResponseData.error(DataAuthResponseCode.ROLE_NOT_DELETE_BECAUSE_HAS_PERMISSION);
    }

    BaseRoleAuth baseRoleAuth = new BaseRoleAuth();
    baseRoleAuth.setId(roleIdInput);
    baseRoleAuth.setUpdateTime(TimeUtil.getCurrentTime());
    baseRoleAuth.setIsValid((byte) 0);
    baseRoleAuthMapper.updateByPrimaryKeySelective(baseRoleAuth);
    log.info("delete baseRoleAuth end baseRoleAuthId:" + roleIdInput);
    return JwResponseData.success("删除角色成功");
  }

  public List<BaseRoleAuth> getRoleAuthListBySysId(int sysIdInput) {
    return baseRoleAuthMapper.selectByExample()
        .where(BaseRoleAuthDynamicSqlSupport.sysId, isEqualTo(sysIdInput))
        .and(BaseRoleAuthDynamicSqlSupport.isValid, isEqualTo((byte) 1))
        .build().execute();
  }

  public List<BaseRoleAuth> getAllRoleAuth() {
    return baseRoleAuthMapper.selectByExample().where(BaseRoleAuthDynamicSqlSupport.isValid, isEqualTo((byte) 1)).build().execute();
  }

  public List<Map<String, Object>> getRoleAndUsersByRoleKey() {
    return baseRoleAuthMapperEx.getRoleAndUsersByRoleKey(serverConfigHelper.getValue("roleOperationDep"));

  }

  public List<BaseRoleAuth> getRoleByUserId(int userId) {
    return baseRoleAuthMapperEx.getRoleByUserId(userId);
  }

  public List<BaseRoleAuth> getRoleByRoleKey() {
    return baseRoleAuthMapperEx.getRoleByRoleKey(serverConfigHelper.getValue("roleOperationDep"));
  }
}
