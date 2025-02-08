package cn.cncc.caos.uaa.model.role.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BaseRoleAuthReq {
  @NotBlank(message = "角色名不能为空")
  @Schema(description = "角色名最大45", type = "String", required = true)
  private String roleName;
  @NotBlank(message = "角色描述不能为空")
  @Schema(description = "角色描述最大45", type = "String", required = true)
  private String roleDesc;
  @NotBlank(message = "系统id不能为空")
  @Schema(description = "系统id", type = "Integer", required = true)
  private Integer sysId;
  @NotBlank(message = "状态不能为空")
  @Schema(description = "状态,正常为1，删除为0", type = "byte", required = true)
  private Byte isValid;
  private String roleKey;
}
