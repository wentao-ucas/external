package cn.cncc.caos.uaa.config;

import cn.cncc.caos.common.core.config.BaseResourceServerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends BaseResourceServerConfig {

  @Override
  public String[] getPermitURIs() {
    return new String[]{"/sso_login", "/iops_login", "/inner_api/**", "/oauth/**", "/webjars/**", "/doc.html", "/v3/**", "/swagger-ui/**"};
  }

}