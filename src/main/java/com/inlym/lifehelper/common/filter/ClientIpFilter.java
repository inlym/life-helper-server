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
 * <h2>背景介绍
 * <p>由于服务器架构问题（客户端 -> API 网关 -> 负载均衡 -> 应用服务器），服务器无法直接获取客户端的 IP 地址，只能依赖从网关处获取客户端 IP 地址并将其传递给应用服务器。
 * 请求传递链中的 API 网关和负载均衡直接使用了阿里云的服务，提供了2种通过请求头传递客户端 IP 地址的方式：
 *
 * <li>API 网关处可自定义传递客户端 IP 地址的请求头，但是如果客户端伪造请求传递了该请求头，API 网关处将不再传递该值，导致获取不到真实的客户端 IP 地址。
 * <li>负载均衡通过 `X-Forwarded-For` 请求头字段传递客户端 IP 地址，如果客户端伪造请求传递了该请求头，则会将真实的数据加在已有值的后面并用逗号分隔。
 *
 * <p>综合考虑以上优缺点，决定使用从负载均衡传递的 `X-Forwarded-For` 请求头字段获取真实的客户端 IP 地址。
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
     * <p>该字段为阿里云负载均衡传入。
     *
     * <p>文档地址：<a href="https://help.aliyun.com/document_detail/446450.html">HTTP头字段</a>
     */
    private static final String HEADER_NAME = CustomHttpHeader.CLIENT_IP;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 包含指定的请求头字段才进入过滤器处理
        return request.getHeader(HEADER_NAME) == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException,
        IOException {
        // 一般情况下，该请求头的格式为 `<真实的客户端 IP>, <API 网关的 VPC IP>`，如果攻击者伪造请求并传递了该字段，
        // 格式将变成 `<攻击者定义的值>, <真实的客户端 IP>, <API 网关的 VPC IP>`，考虑到以上2种情况，获取其中的
        // “真实的客户端 IP”的方式应为：以逗号分隔，取倒数第2段的值。
        String ipString = request.getHeader(HEADER_NAME);

        if (ipString != null) {
            // 使用 `,` 分割字符串，取最后第2段
            String[] ips = ipString.split(",");
            String clientIp = ips[ips.length - 2].trim();
            CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.NAME);

            context.setClientIp(clientIp);
            MDC.put(LogName.CLIENT_IP, clientIp);
        }

        chain.doFilter(request, response);
    }
}
