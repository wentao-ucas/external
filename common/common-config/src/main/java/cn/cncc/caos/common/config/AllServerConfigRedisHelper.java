package cn.cncc.caos.common.config;

import cn.cncc.caos.common.core.enums.ModuleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AllServerConfigRedisHelper {

  private final static String redisKey = "all-server-config";

  @Resource
  public RedisTemplate<String, String> redisTemplate;

  public String getValue(String module, String key) {
    log.info("start get config from redis");
    Object valueObj = redisTemplate.opsForHash().get(module + redisKey, key);
    if (valueObj != null)
      return (String) valueObj;
    return null;
  }

  public Map<Object, Object> getConfigMap(String module) {
    log.info("start get config from redis");
    return redisTemplate.opsForHash().entries(module + redisKey);
  }

  public void putAll(Map<String, String> map, String module) {
    redisTemplate.opsForHash().putAll(module + redisKey, map);
  }

  public void clearConfigs() {
    List<String> keys = new ArrayList<>();
    for (ModuleEnum value : ModuleEnum.values()) {
      String key = value.name + redisKey;
      Boolean hasKey = redisTemplate.hasKey(key);
      if (hasKey != null && hasKey)
        keys.add(key);
    }

    if (!CollectionUtils.isEmpty(keys))
      redisTemplate.delete(keys);
  }

  public void clearConfigsByModule(String module) {
    String key = module + redisKey;
    Boolean hasKey = redisTemplate.hasKey(key);
    if (hasKey != null && hasKey)
      redisTemplate.delete(key);
  }
}
