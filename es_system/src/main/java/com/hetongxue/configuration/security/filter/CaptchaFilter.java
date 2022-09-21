package com.hetongxue.configuration.security.filter;

import com.hetongxue.configuration.redis.RedisUtils;
import com.hetongxue.configuration.security.exception.CaptchaAuthenticationException;
import com.hetongxue.configuration.security.handler.LoginFailureHandler;
import com.hetongxue.constant.Base;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Description: 验证码过滤器
 * @Class: CaptchaFilter
 * @Author: hetongxue
 * @DateTime: 2022/9/14 16:34:19
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    private final static String LOGIN_METHOD = "POST";
    private final static String LOGIN_PATH = "/auth/login";
    private final static String CAPTCHA_KEY = "code";

    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            // 判断是否为登录URL
            if (!Objects.equals(request.getRequestURI(), LOGIN_PATH)) {
                filterChain.doFilter(request, response);
                return;
            }
            // 判断是否为登录URL和POST请求
            if (Objects.equals(request.getRequestURI(), LOGIN_PATH) && LOGIN_METHOD.equalsIgnoreCase(request.getMethod())) {
                String code = request.getParameter(CAPTCHA_KEY);
                String redisCode = (String) redisUtils.getValue(Base.CAPTCHA_KEY);
                // 验证码不一致：抛出异常
                if (!Objects.equals(code, redisCode)) {
                    throw new CaptchaAuthenticationException("验证码错误");
                }
                // 验证码一致：删除redis中的验证码并放行
                redisUtils.delete(Base.CAPTCHA_KEY);
                filterChain.doFilter(request, response);
            }
        } catch (CaptchaAuthenticationException e) {
            // 删除redis中的验证码并放行
            redisUtils.delete(Base.CAPTCHA_KEY);
            // 执行验证失败处理类
            loginFailureHandler.onAuthenticationFailure(request, response, e);
        }
    }

}