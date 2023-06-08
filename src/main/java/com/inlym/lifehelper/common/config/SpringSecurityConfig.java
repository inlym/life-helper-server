package com.inlym.lifehelper.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 配置
 *
 * <h2>说明
 * <p>目前配置方式为 3.1.0 版本后的优选配置方式。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/2
 * @since 1.2.3
 **/
@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig {
    private final HttpSecurity http;

    @Bean
    public SecurityFilterChain securityFilterChain() throws Exception {
        // 备注：实际上可以使用 {@code .and()} 来连接各个语句，但笔者觉得使用 {@code http} 看起来更优雅。
        
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(registry -> registry.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 默认所有 API 均免鉴权，需要鉴权的 API 再额外使用 @Secured 注解声明需要的角色
        http.authorizeHttpRequests(registry -> registry
            .anyRequest()
            .permitAll());

        return http.build();
    }
}
