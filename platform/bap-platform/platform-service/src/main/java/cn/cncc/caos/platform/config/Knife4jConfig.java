package cn.cncc.caos.platform.config;

import cn.cncc.caos.common.core.config.BaseKnife4jConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig extends BaseKnife4jConfig {
  @Override
  public String getTitle() {
    return "平台管理";
  }

  @Override
  public String getDescription() {
    return "平台服务";
  }
}
