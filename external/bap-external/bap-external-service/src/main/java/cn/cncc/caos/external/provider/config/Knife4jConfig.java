package cn.cncc.caos.external.provider.config;

import cn.cncc.caos.common.core.config.BaseKnife4jConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig extends BaseKnife4jConfig {
  @Override
  public String getTitle() {
    return "外部接口服务接口";
  }

  @Override
  public String getDescription() {
    return "外部接口";
  }
}
