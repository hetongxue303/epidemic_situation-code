package com.hetongxue.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hetongxue.system.domain.User;

/**
 * @Description: 用户服务接口
 * @Interface: UserService
 * @Author: hetongxue
 * @DateTime: 2022/9/12 1:11:23
 */
public interface UserService extends IService<User> {

    User selectOneByUsername(String username);

}