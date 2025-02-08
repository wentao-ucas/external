package cn.cncc.caos.uaa.model.role.auth;

import lombok.Data;

import java.util.Date;

@Data
public class BaseRoleAuthRes {
  private String id;
  private String roleName;
  private String roleDesc;
  private Integer sysId;
  private Date updateTime;
  private String roleKey;
}
