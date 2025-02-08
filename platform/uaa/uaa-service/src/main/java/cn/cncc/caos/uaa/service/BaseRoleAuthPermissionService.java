package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.uaa.db.dao.BasePermissionMapper;
import cn.cncc.caos.uaa.db.dao.BaseRoleAuthPermissionDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseRoleAuthPermissionMapper;
import cn.cncc.caos.uaa.db.daoex.BaseRoleAuthPermissionMapperEx;
import cn.cncc.caos.uaa.exception.DataAuthException;
import cn.cncc.caos.uaa.model.role.auth.BaseRoleAuthSetPermissionReq;
import cn.cncc.caos.uaa.db.pojo.BaseRoleAuthPermission;
import cn.cncc.caos.uaa.utils.IdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;


@Slf4j
@Service
public class BaseRoleAuthPermissionService {

  @Autowired
  private IdHelper idHelper;

  @Autowired
  private BaseRoleAuthPermissionMapper baseRoleAuthPermissionMapper;

  @Autowired
  private BaseRoleAuthPermissionMapperEx baseRoleAuthPermissionMapperEx;

  @Autowired
  private BasePermissionMapper basePermissionMapper;

  @Transactional
  public List<String> addRolePermission(BaseRoleAuthSetPermissionReq baseRoleSetPermissionReq) {
    //判断参数中的对象是否为空
    List<String> permissionIds = baseRoleSetPermissionReq.getPermissionIds();
    String roleId = baseRoleSetPermissionReq.getRoleId();
    if (roleId == null || permissionIds==null)
      throw new DataAuthException(JwResponseCode.BIND_ERROR);
    //删除之前的对应关系
    baseRoleAuthPermissionMapperEx.deleteRoleAuthPermission(new Date(),roleId);
    log.info("delete rolePermission by roleId end roleId:"+roleId);
    //组装BaseRolepermission
    if (permissionIds.size()==0)
      return null;
    List<BaseRoleAuthPermission> baseRolePermissions = permissionIds.stream().map(permissionId -> {
      BaseRoleAuthPermission baseRolePermission = new BaseRoleAuthPermission();
      baseRolePermission.setRoleId(roleId);
      baseRolePermission.setId(idHelper.generateRoleAuthPermissionId());
      baseRolePermission.setCreateTime(new Date());
      baseRolePermission.setUpdateTime(new Date());
      baseRolePermission.setPermissionId(permissionId);
      baseRolePermission.setStatus((byte) 1);
      return baseRolePermission;
    }).collect(Collectors.toList());
    //批量新增
    baseRoleAuthPermissionMapperEx.batchInsert(baseRolePermissions);
    log.info("batch insert rolePermission end");
    //回显数据
    //根据permissionIds获取权限
    return permissionIds;
  }

  public List<String> getPermissionIdsByUserId(Integer userId) {
    return baseRoleAuthPermissionMapperEx.getPermissionIdsByUserId(userId);
  }

  public List<BaseRoleAuthPermission> getPermissionNamesByRoleId(String id) {
    return baseRoleAuthPermissionMapper.selectByExample()
        .where(BaseRoleAuthPermissionDynamicSqlSupport.roleId, isEqualTo(id))
        .and(BaseRoleAuthPermissionDynamicSqlSupport.status, isEqualTo((byte) 1))
        .build().execute();
  }

  public void judgePermissionRelRoleAuth(String id) {
    List<BaseRoleAuthPermission> execute = baseRoleAuthPermissionMapper.selectByExample()
        .where(BaseRoleAuthPermissionDynamicSqlSupport.permissionId, isEqualTo(id))
        .and(BaseRoleAuthPermissionDynamicSqlSupport.status, isEqualTo((byte) 1))
        .build().execute();
    if (!CollectionUtils.isEmpty(execute)){
      throw new BapParamsException("无法删除，有角色正在使用该权限");
    }
  }
}
