package cn.cncc.caos.uaa.model.role;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseRoleRes {
  private Integer id;


  private String roleName;


  private String roleDesc;


  private Integer sysId;


  private Date updateTime;
}
