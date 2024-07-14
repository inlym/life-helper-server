package com.weutil.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 日志拦截器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/15
 * @since 3.0.0
 **/
@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    public final String TRACE_ID = "TRACE_ID";
    public final String CLIENT_IP = "CLIENT_IP";
    public final String USER_ID = "USER_ID";

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        String traceId = (String) request.getAttribute(TRACE_ID);
        String clientIp = (String) request.getAttribute(CLIENT_IP);
        Long userId = (Long) request.getAttribute(USER_ID);

        MDC.put(TRACE_ID, traceId);
        MDC.put(CLIENT_IP, clientIp);
        MDC.put(USER_ID, String.valueOf(userId));

        Map<String, String> map = MDC.getCopyOfContextMap();
        if (map != null) {
            log.debug("MDC={}", map);
        }

        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
        MDC.clear();
    }
}
