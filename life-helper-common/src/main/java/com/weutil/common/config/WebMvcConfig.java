package com.weutil.common.config;

import com.weutil.common.annotation.resolver.ClientIpMethodArgumentResolver;
import com.weutil.common.annotation.resolver.UserIdMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * MVC 配置
 * <p>
 * <h2>说明
 * <p>当前类如果 {@code extends WebMvcConfigurationSupport} 时，会导致配置文件中的 Jackson 配置无效，因此要用如下实现接口对方式。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserIdMethodArgumentResolver());
        argumentResolvers.add(new ClientIpMethodArgumentResolver());
    }
}
