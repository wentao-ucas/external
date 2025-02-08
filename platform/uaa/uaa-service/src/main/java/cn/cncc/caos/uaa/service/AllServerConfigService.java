package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.config.AllServerConfigRedisHelper;
import cn.cncc.caos.common.core.exception.BapConfigException;
import cn.cncc.caos.uaa.EnvTypeEnum;
import cn.cncc.caos.uaa.db.daoex.AllServerConfigMapperEx;
import cn.cncc.caos.uaa.db.pojo.AllServerConfig;
import cn.cncc.caos.uaa.enums.LocationTypeEnum;
import cn.cncc.caos.uaa.helper.KDHelper;
import cn.cncc.caos.uaa.utils.EntityUtil;
import cn.cncc.caos.uaa.utils.KDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AllServerConfigService {

  @Resource
  private AllServerConfigRedisHelper allServerConfigRedisHelper;
  @Resource
  private KDHelper kdHelper;
  @Resource
  private AllServerConfigMapperEx allServerConfigMapperEx;

  @PostConstruct
  public void init() {
    allServerConfigRedisHelper.clearConfigs();
    loadConfigs();
  }

  public void loadConfigs() {
    // 区分北京、上海
    String jwEnv = kdHelper.getJwEnv();
    EnvTypeEnum envTypeEnum = KDUtil.isNpc(jwEnv) ? EnvTypeEnum.NPC : EnvTypeEnum.OFFICE;
    LocationTypeEnum locationTypeEnum = LocationTypeEnum.typeOf(jwEnv);
    if (locationTypeEnum == null)
      locationTypeEnum = LocationTypeEnum.ALL;

    List<AllServerConfig> list = allServerConfigMapperEx.getByServerEnv(envTypeEnum.type, locationTypeEnum.type);

    HashMap<String, List<AllServerConfig>> moduleAndObjsMap = EntityUtil.getHashMapOfListUseKeyFieldNameByObjectList(list, "module");

    for (Map.Entry<String, List<AllServerConfig>> stringListEntry : moduleAndObjsMap.entrySet()) {
      String module = stringListEntry.getKey();
      List<AllServerConfig> value = stringListEntry.getValue();
      if (CollectionUtils.isEmpty(value)) {
        log.error("AllServerConfig.init is empty");
        return;
      }

      Map<String, String> keyAndValue = EntityUtil.getHashMapUseKeyFieldNameByObjectList(value, "configKey", "configValue");
      allServerConfigRedisHelper.putAll(keyAndValue, module);
    }
  }

  void reloadConfigs() {
    allServerConfigRedisHelper.clearConfigs();
    this.loadConfigs();
  }


  void reloadConfigsByModule(String module) {
    allServerConfigRedisHelper.clearConfigsByModule(module);
    this.loadConfigsByModule(module);
  }

  public void loadConfigsByModule(String module) {
    String jwEnv = kdHelper.getJwEnv();
    EnvTypeEnum envTypeEnum = KDUtil.isNpc(jwEnv) ? EnvTypeEnum.NPC : EnvTypeEnum.OFFICE;
    LocationTypeEnum locationTypeEnum = LocationTypeEnum.typeOf(jwEnv);
    if (locationTypeEnum == null)
      locationTypeEnum = LocationTypeEnum.ALL;

    List<AllServerConfig> list = allServerConfigMapperEx.getByServerEnvAndModule(envTypeEnum.type, module, locationTypeEnum.type);

    if (CollectionUtils.isEmpty(list)) {
      log.error("AllServerConfig.initByModule is empty");
      return;
    }

    Map<String, String> keyAndValue = EntityUtil.getHashMapUseKeyFieldNameByObjectList(list, "configKey", "configValue");
    allServerConfigRedisHelper.putAll(keyAndValue, module);
  }

  public Map<String, String> getConfig(String module, List<String> configKeyList) {
    Map<Object, Object> configMap = allServerConfigRedisHelper.getConfigMap(module);
    if (configMap == null) {
      log.error("configMap is null module:" + module);
      throw new BapConfigException("获取不到当前模块配置");
    }

    Map<String, String> res = new HashMap<>();
    for (Map.Entry<Object, Object> objectObjectEntry : configMap.entrySet()) {
      String key = (String) objectObjectEntry.getKey();
      if (configKeyList.contains(key))
        res.put(key, (String) objectObjectEntry.getValue());
    }

    return res;
  }
}
