package cn.cncc.caos.data.provider;



import cn.cncc.caos.common.core.helper.BapHelper;
import cn.cncc.mojito.rpc.invoker.annotation.EnableMojitoScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;

@ComponentScan(value = {"cn.cncc.caos.common.core.exception", "cn.cncc.caos.common.core.helper", "cn.cncc.caos.data.provider", "cn.cncc.caos.data.client","cn.cncc.caos.common.redis",
         "cn.cncc.caos.common.config", "cn.cncc.caos.common.core.aspect",  "cn.cncc.caos.common.core.config"})
@ServletComponentScan
@SpringBootApplication
@Slf4j
@EnableMojitoScan
@EnableWebMvc
public class BapDataProviderApplication implements CommandLineRunner {

    @Value("${spring.profiles.active}")
    private String profilesActive;
    @Resource
    private BapHelper bapHelper;


    public static void main(String[] args) {
//		SpringApplication.run(DataManageProviderApplication.class, args);
        new SpringApplicationBuilder(BapDataProviderApplication.class).allowCircularReferences(true).run(args);
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

