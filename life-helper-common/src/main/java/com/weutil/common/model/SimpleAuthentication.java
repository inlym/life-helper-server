package com.weutil.common.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义简单鉴权凭证
 *
 * <h2>主要用途
 * <p>通过鉴权后，将相关信息带入生成这个鉴权凭证，用于在 Spring Security 中使用。
 *
 * <h2>注意事项
 * <p>当前只用到用户 ID 和用户角色。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
public class SimpleAuthentication implements Authentication {
    private final Long userId;

    private final List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    private Boolean authenticated;

    public SimpleAuthentication(long userId) {
        this.userId = userId;
        this.authorities.add(new SimpleGrantedAuthority(Role.USER));
        this.authenticated = true;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return null;
    }
}
