package com.inlym.lifehelper.auth.core;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 简单鉴权凭证
 * <p>
 * 只提供用户 ID 和权限信息
 */
public class SimpleAuthentication implements Authentication {
    private final Integer userId;

    private final List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    private Boolean authenticated;

    public SimpleAuthentication(int userId) {
        this.userId = userId;
        this.authenticated = true;
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public SimpleAuthentication(int userId, String[] roles) {
        this.userId = userId;

        for (String role : roles) {
            this.authorities.add(new SimpleGrantedAuthority(role));
        }

        this.authenticated = true;
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
