package com.inlym.lifehelper.common.config;

import com.inlym.lifehelper.common.http.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
        restTemplate
            .getMessageConverters()
            .add(new CustomMappingJackson2HttpMessageConverter());

        return restTemplate;
    }

    /**
     * 自定义数据转换器
     *
     * <h2>为什么需要这个转换器？
     * <p>微信部分服务端 API 的 `Content-Type` 为 `text/plain`，需要额外支持解析 JSON。
     *
     * @since 1.3.0
     */
    private static class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public CustomMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.TEXT_HTML);
            setSupportedMediaTypes(mediaTypes);
        }
    }
}
