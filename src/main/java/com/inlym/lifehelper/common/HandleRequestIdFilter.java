package com.inlym.lifehelper.common;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
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
public class HandleRequestIdFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest req) {
            String requestId = req.getHeader(CustomHttpHeader.REQUEST_ID);
            if (requestId != null) {
                request.setAttribute(RequestAttributeName.REQUEST_ID, requestId);
            } else {
                String customId = UUID
                    .randomUUID()
                    .toString()
                    .toUpperCase();

                request.setAttribute(RequestAttributeName.REQUEST_ID, customId);

                if (response instanceof HttpServletResponse res) {
                    res.setHeader(CustomHttpHeader.REQUEST_ID, customId);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
