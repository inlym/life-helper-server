package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.constant.CustomHttpHeader;
import com.inlym.lifehelper.common.constant.SpecialPath;
import com.inlym.lifehelper.common.model.CustomRequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * 请求 ID 过滤器
 *
 * <h2>说明
 *
 * <p> 线上生产环境使用 <a href="https://www.aliyun.com/product/apigateway?userCode=lzfqdh6g">阿里云 API 网关</a> 承载 HTTP 请求，
 * 会自动生成一个唯一请求 ID 并放置于请求头的 `X-Ca-Request-Id` 字段，一般将该字段用作全链路追踪 ID，开发环境也模拟了这个 ID。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-19
 * @since 1.0.0
 */
@Order(1)
@Slf4j
@WebFilter(urlPatterns = "/*")
public class RequestIdFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        // 放过“健康检查”请求，不做任何处理
        if (!SpecialPath.HEALTH_CHECK_PATH.equals(request.getRequestURI())) {
            String requestId;
            CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.attributeName);

            String requestIdString = request.getHeader(CustomHttpHeader.REQUEST_ID);

            if (requestIdString != null) {
                // 在线上生产环境会进入到这里
                requestId = requestIdString;
            } else {
                // 在开发环境会进入到这里，需要自己生成一个请求 ID，模拟线上生产环境行为，方便后续测试。
                requestId = UUID
                    .randomUUID()
                    .toString()
                    .toUpperCase();

                response.setHeader(CustomHttpHeader.REQUEST_ID, requestId);
            }

            context.setRequestId(requestId);
            MDC.put("REQUEST_ID", requestId);
        }

        chain.doFilter(request, response);
    }
}
