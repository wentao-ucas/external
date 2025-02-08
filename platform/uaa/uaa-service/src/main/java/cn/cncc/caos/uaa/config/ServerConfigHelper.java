package cn.cncc.caos.uaa.config;

import cn.cncc.caos.common.config.AllServerConfigRedisHelper;
import cn.cncc.caos.common.config.BaseServerConfigHelper;
import cn.cncc.caos.common.core.enums.ModuleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ServerConfigHelper extends BaseServerConfigHelper {

  @Resource
  private AllServerConfigRedisHelper allServerConfigRedisHelper;
  private final String module = ModuleEnum.DATA_AUTH.name;

  @Override
  public void resetConfigMap() {
    configMap = allServerConfigRedisHelper.getConfigMap(module);
  }
/*
  @Override
  public void resetConfigMap(String moduleFromKafka) {
    if (module.equals(moduleFromKafka) || "all".equals(moduleFromKafka)) {
      configMap = allServerConfigRedisHelper.getConfigMap(module);
      log.info("resetConfigMap end module={}, moduleFromKafka={}", module, moduleFromKafka);
    }
  }

  @Override
  public void reloadFromDb(String moduleFromKafka) {
    log.info("data-auth jump reloadFromDb");
  }

 */
}
