package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.exception.DataAuthException;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.model.permission.BasePermissionReq;
import cn.cncc.caos.uaa.model.permission.BasePermissionRes;
import cn.cncc.caos.uaa.model.permission.BasePermissionUpdateReq;
import cn.cncc.caos.uaa.service.BasePermissionService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "权限相关接口")
@Slf4j
public class BasePermissionController {

  @Autowired
  private BasePermissionService basePermissionService;

  private String operTypeAdd = "permissionAdd";
  private String operTypeUpdate = "permissionUpdate";
  private String operTypeDelete = "permissionDelete";

  @Autowired
  private OperationHistoryService operationHistoryService;

  @PreAuthorize("hasAuthority('permission-manage')")
  @Operation(summary = "新增权限接口")
  @RequestMapping(value = "/open_api/permission", method = RequestMethod.POST)
  public JwResponseData<Object> addPermission(@Validated @RequestBody BasePermissionReq basePermissionReq) {
    if (basePermissionReq == null) {
      return JwResponseData.error(JwResponseCode.BIND_ERROR);
    }
    //将权限入库
    try {
      basePermissionService.addPermission(basePermissionReq);
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      operationHistoryService.insertHistoryOper(user, operTypeAdd, JacksonUtil.objToJson(basePermissionReq));
      return JwResponseData.success("新增成功");
    } catch (DataAuthException dae) {
      log.error(dae.getCm().getMsg(), dae);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(dae.getCm().getMsg()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('permission-manage')")
  @Operation(summary = "修改权限接口")
  @RequestMapping(value = "/open_api/permission", method = RequestMethod.PUT)
  public JwResponseData<Object> updatePermission(@Validated @RequestBody BasePermissionUpdateReq basePermissionUpdateReq) {
    try {
      //判断并更新
      if (basePermissionUpdateReq == null)
        return JwResponseData.error(JwResponseCode.BIND_ERROR);
      basePermissionService.updatePermission(basePermissionUpdateReq);
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update permission end operationUser:" + user.getRealName() + ",permissionId:" + basePermissionUpdateReq.getId());
      operationHistoryService.insertHistoryOper(user, operTypeUpdate, JacksonUtil.objToJson(basePermissionUpdateReq));
      return JwResponseData.success("修改成功");
    } catch (DataAuthException dae) {
      log.error(dae.getCm().getMsg(), dae);
      return JwResponseData.error(JwResponseCode.DB_DATA_NOT_EXIST);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('permission-manage')")
  @Operation(summary = "删除权限接口")
  @RequestMapping(value = "/open_api/permission", method = RequestMethod.DELETE)
  public JwResponseData<Object> deletePermission(@RequestParam(value = "id") String id) {
    try {
      //判断当前permission是否关联了角色
      try {
        basePermissionService.judgeRelRoleAuth(id);
      } catch (BapParamsException jpe) {
        log.error("", jpe);
        return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(jpe.getMessage()));
      }
      //删除该权限，判断是否有子权限，有子权限都删除
      basePermissionService.deletePermission(id);
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update permission end operationUser:" + user.getRealName() + ",permissionId:" + id);
      operationHistoryService.insertHistoryOper(user, operTypeDelete, "delete permission:"+id);
      return JwResponseData.success("删除成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('permission-manage')")
  @Operation(summary = "获取全部权限列表")
  @RequestMapping(value = "/open_api/permission/list", method = RequestMethod.GET)
  public JwResponseData<List<BasePermissionRes>> getFullList(@RequestParam(value = "searchColumn", required = false) String searchColumn) {
    try {
      List<BasePermissionRes> basePermissionRes = basePermissionService.getAllPermission(searchColumn);
      return JwResponseData.success("获取权限成功", basePermissionRes);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('permission-manage')")
  @Operation(summary = "根据roleId获取权限标识列表")
  @RequestMapping(value = "/open_api/permission/list/by/role/id", method = RequestMethod.GET)
  public JwResponseData<List<String>> getNameListByRoleId(@RequestParam(value = "roleId") String roleId) {
    return JwResponseData.success("获取权限标识列表成功", basePermissionService.getPermissionNamesByRoleId(roleId));
  }

  @Operation(summary = "删除权限的缓存接口")
  @RequestMapping(value = "/inner_api/permission/cache", method = RequestMethod.GET)
  public JwResponseData<Object> deletePermissionCache() {
    basePermissionService.deletePermissionCache();
    return JwResponseData.success("删除缓存成功");
  }
}
