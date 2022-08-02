package com.inlym.lifehelper.common.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 请求日志拦截器
 *
 * <h2>主要用途
 * <p>在文件 {@link com.inlym.lifehelper.common.config.RestTemplateConfig} 中导入用于配置 {@link org.springframework.web.client.RestTemplate}。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/3
 * @since 1.3.0
 **/
@Slf4j
public class HttpLoggingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    @SuppressWarnings("NullableProblems")
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);

        log.info("[HTTP] {} {} {}", response.getStatusCode(), request.getMethod(), request.getURI());

        log.trace("[HTTP] Request Body: {}", new String(body, StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
        String line = bufferedReader.readLine();
        while (line != null) {
            sb.append(line);
            sb.append('\n');
            line = bufferedReader.readLine();
        }

        log.trace("[HTTP] Response Body: {}", sb);

        return response;
    }
}
