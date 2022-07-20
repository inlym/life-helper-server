package com.inlym.lifehelper.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * HTTP 请求客户端配置
 *
 * <h2>说明
 * <p>通用 HTTP 请求客户端
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/7/20
 * @since 1.3.0
 **/
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
