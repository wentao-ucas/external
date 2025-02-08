package cn.cncc.caos.uaa.controller.inner;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.service.BasePermissionService;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController
// @Tag(name = {"权限相关接口"})
@Slf4j
@MojitoSchema(schemaId = "inner_api/basePermissionInnerController")
public class BasePermissionInnerController {

  @Autowired
  private BasePermissionService basePermissionService;

  @Operation(summary = "根据userId获取权限")
  // @RequestMapping(value = "/inner_api/permission/by/userId", method = RequestMethod.GET)
  public JwResponseData<List<cn.cncc.caos.platform.uaa.client.api.pojo.BasePermission>> getPermissionByUserId(@RequestParam("userId") Integer userId) {
    return JwResponseData.success("", basePermissionService.getPermissionByUserId(userId));
  }
}
