package cn.cncc.caos.uaa.model.role.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class BaseRoleAuthUpdateReq extends BaseRoleAuthReq{
  @Schema(description = "角色id",type = "String",required = true,example = "1")
  private String id;
}
