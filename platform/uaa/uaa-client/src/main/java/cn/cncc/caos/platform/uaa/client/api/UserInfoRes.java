package cn.cncc.caos.platform.uaa.client.api;

import cn.cncc.caos.platform.uaa.client.api.pojo.BasePermission;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRoleAuth;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoRes {
  private BaseUser baseUser;
  private List<BaseRole> roles;
  private List<BaseRoleAuth> roleAuths;
  private List<BasePermission> permissions;
}
