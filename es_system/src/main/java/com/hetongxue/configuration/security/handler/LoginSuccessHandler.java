package com.hetongxue.configuration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetongxue.configuration.redis.RedisUtils;
import com.hetongxue.constant.Base;
import com.hetongxue.response.Result;
import com.hetongxue.system.domain.User;
import com.hetongxue.system.domain.security.LoginUser;
import com.hetongxue.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 认证成功处理器
 * @Class: LoginSuccessHandler
 * @Author: hetongxue
 * @DateTime: 2022/9/12 17:37:13
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final long TIMEOUT = 7;
    private static final TimeUnit TIMEUNIT = TimeUnit.DAYS;
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 设置字符编码
        response.setContentType("application/json;charset=utf-8");
        // 设置状态
        response.setStatus(HttpStatus.OK.value());
        // 获取当前用户信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User user = loginUser.getUser();
        // 生成token
        String token = jwtUtils.generateToken(user.getId(), user.getUsername());
        // 将token存于redis中(默认3天)
        redisUtils.setValue(Base.AUTHORIZATION_KEY, token, TIMEOUT, TIMEUNIT);
        // 将token设置在请求头上
        response.setHeader(Base.AUTHORIZATION_KEY, token);
        // 自定义返回内容
        response.getWriter().println(new ObjectMapper().writeValueAsString(Result.Success(loginUser).setMessage("登陆成功")));
    }

}