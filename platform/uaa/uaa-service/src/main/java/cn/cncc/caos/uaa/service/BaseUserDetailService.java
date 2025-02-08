package cn.cncc.caos.uaa.service;


import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.UserInfoRes;
import cn.cncc.caos.platform.uaa.client.api.pojo.BasePermission;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRoleAuth;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BaseUserDetailService implements UserDetailsService {


//  @Autowired
//  private DataAuthFeignClient dataAuthFeignClient;

  @Autowired
  private BaseUserService baseUserService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 将用户、用户组、角色、 权限一个接口返回
    JwResponseData<UserInfoRes> dataAuthRes = baseUserService.getUserInfoByUserNameFromAuth(username);//dataAuthFeignClient.getUserInfoByUserNameFromAuth(username);
    if (!dataAuthRes.isSuccess() || dataAuthRes.getData() == null) {
      log.error("request dataAuth getUserInfoByUserName error userName:" + username);
      throw new UsernameNotFoundException("获取用户详细信息失败，用户名：" + username);
    }

    UserInfoRes data = dataAuthRes.getData();
    BaseUser baseUser = data.getBaseUser();

    if (baseUser == null) {
      log.error("找不到该用户，用户名：" + username);
      throw new UsernameNotFoundException("找不到该用户，用户名：" + username);
    }
    List<BaseRole> roles = data.getRoles();
    if (roles == null) {
      log.error("查询角色为空，userName:" + username);
      roles = new ArrayList<>();
    }

    List<BaseRoleAuth> roleAuths = data.getRoleAuths();
    if (CollectionUtils.isEmpty(roleAuths)) {
      log.error("该用户所属角色为空，userName:" + username);
      roleAuths = new ArrayList<>();
    }

    List<BasePermission> permissions = data.getPermissions();
    if (CollectionUtils.isEmpty(permissions)) {
      log.error("该用户拥有权限为空，userName:" + username);
      permissions = new ArrayList<>();
    }

    // 获取用户权限列表
    log.info("start convertToAuthorities userName={}", username) ;
    List<GrantedAuthority> authorities = convertToAuthorities(baseUser, roles, roleAuths, permissions);
    log.info("end convertToAuthorities userName={}", username) ;

    try {
      log.info("baseUser" + JacksonUtil.objToJson(baseUser) + "=====roles:" + roles);
    } catch (JsonProcessingException e) {
      log.error("", e);
    }
    // 返回带有用户权限信息的User
    String baseUserStr = null;
    try {
      baseUserStr = JacksonUtil.objToJson(baseUser);
    } catch (IOException e) {
      log.error("",e);
    }
    org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(baseUserStr,
//                encoder.encode(baseUser.getPassword().trim()),
         baseUser.getPassword(),
         isActive(1), true, true, true, authorities);
    return new BaseUserDetail(baseUser, user);
  }

  private List<GrantedAuthority> convertToAuthorities(BaseUser baseUser, List<BaseRole> roles, List<BaseRoleAuth> roleAuths, List<BasePermission> permissions) {
    List<GrantedAuthority> authorities = new ArrayList();
    // 清除 Redis 中用户的角色
    // redisTemplate.delete(baseUser.getId());
    roles.forEach(e -> {
      // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
      GrantedAuthority authority = new SimpleGrantedAuthority(e.getRoleName());
      authorities.add(authority);
      //存储角色到redis
      // redisTemplate.opsForList().rightPush(baseUser.getId(), e);
    });

    roleAuths.forEach(baseRoleAuth -> {
      GrantedAuthority authority = new SimpleGrantedAuthority(baseRoleAuth.getRoleName());
      authorities.add(authority);
    });

    permissions.forEach(basePermission -> {
      GrantedAuthority authority = new SimpleGrantedAuthority(basePermission.getName());
      authorities.add(authority);
    });
    return authorities;
  }

  private boolean isActive(int active) {
    return active == 1 ? true : false;
  }

}

