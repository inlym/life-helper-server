package com.inlym.lifehelper.extern.openai.service;

import com.inlym.lifehelper.extern.openai.model.ChatCompletionRequestBody;
import com.inlym.lifehelper.extern.openai.model.ChatCompletionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * OpenAI（即 ChatGPT） HTTP 请求服务
 * <p>
 * <h2>主要用途
 * <p>封装 HTTP 请求，以便内部调用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/9
 * @since 2.0.3
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class OpenAiHttpService {
    private final OpenAiProfileService openAiProfileService;

    private final RestTemplate restTemplate;

    /**
     * 发送创建会话补全请求
     *
     * @param options 请求数据
     *
     * @date 2023/9/10
     * @see <a href="https://platform.openai.com/docs/api-reference/chat/create">创建会话补全</a>
     * @since 2.0.3
     */
    public ChatCompletionResponse createChatCompletion(ChatCompletionRequestBody options) {
        String url = openAiProfileService.getBaseUrl() + "/v1/chat/completions";
        String key = openAiProfileService.getKey();

        // 构造请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(key);
        // 构造请求
        HttpEntity<ChatCompletionRequestBody> request = new HttpEntity<>(options, headers);

        return restTemplate.postForObject(url, request, ChatCompletionResponse.class);
    }
}
