package cn.cncc.caos.data.provider.config;

import cn.cncc.caos.common.core.config.BaseKnife4jConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig extends BaseKnife4jConfig {
  @Override
  public String getTitle() {
    return "数据接口服务接口文档";
  }

  @Override
  public String getDescription() {
    return "数据接口";
  }
}
