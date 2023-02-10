package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.constant.CustomHttpHeader;
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

/**
 * 客户端 IP 地址过滤器
 *
 * <h2>主要用途
 * <p>用于获取客户端的 IP 地址。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-18
 * @since 1.0.0
 **/
@Component
@Slf4j
public class ClientIpFilter extends OncePerRequestFilter {
    /**
     * 传递客户端 IP 地址的请求头字段
     *
     * <h2>说明
     * <p>该字段为阿里云 API 网关的系统参数，在编辑 API 时，配置获取 `CaClientIp` 参数传入请求头。
     */
    private static final String HEADER_NAME = CustomHttpHeader.CLIENT_IP;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 包含指定的请求头字段才进入过滤器处理
        return request.getHeader(HEADER_NAME) == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        // 在正式环境，请求通过 API 网关时，会在指定字段（`X-Lifehelper-Client-Ip`）添加客户端 IP 地址，
        // 文档地址：https://help.aliyun.com/document_detail/29478.html，见【配置系统参数】部分。
        // 同时，客户端可能伪造请求，直接传递该字段，在 API 网关做的处理是：
        // 直接该请求头值尾部加上 `, ` 和客户端 IP 地址
        String ipString = request.getHeader(HEADER_NAME);

        if (ipString != null) {
            // 使用 `,` 分割字符串，取最后一段，就是从 API 网关处获取的客户端 IP 地址
            String[] ips = ipString.split(",");
            String clientIp = ips[ips.length - 1].trim();
            CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.NAME);

            context.setClientIp(clientIp);
            MDC.put(LogName.CLIENT_IP, clientIp);
        }

        log.trace("[Header] {}={}", HEADER_NAME, ipString);

        chain.doFilter(request, response);
    }
}
