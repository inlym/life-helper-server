package com.inlym.lifehelper.extern.chatgptold.service;

import com.inlym.lifehelper.extern.chatgptold.config.ChatGPTProperties;
import com.inlym.lifehelper.extern.chatgptold.exception.ChatGPTCommonException;
import com.inlym.lifehelper.extern.chatgptold.pojo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * ChatGPT 的 HTTP 请求服务
 *
 * <h2>主要用途
 * <p>封装 HTTP 请求
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/25
 * @since 1.9.5
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ChatGPTHttpService {
    private final ChatGPTProperties properties;

    private final RestTemplate restTemplate;

    /**
     * 会话补全
     *
     * @param prompt 提示语
     *
     * @see <a href="https://platform.openai.com/docs/api-reference/completions/create">Create completion</a>
     * @since 1.9.5
     */
    public CreateCompletionResponse createCompletion(String prompt) {
        Map<String, String> headers = buildHeaderMap();

        CreateCompletionRequestData requestData = CreateCompletionRequestData
            .builder()
            .model("gpt-3.5-turbo")
            .prompt(prompt)
            .maxTokens(1000)
            .suffix("")
            .build();

        ProxyRequest request = ProxyRequest
            .builder()
            .method("POST")
            .url("https://api.openai.com/v1/completions")
            .headers(headers)
            .data(requestData)
            .build();

        String proxyUrl = properties.getProxyUrl();
        CreateCompletionResponse responseData = restTemplate.postForObject(proxyUrl, request, CreateCompletionResponse.class);

        assert responseData != null;
        if (responseData.getError() != null) {
            throw new ChatGPTCommonException(responseData
                                                 .getError()
                                                 .getMessage());
        }

        return responseData;
    }

    /**
     * 创建会话消息补全
     *
     * @param options 请求数据
     *
     * @see <a href="https://platform.openai.com/docs/api-reference/chat/create">Create chat completion</a>
     * @since 1.9.6
     */
    public CreateChatCompletionResponse createChatCompletion(CreateChatCompletionOptions options) {
        Map<String, String> headers = buildHeaderMap();
        String proxyUrl = properties.getProxyUrl();

        ProxyRequest request = ProxyRequest
            .builder()
            .method("POST")
            .url("https://api.openai.com/v1/chat/completions")
            .headers(headers)
            .data(options)
            .build();

        CreateChatCompletionResponse response = restTemplate.postForObject(proxyUrl, request, CreateChatCompletionResponse.class);

        assert response != null;
        if (response.getError() != null) {
            throw new ChatGPTCommonException(response
                                                 .getError()
                                                 .getMessage());
        }

        return response;
    }

    private String getKey() {
        String[] keys = properties.getKeys();
        // 每隔100秒换一个 key
        int seq = ((int) Math.floor(System.currentTimeMillis() / 100000.0)) % keys.length;

        return keys[seq];
    }

    /**
     * 构建请求头
     *
     * @since 1.9.6
     */
    private Map<String, String> buildHeaderMap() {
        Map<String, String> headers = new HashMap<>(16);
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getKey());

        return headers;
    }
}
