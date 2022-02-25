package com.inlym.lifehelper.common.config;

import com.inlym.lifehelper.common.annotation.UserIdMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-14
 * @since 1.0.0
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserIdMethodArgumentResolver());
    }
}
