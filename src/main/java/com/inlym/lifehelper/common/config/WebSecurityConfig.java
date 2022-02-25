package com.inlym.lifehelper.common.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Spring Security 配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-22
 * @since 1.0.0
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
            .disable();

        http
            .httpBasic()
            .disable();

        http
            .csrf()
            .disable();

        // 默认所有 API 均免鉴权，需要鉴权的 API 再额外使用 @Secured 注解声明需要的角色
        http
            .authorizeRequests()
            .anyRequest()
            .permitAll();

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
