package cn.cncc.caos.uaa.model.role.user;

import lombok.Data;

@Data
public class BaseUserRoleAuthReq {
  private String roleId;
  private String userIds;
}
