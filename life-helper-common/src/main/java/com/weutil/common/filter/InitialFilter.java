package com.weutil.common.filter;

import com.weutil.common.constant.ClientType;
import com.weutil.common.constant.CustomHttpHeader;
import com.weutil.common.constant.LogName;
import com.weutil.common.model.CustomRequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * 初始化过滤器
 *
 * <h2>主要用途
 * <p>用于初始化在过滤器链中的一些公用变量。包含：
 * <p>1. 自定义请求上下文对象 {@link com.weutil.common.model.CustomRequestContext}
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
        // 如果是 ping 请求，则不进行处理
        return request
                .getRequestURI()
                .equals("/ping");
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponse = new ContentCachingResponseWrapper(response);

        // 提取公共变量
        String method = request.getMethod();
        String path = request.getRequestURI();
        String querystring = request.getQueryString();
        String url = getWholeUrl(path, querystring);
        String requestBody = new String(cachingRequest.getContentAsByteArray(), StandardCharsets.UTF_8);

        // 初始化自定义请求上下文对象
        request.setAttribute(CustomRequestContext.NAME, CustomRequestContext
                .builder()
                .requestTime(LocalDateTime.now())
                .method(method)
                .path(path)
                .querystring(querystring)
                .clientType(ClientType.UNKNOWN)
                .build());

        // 初始化自定义的 logback 日志变量
        MDC.put(LogName.METHOD, method);
        MDC.put(LogName.URL, url);

        // 自定义请求头打日志
        log.trace("自定义请求头 -> [{}={}] [{}={}] [{}={}] [{}={}] [{}={}]", CustomHttpHeader.REQUEST_ID, request.getHeader(CustomHttpHeader.REQUEST_ID),
                CustomHttpHeader.CLIENT_IP, request.getHeader(CustomHttpHeader.CLIENT_IP), CustomHttpHeader.AUTH_TOKEN,
                request.getHeader(CustomHttpHeader.AUTH_TOKEN), CustomHttpHeader.CLIENT_TYPE, request.getHeader(CustomHttpHeader.CLIENT_TYPE),
                CustomHttpHeader.CLIENT_INFO, request.getHeader(CustomHttpHeader.CLIENT_INFO));

        // 继续执行请求链
        chain.doFilter(cachingRequest, cachingResponse);

        String responseBody = new String(cachingResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

        CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.NAME);
        context.setRequestBody(requestBody);
        context.setResponseBody(responseBody);

        // 把缓存的响应数据，响应给客户端
        cachingResponse.copyBodyToResponse();
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
