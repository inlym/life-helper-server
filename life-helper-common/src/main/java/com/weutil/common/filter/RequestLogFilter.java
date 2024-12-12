package com.weutil.common.filter;

import com.weutil.common.entity.RequestLog;
import com.weutil.common.model.CustomRequestAttribute;
import com.weutil.common.service.RequestLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 请求日志过滤器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/12
 * @since 3.0.0
 **/
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@RequiredArgsConstructor
public class RequestLogFilter extends OncePerRequestFilter {
    private final RequestLogService requestLogService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 该请求路径用于健康检查，不做任何处理
        return "/ping".equalsIgnoreCase(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        RequestLog requestLog = requestLogService.transform(request);
        requestLog.setStartTime(LocalDateTime.now());

        chain.doFilter(request, response);

        requestLog.setEndTime(LocalDateTime.now());
        requestLog.setTraceId((String) request.getAttribute(CustomRequestAttribute.TRACE_ID));
        requestLog.setClientIp((String) request.getAttribute(CustomRequestAttribute.CLIENT_IP));
        requestLog.setAccessToken((String) request.getAttribute(CustomRequestAttribute.ACCESS_TOKEN));
        requestLog.setUserId((Long) request.getAttribute(CustomRequestAttribute.USER_ID));
        requestLog.setClientType(requestLogService.parseClientType((String) request.getAttribute(CustomRequestAttribute.CLIENT_TYPE)));
        requestLog.setClientId((String) request.getAttribute(CustomRequestAttribute.CLIENT_ID));
        requestLog.setClientVersion((String) request.getAttribute(CustomRequestAttribute.CLIENT_VERSION));

        requestLog.setStatus(response.getStatus());

        requestLogService.recordAsync(requestLog);
    }
}
