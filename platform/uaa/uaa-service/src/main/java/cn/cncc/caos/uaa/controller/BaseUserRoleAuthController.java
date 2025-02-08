package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.service.BaseUserRoleAuthHelper;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.model.role.user.BaseUserRoleAuthReq;
import cn.cncc.caos.uaa.service.BaseUserRoleAuthService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import cn.cncc.caos.uaa.utils.EncryptAndDecryptUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "用户与角色关联相关接口")
public class BaseUserRoleAuthController {

  @Autowired
  private BaseUserRoleAuthService baseUserRoleAuthService;

  private String operTypeUpdate = "roleAuthRelUserUpdate";

  @Autowired
  private OperationHistoryService operationHistoryService;

  @Resource
  private BaseUserRoleAuthHelper baseUserRoleAuthHelper;

  @PreAuthorize("hasAuthority('user-relation-role-manage')")
  @Operation(summary = "角色关联用户接口")
  @RequestMapping(value = "/open_api/user/role/auth/relation", method = RequestMethod.POST)
  public JwResponseData<String> updateUserRoleAuth(@RequestBody BaseUserRoleAuthReq baseUserRoleAuthReq) {

    try {
      baseUserRoleAuthHelper.updateUserRoleAuth(baseUserRoleAuthReq);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update roleAuthRelUser end operationUser:" + user.getRealName() + ",roleAuthId:" + baseUserRoleAuthReq.getRoleId() + ",userIds:" + baseUserRoleAuthReq.getUserIds());
      operationHistoryService.insertHistoryOper(user, operTypeUpdate, JacksonUtil.objToJson(baseUserRoleAuthReq));
      return JwResponseData.success("更新用户角色对应关系成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

//  @ResponseBody
//  @Operation(summary = "获取用户角色关联列表")
//  @GetMapping("/open_api/user/role/auth/list")
//  public JwResponseData<List<BaseUserRoleAuth>> getAllRel() {
//    try {
//      List<BaseUserRoleAuth> list = baseUserRoleAuthService.getAllRel();
//      return JwResponseData.success("获得用户角色对应关系成功", list);
//    } catch (Exception e) {
//      log.error("", e);
//      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
//    }
//  }

  @ResponseBody
  @Operation(summary = "获取用户角色关联列表")
  @RequestMapping(value = "/open_api/user/role/auth/list", method = RequestMethod.GET)
  public JwResponseData<List<BaseUser>> getUserListByRoleId(@RequestParam(value = "roleId", required = true) String roleId) {
    try {
      // 调用FeignClient查询用户
      List<BaseUser> list = baseUserRoleAuthService.selectUsersByRoleAuthId(roleId);
      for (BaseUser baseUser : list) {
        //解密
        EncryptAndDecryptUtil.decryptBaseUser(baseUser);
        baseUser.setPassword("******");
      }
      return JwResponseData.success("getUsersByRoleId获取用户列表成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
}
