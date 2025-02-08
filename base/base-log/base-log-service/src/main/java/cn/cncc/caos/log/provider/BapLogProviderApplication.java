package cn.cncc.caos.log.provider;

import cn.cncc.caos.common.core.helper.BapHelper;
import cn.cncc.mojito.rpc.invoker.annotation.EnableMojitoScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @className: BapLogProviderApplication
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/11 15:11
 */
@ComponentScan(value = {"cn.cncc.caos"})
//@ServletComponentScan
@SpringBootApplication
@Slf4j
@EnableMojitoScan
//@EnableWebMvc
public class BapLogProviderApplication implements CommandLineRunner {

  @Value("${spring.profiles.active}")
  private String profilesActive;
  @Resource
  private BapHelper bapHelper;


  public static void main(String[] args) {
//		SpringApplication.run(DataManageProviderApplication.class, args);
    new SpringApplicationBuilder(BapLogProviderApplication.class).allowCircularReferences(true).run(args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.info(String.format("spring.profiles.active=%s, caos.env=%s", profilesActive, bapHelper.getBapEnv()));
  }

  // 配置 RestTemplate
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
