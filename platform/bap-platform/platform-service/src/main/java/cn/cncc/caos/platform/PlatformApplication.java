package cn.cncc.caos.platform;

import cn.cncc.caos.common.core.config.BaseBapConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

//@EnableMojitoScan
@SpringBootApplication
@ComponentScan({"cn.cncc.caos"})
@Slf4j
public class PlatformApplication extends BaseBapConfig implements CommandLineRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder(PlatformApplication.class).allowCircularReferences(true).run(args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("run.....xxxxx");
  }
}
