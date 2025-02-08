package cn.cncc.caos.uaa.model.role.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class BaseRoleAuthSetPermissionReq {
  @Schema(description = "角色id",type = "String",required = true,example = "1")
  private String roleId;
  @Schema(description = "权限id列表",type = "List",required = true,example = "[1,2]")
  private List<String> permissionIds;
}
