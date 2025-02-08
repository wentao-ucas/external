package cn.cncc.caos.common.core.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
public abstract class BaseKnife4jConfig {
  @Bean
  public OpenAPI caosOpenAPI() {
    return new OpenAPI()
        .info(new Info().title(getTitle())
            .description(getDescription())
            .version("v1.0")
            .contact(new Contact().name("运维系统部").url("http://27.193.10.121/")))
        .externalDocs(new ExternalDocumentation()
            .description("Gitlab")
            .url("http://172.22.139.28/"));
  }

  public abstract String getTitle();

  public abstract String getDescription();
}
