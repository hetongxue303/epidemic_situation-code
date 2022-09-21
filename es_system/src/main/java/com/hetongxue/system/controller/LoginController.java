package com.hetongxue.system.controller;

import com.hetongxue.configuration.redis.RedisUtils;
import com.hetongxue.constant.Base;
import com.hetongxue.response.Result;
import com.wf.captcha.ArithmeticCaptcha;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 登录模块
 * @Class: LoginController
 * @Author: hetongxue
 * @DateTime: 2022/9/12 1:30:23
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    private static final long TIME = 60;
    private static final TimeUnit TIMEUNIT = TimeUnit.SECONDS;
    private static final int WIDTH = 111;
    private static final int HEIGHT = 36;
    private static final int LENGTH = 2;

    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/getCode")
    public Result getVerify() {
        // 在java11中使用Nashorn engine  会出现 Warning: Nashorn engine is planned to be removed from a future JDK release
        System.setProperty("nashorn.args", "--no-deprecation-warning");
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(WIDTH, HEIGHT, LENGTH);
        // 设置60秒过期
        redisUtils.setValue(Base.CAPTCHA_KEY, captcha.text(), TIME, TIMEUNIT);
        return Result.Success(captcha.toBase64());
    }
}