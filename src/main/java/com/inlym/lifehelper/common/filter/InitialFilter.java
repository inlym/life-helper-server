package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.constant.LogName;
import com.inlym.lifehelper.common.model.CustomRequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 初始化过滤器
 *
 * <h2>主要用途
 * <p>用于初始化在过滤器链中的一些公用变量。包含：
 * <p>1. 自定义请求上下文对象 {@link com.inlym.lifehelper.common.model.CustomRequestContext}
 * <p>2. MDC 在打印日志中用到的变量。
 *
 * <h2>注意事项
 * <p>这个过滤器必须位于所有“自定义过滤器”的最前面。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/2/2
 * @since 1.9.0
 **/
@Component
@Slf4j
public class InitialFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        // 第1步：提取公共变量
        String method = request.getMethod();
        String url = getWholeUrl(request.getRequestURI(), request.getQueryString());

        // 第2步：初始化自定义请求上下文对象
        request.setAttribute(CustomRequestContext.NAME, CustomRequestContext
            .builder()
            .requestTime(LocalDateTime.now())
            .method(method)
            .url(url)
            .build());

        // 第3步：初始化自定义的 logback 日志变量
        MDC.put(LogName.METHOD, method);
        MDC.put(LogName.URL, url);

        chain.doFilter(request, response);
    }

    /**
     * 获取完整的路径地址
     *
     * <h2>格式示例
     * <p>1. `/path/to?name=mark&age=19`
     * <p>2. `/path/to`
     *
     * @param path 路径
     * @param qs   查询字符串
     *
     * @since 1.9.0
     */
    private String getWholeUrl(String path, String qs) {
        if (qs == null) {
            return path;
        }
        return path + "?" + qs;
    }
}
