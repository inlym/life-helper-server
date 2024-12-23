package com.weutil.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 调试暂停过滤器
 *
 * <h2>说明
 * <p>用于调试慢请求。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/23
 * @since 3.0.0
 **/
@Component
@Slf4j
public class DebugSleepFilter extends OncePerRequestFilter {
    private static final String PARAM_NAME = "sleep";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getParameter(PARAM_NAME) == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String str = request.getParameter(PARAM_NAME);

        if (str != null) {
            long timeout = Long.parseLong(str);

            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                log.debug(e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
