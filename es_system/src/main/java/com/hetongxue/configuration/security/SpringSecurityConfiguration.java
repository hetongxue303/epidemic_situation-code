package com.hetongxue.configuration.security;

import com.hetongxue.configuration.security.filter.JwtAuthenticationFilter;
import com.hetongxue.configuration.security.handler.*;
import com.hetongxue.system.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

/**
 * @Description: spring security配置类
 * @Class: SpringSecurityConfiguration
 * @Author: hetongxue
 * @DateTime: 2022/9/12 0:07:11
 */
@Configuration
public class SpringSecurityConfiguration {

    /**
     * 配置参数
     */
    public static final String LOGIN_PATH = "/auth/login";
    public static final String LOGOUT_PATH = "/auth/logout";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    /**
     * 请求白名单
     */
    private final String[] REQUEST_WHITE_LIST = {"/auth/login", "/auth/getCode"};

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private UserLogoutSuccessHandler logoutSuccessHandler;
    @Resource
    private UserDetailsServiceImpl userService;
    @Resource
    private UserAuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private UserAccessDeniedHandler accessDeniedHandler;

    /**
     * 配置安全过滤器链
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 配置授权请求
        http.authorizeRequests()
                // 指定未登录时允许访问的URL
                .antMatchers(REQUEST_WHITE_LIST).permitAll()
                // 指定认证成功后才能访问的URL
                .anyRequest().authenticated();

        // 配置表单登录
        http.formLogin()
                // 设置登录URL
                .loginProcessingUrl(LOGIN_PATH)
                // 设置登陆请求参数
                .usernameParameter(USERNAME).passwordParameter(PASSWORD)
                // 设置认证成功处理器
                .successHandler(loginSuccessHandler)
                // 认证失败处理器
                .failureHandler(loginFailureHandler);

        // 配置注销登录
        http.logout()
                // 设置注销URL
                .logoutUrl(LOGOUT_PATH)
                // 设置注销成功处理类
                .logoutSuccessHandler(logoutSuccessHandler);

        // 配置异常处理
        http.exceptionHandling()
                // 设置匿名用户处理类(未登录 需要跳转登陆)
                .authenticationEntryPoint(authenticationEntryPoint)
                // 设置无权访问处理类(已登录 但无权限时)
                .accessDeniedHandler(accessDeniedHandler);

        // 配置跨域
        http.cors()
                // 设置跨域配置类
                .configurationSource(corsConfigurationSource());
        // 配置csrf
        http.csrf().disable();

        // 配置会话管理
        http.sessionManagement()
                // 设置创建会话策略
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 在 UsernamePasswordAuthenticationFilter 之前添加 jwtAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.httpBasic(Customizer.withDefaults()).build();
    }

    /**
     * 配置认证处理器
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // 设置密码编码器
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        // 设置UserDetails实现类
        authenticationProvider.setUserDetailsService(userService);
        // 这里要隐藏系统默认的提示信息，否则一直显示账户或密码错误
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    /**
     * 注入密码加密处理
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 解决添加security后 springboot自身配置的跨域不生效问题(主要由于security的过滤器优先级高于springboot的优先级)
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}