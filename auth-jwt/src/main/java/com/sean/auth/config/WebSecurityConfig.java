package com.sean.auth.config;

import com.sean.auth.security.jwt.CustomAccessDeineHandler;
import com.sean.auth.security.jwt.JwtAuthenticationEntryPoint;
import com.sean.auth.security.jwt.JwtAuthenticationFilter;
import com.sean.auth.security.jwt.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import com.sean.auth.security.jwt.config.FilterIgnorePropertiesConfig;

/**
 * Spring Security Config
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/8 21:23
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 读取忽略的配置文件
     */
    @Autowired
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义身份验证组件
        auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
//        http.cors().and().csrf().disable()
//                .authorizeRequests()
//                // 跨域预检请求
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                // web jars
//                .antMatchers("/webjars/**").permitAll()
//                // 查看SQL监控（druid）
//                .antMatchers("/druid/**").permitAll()
//                // 首页和登录页面
//                .antMatchers("/").permitAll()
//                .antMatchers("/login").permitAll()
//                // swagger
//                .antMatchers("/swagger-ui.html").permitAll()
//                .antMatchers("/swagger-resources/**").permitAll()
//                .antMatchers("/v2/api-docs").permitAll()
//                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
//                // 验证码
//                .antMatchers("/captcha.jpg**").permitAll()
//                // 服务监控
//                .antMatchers("/actuator/**").permitAll()
//                // 其他所有请求需要身份认证
//                .anyRequest().authenticated();
//        // 退出登录处理器
//        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
//        // token验证过滤器
//        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();
        http.headers().frameOptions().disable();

        // 【1】授权异常及不创建会话(不使用session)
        http.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeineHandler()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 允许不登录访问的接口
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();

        // 【2】从配置文件读取URL
        registry.antMatchers(HttpMethod.OPTIONS, "/**").anonymous();
        filterIgnorePropertiesConfig.getUrls().forEach(url -> registry.antMatchers(url).permitAll());

        // 需要登录才允许访问
        filterIgnorePropertiesConfig.getAuthenticates().forEach(url -> registry.antMatchers(url).authenticated());

        // 其他的严格权限控制，必须权限拥有的菜单中对应api_url才允许访问 【3】权限控制
//        registry.anyRequest().authenticated();

        // 把token拦截器配置在security用户名和密码拦截器之前， 【4】从token解析的逻辑
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        // 退出登录处理器
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
