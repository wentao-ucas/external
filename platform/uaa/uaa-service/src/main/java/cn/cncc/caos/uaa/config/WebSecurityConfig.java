package cn.cncc.caos.uaa.config;

import cn.cncc.caos.uaa.service.BaseUserDetailService;
import cn.cncc.caos.uaa.encoder.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BaseUserDetailService baseUserDetailServiceImp;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        /*auth.inMemoryAuthentication()
                .passwordEncoder(new CustomPasswordEncoder())
                .withUser("user_1").password("111111").roles("SUPER_ADMIN")
                .and()
                .withUser("user_2").password("123456").authorities("USER");
        */
         // 加密方法(不加密)
         auth.userDetailsService(baseUserDetailServiceImp).passwordEncoder(new CustomPasswordEncoder());;
        // auth.userDetailsService(baseUserDetailServiceImp).passwordEncoder(passwordEncoder);;
    }

    //该项目只在ResourceServerConfigurerAdapter（EnableResourceServer）未配置才生效
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers().anyRequest()
            .and()
            .authorizeRequests()
            .antMatchers("/oauth/**","/oauth/authorize").authenticated()
            .and().httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll();

        //关闭默认的csrf认证
        http.csrf().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
