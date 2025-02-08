package cn.cncc.caos.common.config;


import cn.cncc.caos.common.core.exception.BapConfigException;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class BaseServerConfigHelper {
  public String module;
  protected Map<Object, Object> configMap;

  public String getValue(String key) {
    if (configMap == null || StringUtils.isEmpty(configMap.get(key))) {
      resetConfigMap();
      if (configMap == null || StringUtils.isEmpty(configMap.get(key))) {
        log.error("get configMap from redis is empty,key:" + key);
        throw new BapConfigException(" key：" + key);
      }
    }
    return (String) configMap.get(key);
  }

  public Long getLongValue(String key) {
    String value = this.getValue(key);
    return Long.parseLong(value);
  }

  public Integer getIntegerValue(String key) {
    String value = this.getValue(key);
    return Integer.parseInt(value);
  }

  public List<String> getListValue(String key) {
    String listValueStr = this.getValue(key);
//    if (StringUtils.isEmpty(listValueStr)) {
//      log.info("get list value is empty kye:" + key);
//      throw new JwConfigException("get list value is empty key：" + key);
//    }

    List<String> res = new ArrayList<>();
    if (listValueStr.contains(",")) {
      res.addAll(Arrays.asList(listValueStr.split(",")));
    } else {
      res.add(listValueStr);
    }
    return res;
  }

  public boolean getBooleanValue(String key) {
    String booleanValue = this.getValue(key);
    return Boolean.parseBoolean(booleanValue);
  }

  public Map<String, String> getMapValue(String key) {
    try {
      String value = this.getValue(key);
      return JacksonUtil.jsonToObj(value, Map.class);
    } catch (IOException e) {
      log.error("", e);
      throw new BapConfigException("获取map配置失败，key:" + key);
    }

  }

  public abstract void resetConfigMap();
  /*
  public abstract void resetConfigMap(String moduleFromKafka);


  public abstract void reloadFromDb(String moduleFromKafka);

    @KafkaListener(topics = "${kafka.topic.server-config}", containerFactory = "kafkaListenerContainerFactory")
    public void listenSshResult(ConsumerRecord<?, ?> record, Acknowledgment acknowledgment) {
      try {
        String moduleFromKafka = record.value().toString();
        reloadFromDb(moduleFromKafka);
        log.info("loadConfigs end from kafka");
        resetConfigMap(moduleFromKafka);
      } catch (Exception e) {
        log.error("", e);
      } finally {
        acknowledgment.acknowledge();
      }
    }
  public boolean containsProperty(String key) {
    if (configMap == null)
      resetConfigMap();
    return configMap.containsKey(key);
  }
  */
}
