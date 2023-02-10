package com.inlym.lifehelper.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 日志拦截器
 *
 * <h2>主要用途
 * <p>进行日志方面的调试。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/2/9
 * @since 1.9.1
 **/
@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
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
