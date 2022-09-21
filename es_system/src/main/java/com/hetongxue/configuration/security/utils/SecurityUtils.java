package com.hetongxue.configuration.security.utils;

import com.hetongxue.system.domain.Permission;
import com.hetongxue.system.domain.Role;
import com.hetongxue.system.domain.vo.MenuVo;
import com.hetongxue.system.domain.vo.RouterVo;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: Security工具类
 * @Class: SecurityUtils
 * @Author: 何同学
 * @DateTime: 2022/9/12 12:27:11
 */
public class SecurityUtils {

    private static final int LIST_KEY = 0;
    private static final int MENUITEM_KEY = 1;
    private static final int BUTTON_KEY = 2;

    /**
     * 生成菜单列表
     */
    public static List<MenuVo> generateMenu(List<Permission> permissions, Long parentId) {
        List<MenuVo> menus = new ArrayList<>();
        // 判断是否为空
        Optional.ofNullable(permissions)
                // 不为空时创建新的数组
                .orElse(new ArrayList<Permission>())
                // 转换流
                .stream()
                // 过滤 不为空 和 对应父ID 的数据 以及类型不为 按钮 的
                .filter(item -> item != null && Objects.equals(item.getParentId(), parentId) && item.getType() != BUTTON_KEY)
                // 循环遍历
                .forEach(item -> {
                    menus.add(new MenuVo().setName(item.getTitle()).setPath(item.getPath()).setIcon(item.getIcon()).setChildren(generateMenu(permissions, item.getId())));
                });
        return menus;
    }

    /**
     * 生成路由列表
     */
    public static List<RouterVo> generateRouter(List<Permission> permissions, Long parentId) {
        List<RouterVo> routers = new ArrayList<>();
        // 判断是否为空
        Optional.ofNullable(permissions)
                // 不为空时创建新的数组
                .orElse(new ArrayList<Permission>())
                // 转成流
                .stream()
                // 过滤 不为空 和 对应父ID 的数据 以及类型不为 按钮 的
                .filter(item -> item != null && Objects.equals(item.getParentId(), parentId) && item.getType() != BUTTON_KEY)
                // 遍历循环
                .forEach(item -> {
                    routers.add(new RouterVo().setName(item.getName()).setPath(item.getPath()).setComponent(item.getComponents()).setMeta(new RouterVo.MetaVo().setTitle(item.getTitle()).setIcon(item.getIcon()).setKeepAlive(true).setRequireAuth(true)
                            // 当类型是目录时 不存在权限代码
                            .setRoles(item.getType() != LIST_KEY ? permissions.stream()
                                    // 过滤权限代码 不为空 且 不能是目录
                                    .filter(strip -> strip.getPermissionCode() != null)
                                    // 过滤父ID等于当前ID的数据(此时不存在list权限 若要存在list权限 则过滤排序顺序一致的数据即可)
                                    .filter(strip -> Objects.equals(strip.getParentId(), item.getId()))
                                    // 获得权限编码
                                    .map(Permission::getPermissionCode)
                                    // 生成string数组
                                    .toArray(String[]::new) : null)).setChildren(generateRouter(permissions, item.getId())));
                });
        return routers;
    }

    /**
     * 生成权限信息
     */
    public static String generateAuthority(List<Role> roles, List<Permission> permissions) {
        // 获取角色代码列表
        String role = Optional.ofNullable(roles).orElse(new ArrayList<Role>()).stream().filter(Objects::nonNull).map(item -> "ROLE_" + item.getName()).collect(Collectors.joining(","));
        // 获取权限代码列表
        String permission = Optional.ofNullable(permissions).orElse(new ArrayList<Permission>()).stream().filter(Objects::nonNull).map(Permission::getPermissionCode).filter(Objects::nonNull).collect(Collectors.joining(","));
        // 判断角色列表是否为空 为空则只返回权限代码 不为空则返回角色列表+权限代码列表
        return ObjectUtils.isEmpty(role) ? permission : role.concat(",").concat(permission);
    }

}