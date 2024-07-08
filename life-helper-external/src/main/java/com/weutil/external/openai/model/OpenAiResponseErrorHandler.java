package com.weutil.external.openai.model;

import com.weutil.external.openai.exception.OpenAiApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * 响应错误处理器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/22
 * @since 2.2.0
 **/
@Slf4j
public class OpenAiResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response
                .getStatusCode()
                .isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response
                .getStatusCode()
                .isError()) {
            String message = "[OpenAI] 请求错误，status=" + response
                    .getStatusCode()
                    .value() + " - " + response.getBody();
            log.error(message);
            throw new OpenAiApiException(message);
        }
    }
}
