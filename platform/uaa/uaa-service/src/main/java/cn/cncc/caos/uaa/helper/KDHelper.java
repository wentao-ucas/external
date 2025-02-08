package cn.cncc.caos.uaa.helper;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class KDHelper {

  @Resource
  private Environment env;

  public String getJwEnv() {
    if (StringUtils.isNotEmpty(System.getenv("jwEnv")))
      return System.getenv("jwEnv");
    return env.getProperty("jw.env");
  }
}
