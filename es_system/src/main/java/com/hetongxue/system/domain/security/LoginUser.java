package com.hetongxue.system.domain.security;

import com.hetongxue.system.domain.User;
import com.hetongxue.system.domain.vo.MenuVo;
import com.hetongxue.system.domain.vo.RouterVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Description: 用户详情类
 * @Class: LoginUser
 * @Author: hetongxue
 * @DateTime: 2022/9/12 0:58:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginUser implements UserDetails {

    private User user;
    private Collection<GrantedAuthority> authorities;
    private Collection<MenuVo> menus;
    private Collection<RouterVo> routers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}