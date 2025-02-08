package cn.cncc.caos.gateway;


import cn.cncc.mojito.rpc.invoker.annotation.EnableMojitoScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMojitoScan
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
