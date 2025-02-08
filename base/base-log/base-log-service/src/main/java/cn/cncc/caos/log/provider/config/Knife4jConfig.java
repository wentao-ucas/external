package cn.cncc.caos.log.provider.config;

import cn.cncc.caos.common.core.config.BaseKnife4jConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig extends BaseKnife4jConfig {
  @Override
  public String getTitle() {
    return "日志服务";
  }

  @Override
  public String getDescription() {
    return "日志查询服务";
  }
}
