package cn.cncc.caos.common.core.config;

import cn.cncc.caos.common.core.response.FailResponse;
import cn.cncc.caos.common.core.response.IResponse;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;


public abstract class BaseResourceServerConfig extends ResourceServerConfigurerAdapter {

  /**
   * 解决问题： permitAll放过拦截后，如果携带无效token仍进行拦截（iops_login存在此问题）
   * 实现即时请求 无须认证 的接口时带着 过期 或 无效 的 token 时,可以正常继续请求而不会因为 过期或无效的token 导致请求失败
   *
   * @param resources
   */
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    //添加认证管理器
    resources.authenticationManager(new OAuth2AuthenticationManager() {

      @Override
      public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
          return super.authenticate(authentication);
        } catch (AuthenticationException | InvalidTokenException e) {
          return new AnonymousAuthenticationToken(UUID.randomUUID().toString(), "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        }
      }
    });
  }

  public abstract String [] getPermitURIs();
//  public abstract String [] getAuthenticatedURIs();
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers(getPermitURIs()).permitAll()
//        .antMatchers(getAuthenticatedURIs()).authenticated()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling()
        .defaultAuthenticationEntryPointFor((request, response, authException) -> {
          String msg = JacksonUtil.objToJson(new FailResponse<>(IResponse.ERR_LOGIN, authException.getMessage()));
          response.setContentType("application/json;charset=utf-8");
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          response.getWriter().write(msg);
        }, new AntPathRequestMatcher("/**"))
        .and().formLogin();

  }
}