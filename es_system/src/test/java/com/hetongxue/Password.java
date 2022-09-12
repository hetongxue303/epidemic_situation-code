package com.hetongxue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @Description:
 * @Class: Password
 * @Author: hetongxue
 * @DateTime: 2022/9/12 22:12:51
 */
@SpringBootTest
public class Password {

    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Test
    void test() {
        String encode = passwordEncoder.encode("123456");
        System.out.println("encode = " + encode);
    }

}