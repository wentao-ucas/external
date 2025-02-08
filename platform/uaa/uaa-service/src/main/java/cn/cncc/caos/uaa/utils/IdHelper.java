package cn.cncc.caos.uaa.utils;

import cn.cncc.caos.common.core.utils.StringUtil;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class IdHelper {
  private final String PERMISSION_PREFIX = "P";
  private final String RULE_PREFIX = "R";
  private final String ROLE_AUTH_PERMISSION_PREFIX = "RP";
  private final String ROLE_AUTH_PREFIX = "RA";
  private final String USER_ROLE_AUTH_PREFIX = "URA";

  @Resource
  private ServerConfigHelper serverConfigHelper;

  public String generatePermissionId() {
    return PERMISSION_PREFIX + serverConfigHelper.getValue("sync.id.prefix") + StringUtil.getNextSequence();
  }

  public String generateRuleId() {
    return RULE_PREFIX + serverConfigHelper.getValue("sync.id.prefix") + StringUtil.getNextSequence();
  }

  public String generateRoleAuthPermissionId() {
    return ROLE_AUTH_PERMISSION_PREFIX + serverConfigHelper.getValue("sync.id.prefix") + StringUtil.getNextSequence();
  }

  public String generateRoleAuthId() {
    return ROLE_AUTH_PREFIX + serverConfigHelper.getValue("sync.id.prefix") + StringUtil.getNextSequence();
  }

  public String generateUserRoleAuthId() {
    return USER_ROLE_AUTH_PREFIX + serverConfigHelper.getValue("sync.id.prefix") + StringUtil.getNextSequence();
  }
}
