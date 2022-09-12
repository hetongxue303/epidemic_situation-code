package com.hetongxue.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hetongxue.system.domain.Role;
import com.hetongxue.system.mapper.RoleMapper;
import com.hetongxue.system.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 角色服务实现类
 * @Class: RoleServiceImpl
 * @Author: hetongxue
 * @DateTime: 2022/9/12 1:14:57
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> selectRoleByUserId(Long userId) {
        return roleMapper.selectList(new QueryWrapper<Role>().inSql("id", "select distinct role_id from sys_user_role where user_id = " + userId));
    }

}