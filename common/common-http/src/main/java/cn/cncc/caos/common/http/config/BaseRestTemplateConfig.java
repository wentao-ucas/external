package cn.cncc.caos.common.http.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
@Slf4j
public class BaseRestTemplateConfig {
  private static HttpComponentsClientHttpRequestFactory generateHttpRequestFactory()
      throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
    TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
    SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
    SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

    HttpClientBuilder httpClientBuilder = HttpClients.custom();
    httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
    CloseableHttpClient httpClient = httpClientBuilder.build();
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setHttpClient(httpClient);
    return factory;
  }

  @Bean
  public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
    return new RestTemplate(factory);
  }

  // 配置 RestTemplate
  @Bean(name = "httpRestTemplate")
  public RestTemplate httpRestTemplate() {
    return new RestTemplate();
  }

  @Bean(name = "httpsRestTemplate")
  public RestTemplate httpsRestTemplate() {
    HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = null;
    try {
      httpComponentsClientHttpRequestFactory = BaseRestTemplateConfig.generateHttpRequestFactory();
    } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
      log.error("", e);
    }
    assert httpComponentsClientHttpRequestFactory != null;
    return new RestTemplate(httpComponentsClientHttpRequestFactory);
  }

  @Bean
  public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(60000);
    factory.setReadTimeout(30000);
    factory.setConnectionRequestTimeout(60000);

    HttpClient httpClient = HttpClientBuilder.create()
            //重试次数，默认是3次，没有开启
            .setRetryHandler(new DefaultHttpRequestRetryHandler(2, true))
            .build();
    factory.setHttpClient(httpClient);
    return factory;
  }
}