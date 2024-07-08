package com.weutil.common.filter;

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

import java.io.IOException;
import java.util.UUID;

/**
 * 追踪 ID 过滤器
 *
 * <h2>说明
 * <p>线上生产环境使用 <a href="https://www.aliyun.com/product/apigateway?userCode=lzfqdh6g">阿里云 API 网关</a> 承载 HTTP 请求，
 * 会自动生成一个唯一请求 ID 并放置于响应头的 `X-Ca-Request-Id` 字段，一般将该字段用作全链路追踪 ID，开发测试环境也模拟了这个行为。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-19
 * @since 1.0.0
 */
@Component
@Slf4j
public class TraceIdFilter extends OncePerRequestFilter {
    /**
     * 传递唯一请求 ID 的请求头字段
     *
     * <h2>说明
     * <p>该字段为阿里云 API 网关的系统参数，在编辑 API 时，配置获取 `CaRequestId` 参数传入请求头。
     */
    private static final String REQUEST_HEADER_NAME = CustomHttpHeader.REQUEST_ID;

    /**
     * 携带唯一请求 ID 的响应头字段名
     *
     * <h2>说明
     * <p>该字段不是自定义的，而是和阿里云 API 网关保持一致，在开发测试环境模拟该行为。
     */
    private static final String RESPONSE_HEADER_NAME = "x-ca-request-id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException,
            IOException {
        String traceId;
        CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.NAME);

        String requestIdString = request.getHeader(REQUEST_HEADER_NAME);
        if (requestIdString != null) {
            // 在线上生产环境会进入到这里
            traceId = requestIdString;
        } else {
            // 在开发环境会进入到这里，需要自己生成一个请求 ID，模拟线上生产环境行为，方便后续测试。
            traceId = UUID
                    .randomUUID()
                    .toString()
                    .toUpperCase();

            response.setHeader(RESPONSE_HEADER_NAME, traceId);
        }

        context.setTraceId(traceId);
        MDC.put(LogName.TRACE_ID, traceId);

        chain.doFilter(request, response);
    }
}
