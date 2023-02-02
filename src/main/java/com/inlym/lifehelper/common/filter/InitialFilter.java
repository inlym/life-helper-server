package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.constant.LogName;
import com.inlym.lifehelper.common.constant.SpecialPath;
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
 * 初始过滤器
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
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // “健康检查”请求由负载均衡发起，用于检查服务器是否正常运行，该请求直接放过，不做任何处理。
        return SpecialPath.HEALTH_CHECK_PATH.equalsIgnoreCase(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        // 第1部分：初始化自定义请求上下文对象
        CustomRequestContext context = new CustomRequestContext();
        context.setRequestTime(LocalDateTime.now());
        context.setMethod(request.getMethod());
        context.setPath(request.getRequestURI());

        if (request.getQueryString() == null) {
            context.setUrl(request.getRequestURI());
        } else {
            context.setUrl(request.getRequestURI() + "?" + request.getQueryString());
        }

        request.setAttribute(CustomRequestContext.attributeName, context);

        // 第2部分：初始化自定义的 logback 日志变量
        MDC.put(LogName.USER_ID, "0");

        chain.doFilter(request, response);
    }
}
