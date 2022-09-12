package com.hetongxue.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hetongxue.system.domain.Permission;

import java.util.List;

/**
 * @Description: 权限服务接口
 * @Interface: PermissionService
 * @Author: hetongxue
 * @DateTime: 2022/9/12 1:12:49
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据用户ID获取权限列表
     */
    List<Permission> selectPermissionByUserId(Long userId);

}