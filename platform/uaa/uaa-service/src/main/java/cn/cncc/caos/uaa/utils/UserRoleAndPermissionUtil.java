package cn.cncc.caos.uaa.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class UserRoleAndPermissionUtil {

  public static String getRoleAndPermission(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    StringBuilder roleAndPermissionSB = new StringBuilder();
    for (GrantedAuthority authority : authorities) {
      String authorityStr = authority.getAuthority();
      roleAndPermissionSB.append(",").append(authorityStr);
    }
    String roleAndPermissionStr = roleAndPermissionSB.toString();
    if (roleAndPermissionStr.startsWith(","))
      roleAndPermissionStr = roleAndPermissionStr.substring(1,roleAndPermissionStr.length());
    return roleAndPermissionStr;
  }

}
