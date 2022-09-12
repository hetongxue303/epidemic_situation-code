package com.hetongxue.system.controller;

import com.hetongxue.response.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 测试模块
 * @Class: TestController
 * @Author: hetongxue
 * @DateTime: 2022/9/12 0:04:54
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public Result hello() {
        return Result.Success().setMessage("hello es!");
    }

}