package com.weutil.common.service;

import com.weutil.common.entity.RequestLog;
import com.weutil.common.mapper.RequestLogMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * 请求日志服务
 *
 * <h2>主要用途
 * <p>记录请求信息。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/12
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class RequestLogService {
    private final RequestLogMapper requestLogMapper;

    /**
     * 转化请求日志实体
     *
     * @param request 请求
     *
     * @date 2024/12/12
     * @since 3.0.0
     */
    public RequestLog transform(HttpServletRequest request) {
        RequestLog requestLog = RequestLog.builder()
            .method(request.getMethod())
            .path(request.getRequestURI())
            .querystring(request.getQueryString())
            .build();

        try {
            requestLog.setRequestBody(new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("读取请求数据出错");
        }

        return requestLog;
    }

    /**
     * 异步记录请求日志
     *
     * @param requestLog 请求日志实体
     *
     * @date 2024/12/12
     * @since 3.0.0
     */
    @Async
    public void recordAsync(RequestLog requestLog) {
        long duration = Duration.between(requestLog.getStartTime(), requestLog.getEndTime()).toMillis();
        requestLog.setDuration(duration);

        record(requestLog);
    }

    /**
     * 记录请求日志
     *
     * @param requestLog 请求日志实体
     *
     * @date 2024/12/12
     * @since 3.0.0
     */
    public void record(RequestLog requestLog) {
        requestLogMapper.insertSelective(requestLog);
    }
}
