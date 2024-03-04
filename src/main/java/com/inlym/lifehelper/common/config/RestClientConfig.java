package com.inlym.lifehelper.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.function.Consumer;

/**
 * HTTP 请求客户端
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/3/1
 * @since 2.3.0
 **/
@Configuration
@RequiredArgsConstructor
public class RestClientConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public RestClient restClient() {
        Consumer<HttpHeaders> jsonContentHeaders = headers -> {
            headers.setContentType(MediaType.APPLICATION_JSON);
        };

        return RestClient
            .builder()
            .messageConverters(converters -> {
                // 说明：RestClient 原生的转化器内自建的 objectMapper 不会过滤值为 null 的属性，此处相当于做了个替换
                converters.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
                converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
            })
            .defaultHeaders(jsonContentHeaders)
            .build();
    }
}
