package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.constant.CustomHttpHeader;
import com.inlym.lifehelper.common.constant.SpecialPath;
import com.inlym.lifehelper.common.model.CustomRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
        if (!SpecialPath.HEALTH_CHECK_PATH.equals(request.getRequestURI())) {
            String requestId;
            CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.attributeName);

            String requestIdString = request.getHeader(CustomHttpHeader.REQUEST_ID);

            if (requestIdString != null) {
                requestId = requestIdString;
            } else {
                // 如果没有从请求头中拿到，则自己生成一个
                requestId = UUID
                    .randomUUID()
                    .toString()
                    .toUpperCase();

                response.setHeader(CustomHttpHeader.REQUEST_ID, requestId);
            }

            context.setRequestId(requestId);
        }

        chain.doFilter(request, response);
    }
}
