package cn.cncc.caos.gateway;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {
  @Bean
  public OpenAPI caosOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("网关服务")
            .description("网关服务")
            .version("v1.0")
            .contact(new Contact().name("运维系统部").url("http://27.193.10.121/")))
        .externalDocs(new ExternalDocumentation()
            .description("Gitlab")
            .url("http://172.22.139.28/"));
  }
}
