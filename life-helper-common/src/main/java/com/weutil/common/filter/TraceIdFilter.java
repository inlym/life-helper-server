package com.weutil.common.filter;

import com.weutil.common.model.CustomHttpHeader;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 追踪 ID 过滤器
 *
 * <h2>说明
 * <p>线上生产环境使用 <a href="https://www.aliyun.com/product/apigateway?userCode=lzfqdh6g">阿里云 API 网关</a> 承载 HTTP 请求，
 * 会自动生成一个唯一请求 ID 并放置于响应头的 `X-Ca-Request-Id` 字段，项目将该字段用作全链路追踪 ID。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@WebFilter
public class TraceIdFilter extends OncePerRequestFilter {
    /**
     * 传递唯一请求 ID 的请求头字段
     *
     * <h2>说明
     * <p>该字段为阿里云 API 网关的系统参数，在编辑 API 时，配置获取 `CaRequestId` 参数传入请求头。
     */
    private static final String HEADER_NAME = CustomHttpHeader.REQUEST_ID;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 指定请求头为空则不进行任何处理
        return request.getHeader(HEADER_NAME) == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String traceId = request.getHeader(HEADER_NAME);
        request.setAttribute("TRACE_ID", traceId);

        chain.doFilter(request, response);
    }
}
