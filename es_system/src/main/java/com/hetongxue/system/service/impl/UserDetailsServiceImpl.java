package com.hetongxue.system.service.impl;

import com.hetongxue.configuration.security.utils.SecurityUtils;
import com.hetongxue.system.domain.Permission;
import com.hetongxue.system.domain.Role;
import com.hetongxue.system.domain.User;
import com.hetongxue.system.domain.security.LoginUser;
import com.hetongxue.system.domain.vo.MenuVo;
import com.hetongxue.system.domain.vo.RouterVo;
import com.hetongxue.system.service.PermissionService;
import com.hetongxue.system.service.RoleService;
import com.hetongxue.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Description: UserDetailsService实现类
 * @Class: UserDetailsServiceImpl
 * @Author: hetongxue
 * @DateTime: 2022/9/12 17:44:11
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.获取用户信息
        User user = userService.selectOneByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 2.获取权限信息
        List<Permission> permissions = permissionService.selectPermissionByUserId(user.getId());
        List<Role> roles = roleService.selectRoleByUserId(user.getId());
        String authorities = SecurityUtils.generateAuthority(roles, permissions);
        // 3.获取菜单信息
        List<MenuVo> menus = SecurityUtils.generateMenu(permissions, 0L);
        // 4.获取路由信息
        List<RouterVo> routers = SecurityUtils.generateRouter(permissions, 0L);

        return new LoginUser(user, authorities, menus, routers);
    }

}