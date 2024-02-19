package com.inlym.lifehelper.ai.openai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inlym.lifehelper.ai.openai.config.OpenAiProperties;
import com.inlym.lifehelper.ai.openai.model.ChatCompletion;
import com.inlym.lifehelper.ai.openai.model.ChatCompletionRequest;
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
            .build();
    }

    /**
     * 发起会话补全
     *
     * @param chatRequest 请求数据
     *
     * @since 2.2.0
     */
    public ResponseEntity<ChatCompletion> chatCompletionEntity(ChatCompletionRequest chatRequest) {
        return this.restClient
            .post()
            .uri("/v1/chat/completions")
            .body(chatRequest)
            .retrieve()
            .toEntity(ChatCompletion.class);
    }
}
