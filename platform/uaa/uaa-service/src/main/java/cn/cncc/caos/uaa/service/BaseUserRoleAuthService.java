package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.enums.DataStatus;
import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.db.dao.BaseUserRoleAuthDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseUserRoleAuthMapper;
import cn.cncc.caos.uaa.db.daoex.BaseUserRoleAuthMapperEx;
import cn.cncc.caos.uaa.db.pojo.BaseUserRoleAuth;
import cn.cncc.caos.uaa.utils.IdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;

@Slf4j
@Service
public class BaseUserRoleAuthService {

  @Autowired
  private BaseUserRoleAuthMapper baseUserRoleAuthMapper;

  @Autowired
  private BaseUserRoleAuthMapperEx baseUserRoleAuthMapperEx;

  @Autowired
  private IdHelper idHelper;

  public List<BaseUserRoleAuth> getAllRel() {
    return baseUserRoleAuthMapper.selectByExample()
        .where(BaseUserRoleAuthDynamicSqlSupport.status, isEqualTo((byte) 1))
        .build().execute();
  }

  public List<BaseUserRoleAuth> getBaseUserRoleAuthListByRoleId(String roleId) {
    return baseUserRoleAuthMapper.selectByExample()
        .where(BaseUserRoleAuthDynamicSqlSupport.roleId, isEqualTo(roleId))
        .where(BaseUserRoleAuthDynamicSqlSupport.status, isEqualTo((byte) 1))
        .build().execute();
  }

  public List<BaseUser> selectUsersByRoleAuthId(String roleId) {
    return baseUserRoleAuthMapperEx.selectUsersByRoleAuthId(roleId);
  }

  public HashMap<String, List<Integer>> selectUsersByRoleAuthIdToMap(Integer handoverUserId, List<String> roleIds) {
    List<BaseUserRoleAuth> baseUserRoleAuths = baseUserRoleAuthMapper.selectByExample()
        .where(BaseUserRoleAuthDynamicSqlSupport.roleId, isIn(roleIds))
        .and(BaseUserRoleAuthDynamicSqlSupport.userId, isEqualTo(handoverUserId))
        .and(BaseUserRoleAuthDynamicSqlSupport.status, isEqualTo((byte) 1))
        .build().execute();
    HashMap<String, List<Integer>> userRoleMap = new HashMap<>();
    for (BaseUserRoleAuth baseUserRoleAuth : baseUserRoleAuths) {
      List<Integer> userIds = new ArrayList<>();
      if (userRoleMap.get(baseUserRoleAuth.getRoleId()) == null) {
        userIds = Arrays.asList(baseUserRoleAuth.getUserId());
        userRoleMap.put(baseUserRoleAuth.getRoleId(), userIds);
      } else {
        userIds = userRoleMap.get(baseUserRoleAuth.getRoleId());
        userRoleMap.put(baseUserRoleAuth.getRoleId(), userIds);
      }
    }
    return userRoleMap;
  }

  public List<BaseUserRoleAuth> selectRoleAuthByUser(Integer sourceId) {
    return baseUserRoleAuthMapper.selectByExample()
        .where(BaseUserRoleAuthDynamicSqlSupport.userId, isEqualTo(sourceId))
        .and(BaseUserRoleAuthDynamicSqlSupport.status, isEqualTo(DataStatus.NORMAL.index))
        .build().execute();
  }

  public void batchInsert(List<BaseUserRoleAuth> targetBaseUserRoleAuthList) {
    baseUserRoleAuthMapperEx.batchInsert(targetBaseUserRoleAuthList);
    log.info("batch insert baseUserRoleAuth end");
  }


  public void deleteByUser(Integer targetId) {
    baseUserRoleAuthMapperEx.deleteByUser(targetId, TimeUtil.getCurrentTime());
    log.info("batch delete baseUserRoleAuth end, targetId:" + targetId);
  }


  public void handoverRoleAuth(String roleId, Integer handoverUserId, Integer candidateUserId) {
    //把候选人关联上角色
    BaseUserRoleAuth bura = new BaseUserRoleAuth();
    bura.setId(idHelper.generateUserRoleAuthId());
    bura.setUserId(candidateUserId);
    bura.setRoleId(roleId);
    bura.setStatus((byte) 1);
    bura.setCreateTime(TimeUtil.getCurrentTime());
    bura.setUpdateTime(TimeUtil.getCurrentTime());
    baseUserRoleAuthMapper.insert(bura);

  }

  @Transactional
  public void unboundRoleAuth(Integer handoverUserId) {
    //把交接人从角色关联中干掉
    List<BaseUserRoleAuth> handoverList = baseUserRoleAuthMapper.selectByExample()
        .where(BaseUserRoleAuthDynamicSqlSupport.userId, isEqualTo(handoverUserId))
        .and(BaseUserRoleAuthDynamicSqlSupport.status, isEqualTo(Byte.valueOf((byte) 1)))
        .build().execute();
    for (BaseUserRoleAuth baseUserRoleAuth : handoverList) {
      baseUserRoleAuth.setStatus(Byte.valueOf((byte) 0));
      baseUserRoleAuthMapper.updateByPrimaryKey(baseUserRoleAuth);
    }
  }
}
