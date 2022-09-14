package com.hetongxue.configuration.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Description: 验证码异常处理类
 * @Class: CaptchaAuthenticationException
 * @Author: hetongxue
 * @DateTime: 2022/9/14 16:55:24
 */
public class CaptchaAuthenticationException extends AuthenticationException {

    public CaptchaAuthenticationException(String msg) {
        super(msg);
    }

}