package com.inlym.lifehelper.common.config;

import com.inlym.lifehelper.common.annotation.ClientIpMethodArgumentResolver;
import com.inlym.lifehelper.common.annotation.UserIdMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * MVC 配置
 *
 * <h2>说明
 * <p>当前类如果 {@code extends WebMvcConfigurationSupport} 时，会导致配置文件中的 Jackson 配置无效，因此要用如下实现接口对方式。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @version 1.1.1
 * @date 2022-02-14
 * @since 1.0.0
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${lifehelper.web-url}")
    private String webUrl;

    /**
     * 跨域资源共享配置
     *
     * @since 1.2.3
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins(webUrl)
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .maxAge(86400L);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserIdMethodArgumentResolver());
        argumentResolvers.add(new ClientIpMethodArgumentResolver());
    }
}
