package cn.cncc.caos.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    //密码模式需要在OAuth2AuthorizationServer 中配置AuthenticationManager
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        // 获取当日剩余时间
//        long secondsLeftTodayLong = 86400 - DateUtils.getFragmentInSeconds(Calendar.getInstance(), Calendar.DATE);
        long secondsLeftTodayLong = 86400;
        int secondsLeftTodayInt = Integer.parseInt(String.valueOf(secondsLeftTodayLong));

        clients.inMemory()
                .withClient("sso_client")
                .secret(passwordEncoder.encode("sso_secret"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("sso_scope")
                .authorities("client")
                //.accessTokenValidit      ySeconds(7200)
//                .accessTokenValiditySeconds(secondsLeftTodayInt)
                .and().withClient("open_client")
                .secret(passwordEncoder.encode("open_secret"))
                .authorizedGrantTypes("authorization_code","password", "refresh_token")
                .scopes("open_scope")
                .accessTokenValiditySeconds(secondsLeftTodayInt);
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security)
            throws Exception {
        //允许表单认证
        //不加密
//        security.passwordEncoder(new CustomPasswordEncoder());
//        security.passwordEncoder(new BCryptPasswordEncoder());

        security.allowFormAuthenticationForClients();
        //security.tokenKeyAccess("isAuthenticated()");
        security.tokenKeyAccess("permitAll()");
        security.checkTokenAccess("permitAll()");
    }

    @Bean
    public RedisTokenStore redisTokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        return redisTokenStore;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
//        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
        endpoints.tokenStore(redisTokenStore())
            .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }
}
