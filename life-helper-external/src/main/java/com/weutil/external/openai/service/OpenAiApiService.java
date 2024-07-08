package com.weutil.external.openai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weutil.external.openai.config.OpenAiProperties;
import com.weutil.external.openai.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.function.Consumer;

/**
 * OpenAi 的 HTTP 请求服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/20
 * @since 2.1.0
 **/
@Service
public class OpenAiApiService {
    private final RestClient restClient;

    public OpenAiApiService(OpenAiProperties openAiProperties, ObjectMapper objectMapper) {
        Consumer<HttpHeaders> jsonContentHeaders = headers -> {
            headers.setBearerAuth(openAiProperties.getKey());
            headers.setContentType(MediaType.APPLICATION_JSON);
        };

        this.restClient = RestClient
                .builder()
                .messageConverters(converters -> {
                    // 说明：RestClient 原生的转化器内自建的 objectMapper 不会过滤值为 null 的属性，此处相当于做了个替换
                    converters.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
                    converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
                })
                .baseUrl(openAiProperties.getBaseUrl())
                .defaultHeaders(jsonContentHeaders)
                .defaultStatusHandler(new OpenAiResponseErrorHandler())
                .build();
    }

    /**
     * 发起会话补全
     *
     * @param request 请求数据
     *
     * @since 2.2.0
     */
    public ChatCompletion createChatCompletion(ChatCompletionRequest request) {
        ResponseEntity<ChatCompletion> response = this.restClient
                .post()
                .uri("/v1/chat/completions")
                .body(request)
                .retrieve()
                .toEntity(ChatCompletion.class);

        return response.getBody();
    }

    /**
     * 创建图片
     *
     * @param request 请求数据
     *
     * @since 2.2.0
     */
    public ImageResponse createImage(ImageRequest request) {
        ResponseEntity<ImageResponse> response = this.restClient
                .post()
                .uri("/v1/images/generations")
                .body(request)
                .retrieve()
                .toEntity(ImageResponse.class);

        return response.getBody();
    }
}
