package com.inlym.lifehelper.common;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 唯一请求 ID 过滤器
 *
 * <p>
 * 线上生产环境使用 <a href="https://www.aliyun.com/product/apigateway?userCode=lzfqdh6g">阿里云 API 网关</a> 承载 HTTP 请求，
 * 会自动生成一个唯一请求 ID 并放置于请求头的 `X-Ca-Request-Id` 字段，一般将该字段用作全链路追踪 ID，开发环境也模拟了这个 ID
 */
@Order(1)
@WebFilter(urlPatterns = "/*")
public class RequestIdFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 存放唯一请求 ID 的请求头字段名
        String REQUEST_ID_HEADER_NAME = CustomHttpHeader.REQUEST_ID;

        String requestId = request.getHeader(REQUEST_ID_HEADER_NAME);

        if (requestId != null) {
            request.setAttribute(RequestAttributeName.REQUEST_ID, requestId);
        } else {
            // 如果没有从请求头中拿到，则自己生成一个并赋值
            String customId = UUID
                .randomUUID()
                .toString()
                .toUpperCase();

            request.setAttribute(RequestAttributeName.REQUEST_ID, customId);
            response.setHeader(REQUEST_ID_HEADER_NAME, customId);
        }

        chain.doFilter(request, response);
    }
}
