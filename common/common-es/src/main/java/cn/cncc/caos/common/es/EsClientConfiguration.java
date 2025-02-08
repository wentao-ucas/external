package cn.cncc.caos.common.es;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * ElasticSearch的配置类
 */
@Configuration
public class EsClientConfiguration {

  @Value("${spring.elasticsearch.rest.username}")
  private String username;

  @Value("${spring.elasticsearch.rest.password}")
  private String password;

  public static final RequestOptions COMMON_OPTIONS;

  static {
    RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
    COMMON_OPTIONS = builder.build();
  }

  @Bean
  public RestHighLevelClient restHighLevelClient(@Autowired RestClientBuilder restClientBuilder) {
    //开始设置用户名和密码
    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

    return new RestHighLevelClient(restClientBuilder.setHttpClientConfigCallback(requestConfig ->
        requestConfig.setKeepAliveStrategy((response, context) -> TimeUnit.MINUTES.toMillis(3))
            .setDefaultCredentialsProvider(credentialsProvider)));
  }
}
