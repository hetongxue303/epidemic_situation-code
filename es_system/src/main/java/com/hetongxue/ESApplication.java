package com.hetongxue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: 程序入口类
 * @Class: ESApplication
 * @Author: hetongxue
 * @DateTime: 2022/9/11 23:41:39
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.hetongxue.system.mapper")
public class ESApplication {

    public static void main(String[] args) {
        SpringApplication.run(ESApplication.class, args);
    }

}