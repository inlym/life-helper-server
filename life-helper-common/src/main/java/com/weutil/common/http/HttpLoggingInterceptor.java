package com.weutil.common.http;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 请求日志拦截器
 * <p>
 * <h2>主要用途
 * <p>在文件 {@link com.weutil.common.config.RestTemplateConfig} 中导入用于配置 {@link org.springframework.web.client.RestTemplate}。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/3
 * @since 1.3.0
 **/
@Slf4j
public class HttpLoggingInterceptor implements ClientHttpRequestInterceptor {
    @NotNull
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);

        log.info("[HTTP] {} {} {}", response.getStatusCode(), request.getMethod(), request.getURI());

        if (HttpMethod.POST == request.getMethod()) {
            log.trace("[HTTP] Request Body: {}", new String(body, StandardCharsets.UTF_8));
        }

        return response;
    }
}
