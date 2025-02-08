package cn.cncc.caos.uaa.config;

import cn.cncc.caos.common.core.config.BaseKnife4jConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig extends BaseKnife4jConfig {
  @Override
  public String getTitle() {
    return "用户中心";
  }

  @Override
  public String getDescription() {
    return "用户中心服务";
  }
}
