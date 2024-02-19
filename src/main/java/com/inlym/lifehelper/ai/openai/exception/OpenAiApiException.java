package com.inlym.lifehelper.ai.openai.exception;

/**
 * OpenAI 接口请求异常
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/19
 * @since 2.1.0
 **/
public class OpenAiApiException extends RuntimeException {
    public OpenAiApiException(String message) {
        super(message);
    }
}
