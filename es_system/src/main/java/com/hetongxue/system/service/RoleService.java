package com.hetongxue.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hetongxue.system.domain.Role;

import java.util.List;

/**
 * @Description: 角色服务接口
 * @Interface: RoleService
 * @Author: hetongxue
 * @DateTime: 2022/9/12 1:12:00
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户ID获取角色列表
     */
    List<Role> selectRoleByUserId(Long userId);

}