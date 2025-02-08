package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.model.role.auth.BaseRoleAuthSetPermissionReq;
import cn.cncc.caos.uaa.service.BaseRoleAuthPermissionService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@Tag(name = "角色-权限相关接口")
@RestController
public class BaseRoleAuthPermissionController {

  @Autowired
  private BaseRoleAuthPermissionService baseRoleAuthPermissionService;


  private String operTypeUpdate = "roleAuthPermissionUpdate";

  @Autowired
  private OperationHistoryService operationHistoryService;

  @PreAuthorize("hasAuthority('role-manage')")
  @Operation(summary = "角色增加权限接口")
  @RequestMapping(value = "/open_api/admin/role/permission/relation", method = RequestMethod.PUT)
  public JwResponseData<List<String>> setPermission(@RequestBody BaseRoleAuthSetPermissionReq baseRoleSetPermissionReq) {
    if (baseRoleSetPermissionReq == null)
      return JwResponseData.error(JwResponseCode.BIND_ERROR);
    //role and permission表新增记录，roleid，permissionid
    List<String> permissionNameList = baseRoleAuthPermissionService.addRolePermission(baseRoleSetPermissionReq);
    BaseUser user = UserHolder.getUser();
    if (user == null)
      return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
    log.info("update roleAuthPermission end operationUser:" + user.getRealName() + ",roleId:" + baseRoleSetPermissionReq.getRoleId() + ",permissionIds:" + baseRoleSetPermissionReq.getPermissionIds());
    try {
      operationHistoryService.insertHistoryOper(user, operTypeUpdate, JacksonUtil.objToJson(baseRoleSetPermissionReq));
    } catch (IOException e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs("obj to json error"));
    }
    return JwResponseData.success("设置权限成功", permissionNameList);
  }
}
