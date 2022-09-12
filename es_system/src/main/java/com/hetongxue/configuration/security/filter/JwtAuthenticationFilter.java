package com.hetongxue.configuration.security.filter;

import com.hetongxue.configuration.redis.RedisUtils;
import com.hetongxue.configuration.security.exception.JwtAuthenticationException;
import com.hetongxue.configuration.security.utils.SecurityUtils;
import com.hetongxue.constant.Base;
import com.hetongxue.system.domain.User;
import com.hetongxue.system.service.PermissionService;
import com.hetongxue.system.service.RoleService;
import com.hetongxue.system.service.UserService;
import com.hetongxue.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @Description: JWT认证过滤器(每一次的请求都会访问该过滤器)
 * @Class: JwtAuthenticationFilter
 * @Author: hetongxue
 * @DateTime: 2022/9/12 13:37:52
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 1.获取token信息
        String token = request.getHeader(Base.AUTHORIZATION_KEY);
        // 1.1 判断token是否存在 不存在则直接继续过滤链并返回
        if (Objects.isNull(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 2.解析token信息
        Claims claims = jwtUtils.getClaims(token);
        // 2.1 校验token是否就合法
        if (Objects.isNull(claims)) {
            throw new JwtAuthenticationException("token不合法");
        }
        // 2.2 校验token是否过期
        String redisToken = String.valueOf(redisUtils.getValue(Base.AUTHORIZATION_KEY));
        if (Objects.isNull(redisToken) || jwtUtils.isExpired(claims)) {
            throw new JwtAuthenticationException("token已过期");
        }
        // 2.3 校验用户token与redis中的token是否一致
        if (!Objects.equals(token, redisToken)) {
            throw new JwtAuthenticationException("token不存在");
        }
        // 3.获取id和subject
        Long id = Long.valueOf(claims.getId());
        String username = claims.getSubject();
        User user = userService.selectOneByUsername(username);
        if (!user.getId().equals(id)) {
            throw new JwtAuthenticationException("token异常");
        }
        // 4.获取权限列表
        String authority = SecurityUtils.generateAuthority(roleService.selectRoleByUserId(user.getId()), permissionService.selectPermissionByUserId(user.getId()));
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
        // 5.封装Authentication
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorityList);
        // 6.存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }

}