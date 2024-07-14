package com.weutil.common.config;

import com.weutil.common.annotation.resolver.ClientIpMethodArgumentResolver;
import com.weutil.common.annotation.resolver.UserIdMethodArgumentResolver;
import com.weutil.common.interceptor.LogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
    private final LogInterceptor logInterceptor;

    /**
     * 配置拦截器
     *
     * @since 1.9.1
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).order(1).addPathPatterns("/**").excludePathPatterns("/ping");
    }
    
    /**
     * 跨域资源共享配置
     *
     * @since 1.2.3
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*").maxAge(86400L);
    }

    /**
     * 注解解析器配置
     *
     * @since 1.2.3
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserIdMethodArgumentResolver());
        argumentResolvers.add(new ClientIpMethodArgumentResolver());
    }
}
