package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.uaa.db.dao.BaseRoleDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseRoleMapper;
import cn.cncc.caos.uaa.db.dao.BaseUserRelRoleDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseUserRelRoleMapper;
import cn.cncc.caos.uaa.db.daoex.BaseUserRelRoleMapperEx;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUserRelRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.cncc.caos.uaa.db.dao.BaseUserRelRoleDynamicSqlSupport.roleId;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;

@Slf4j
@Service
public class BaseUserRelRoleService {

  @Autowired
  private BaseUserRelRoleMapper baseUserRelRoleMapper;

  @Autowired
  private BaseUserRelRoleMapperEx baseUserRelRoleMapperEx;

  @Autowired
  private BaseRoleMapper baseRoleMapper;


  public Map<String, String> getUserAndRoleByRoleId(List<Integer> roleIdList) {
    List<Map<String, String>> roleAndUserList = baseUserRelRoleMapperEx.getUserAndRoleByRoleId(roleIdList);

    Map<String, String> res = new HashMap<>();
    for (Map<String, String> stringStringMap : roleAndUserList) {
      String roleName = stringStringMap.get("roleName");
      String realName = stringStringMap.get("realName");
      if (StringUtils.isEmpty(roleName) || StringUtils.isEmpty(realName)) {
        log.error("getUserAndRoleByRoleId is error roleName={},realName={}", roleName, realName);
      } else {
        if (res.containsKey(roleName)) {
          String userNames = res.get(roleName);
          userNames += "," + realName;
          res.put(roleName, userNames);
        } else {
          res.put(roleName, realName);
        }
      }
    }
    return res;
  }

  public List<BaseRole> getBaseRolesByUserId(Integer userId) {
    List<BaseRole> baseRoleList = new ArrayList<>();
    //根据用户查询roleIdList
    List<BaseUserRelRole>  baseUserRelRoles = baseUserRelRoleMapper.selectByExample()
            .where(BaseUserRelRoleDynamicSqlSupport.userId, isEqualTo(userId))
            .and(BaseUserRelRoleDynamicSqlSupport.isValid, isEqualTo(1))
            .build().execute();
    List<Integer> roleIdList = baseUserRelRoles.stream().map(BaseUserRelRole::getRoleId).collect(Collectors.toList());
    if (roleIdList.isEmpty()) {
      return baseRoleList;
    }
    baseRoleList = baseRoleMapper.selectByExample()
            .where(BaseRoleDynamicSqlSupport.id,isIn(roleIdList))
            .build().execute();
    return baseRoleList;
  }

  public List<BaseUserRelRole> selectRoleByUser(Integer sourceId) {
    return baseUserRelRoleMapper.selectByExample()
            .where(BaseUserRelRoleDynamicSqlSupport.userId, isEqualTo(sourceId))
            .and(BaseUserRelRoleDynamicSqlSupport.isValid, isEqualTo(1))
            .build().execute();
  }

  public List<BaseUserRelRole> selectByUserAndRoleIds(Integer userId,List<Integer> roleIds) {
    return baseUserRelRoleMapper.selectByExample()
            .where(BaseUserRelRoleDynamicSqlSupport.userId, isEqualTo(userId))
            .and(BaseUserRelRoleDynamicSqlSupport.roleId,isIn(roleIds))
            .and(BaseUserRelRoleDynamicSqlSupport.isValid, isEqualTo(1))
            .build().execute();
  }

  public void batchInsert(List<BaseUserRelRole> targetBaseUserRelRoleList) {
    baseUserRelRoleMapperEx.batchInsert(targetBaseUserRelRoleList);
    log.info("batch insert targetBaseUserRelRoleList end");
  }

  public void batchInsertWithoutSync(List<BaseUserRelRole> targetBaseUserRelRoleList) {
    baseUserRelRoleMapperEx.batchInsert(targetBaseUserRelRoleList);
  }


  public void deleteByUser(Integer targetId) {
    baseUserRelRoleMapperEx.deleteByUser(targetId);
    log.info("batch delete targetBaseUserRelRoleList end, targetId:" + targetId);
  }


  public void batchDelete(List<BaseUserRelRole> baseUserRelRoleList) {
    baseUserRelRoleMapperEx.batchDelete(baseUserRelRoleList);
  }
}
