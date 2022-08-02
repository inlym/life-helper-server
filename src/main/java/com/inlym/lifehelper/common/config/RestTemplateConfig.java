package com.inlym.lifehelper.common.config;

import com.inlym.lifehelper.common.http.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
        RestTemplate restTemplate = new RestTemplate();

        // 设置拦截器，答应请求信息，方便Debug
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HttpLoggingInterceptor());

        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}
