package com.hetongxue.configuration.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Description: JWT异常处理类
 * @Class: JwtAuthenticationException
 * @Author: hetongxue
 * @DateTime: 2022/9/12 13:45:59
 */
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}