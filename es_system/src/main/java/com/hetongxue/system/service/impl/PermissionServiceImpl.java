package com.hetongxue.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hetongxue.system.domain.Permission;
import com.hetongxue.system.mapper.PermissionMapper;
import com.hetongxue.system.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 权限服务实现类
 * @Class: PermissionServiceImpl
 * @Author: hetongxue
 * @DateTime: 2022/9/12 1:16:05
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Permission> selectPermissionByUserId(Long userId) {
        return permissionMapper.selectList(new QueryWrapper<Permission>().inSql("id", "select permission_id from sys_role_permission where role_id in " + "(select distinct role_id from sys_user_role where user_id = " + userId + ")").orderByAsc("sort"));
    }
}