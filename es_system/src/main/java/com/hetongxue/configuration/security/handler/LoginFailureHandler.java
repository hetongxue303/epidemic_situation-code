package com.hetongxue.configuration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetongxue.configuration.security.exception.CaptchaAuthenticationException;
import com.hetongxue.response.Result;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 认证失败处理器
 * @Class: LoginFailureHandler
 * @Author: hetongxue
 * @DateTime: 2022/9/12 17:39:28
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Result result = Result.Error().setMessage("登陆失败，未知异常！");
        response.setContentType("application/json;charset=utf-8");
        if (exception instanceof AccountExpiredException) {
            result.setMessage("账户已过期");
        }
        if (exception instanceof BadCredentialsException) {
            result.setMessage("用户名或密码错误");
        }
        if (exception instanceof CredentialsExpiredException) {
            result.setMessage("密码已过期");
        }
        if (exception instanceof DisabledException) {
            result.setMessage("账户被禁用");
        }
        if (exception instanceof LockedException) {
            result.setMessage("账户已被锁");
        }
        if (exception instanceof InternalAuthenticationServiceException) {
            result.setMessage("账户不存在");
        }
        // 认证服务异常 用户没有找到异常 验证码异常
        if (exception instanceof AuthenticationServiceException || exception instanceof UsernameNotFoundException || exception instanceof CaptchaAuthenticationException) {
            result.setMessage(exception.getMessage());
        }
        response.getWriter().println(new ObjectMapper().writeValueAsString(result));
    }

}