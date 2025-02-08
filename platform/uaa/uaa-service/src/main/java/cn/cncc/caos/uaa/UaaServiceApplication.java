package cn.cncc.caos.uaa;

import cn.cncc.caos.common.core.config.BaseBapConfig;
import cn.cncc.mojito.rpc.invoker.annotation.EnableMojitoScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Set;

@ComponentScan({"cn.cncc.caos"})
@EnableScheduling
@EnableMojitoScan
@EnableAsync
@Slf4j
@SpringBootApplication
public class UaaServiceApplication extends BaseBapConfig implements CommandLineRunner {
  @Value("${spring.profiles.active}")
  private String profilesActive;

  @Resource
  private RedisTemplate redisTemplate;

  @Value("${permission.redis.key}")
  private String permissionRedisKey;

  @Value("${rule.redis.key}")
  private String ruleRedisKey;


  @Resource
  private StringRedisTemplate stringRedisTemplate;

  public static void main(String[] args) {
//		SpringApplication.run(UaaCenterApplication.class, args);
    new SpringApplicationBuilder(UaaServiceApplication.class).allowCircularReferences(true).run(args);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void run(String... args) throws Exception {
    log.info(String.format("spring.profiles.active=%s", profilesActive));
    //清除redis
    //清除权限
    redisTemplate.delete(permissionRedisKey);
    //清除数据权限
    Set<String> keys = stringRedisTemplate.keys("dataPermission" + "*");
    if (!CollectionUtils.isEmpty(keys)) {
      stringRedisTemplate.delete(keys);
    }
    //清除规则
    redisTemplate.delete(ruleRedisKey);
    log.info("redis cache clean");
  }
}
