package com.inlym.lifehelper.extern.openai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
