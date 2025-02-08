package cn.cncc.caos.external.provider;



import cn.cncc.caos.common.core.helper.BapHelper;
import cn.cncc.mojito.rpc.invoker.annotation.EnableMojitoScan;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@ComponentScan(value = {"cn.cncc.caos.common.core.exception", "cn.cncc.caos.common.core.helper", "cn.cncc.caos.external.provider", "cn.cncc.caos.common.core.utils","cn.cncc.caos.common.redis",
         "cn.cncc.caos.common.config","cn.cncc.caos.common.http", "cn.cncc.caos.common.core.aspect", "cn.cncc.caos.data.client", "cn.cncc.caos.common.core.config"})
@ServletComponentScan
@SpringBootApplication
@Slf4j
@EnableMojitoScan
@EnableWebMvc
public class BapExternalProviderApplication implements CommandLineRunner {

    @Value("${spring.profiles.active}")
    private String profilesActive;
    @Resource
    private BapHelper bapHelper;


    public static void main(String[] args) {
//		SpringApplication.run(DataManageProviderApplication.class, args);
        new SpringApplicationBuilder(BapExternalProviderApplication.class).allowCircularReferences(true).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(String.format("spring.profiles.active=%s, caos.env=%s", profilesActive, bapHelper.getBapEnv()));
    }

    // 配置 RestTemplate
    @Bean
    public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //配置信赖策略
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        // 配置NoopHostnameVerifier.INSTANCE
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectionRequestTimeout(10000);
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(10000);

        return new RestTemplate(requestFactory);
    }
}

