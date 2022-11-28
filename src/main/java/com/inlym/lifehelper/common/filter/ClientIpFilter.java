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
import org.springframework.core.annotation.Order;
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
@Order(2)
@Slf4j
@WebFilter(urlPatterns = "/*")
public class ClientIpFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        // 放过“健康检查”请求，不做任何处理
        if (!SpecialPath.HEALTH_CHECK_PATH.equals(request.getRequestURI())) {

            // 在正式环境，请求通过 API 网关时，会在指定字段（`X-Lifehelper-Client-Ip`）添加客户端 IP 地址，
            // 文档地址：https://help.aliyun.com/document_detail/29478.html，见【配置系统参数】部分。
            // 同时，客户端可能伪造请求，直接传递该字段，在 API 网关做的处理是：
            // 直接该请求头值尾部加上 `, ` 和客户端 IP 地址
            String ipString = request.getHeader(CustomHttpHeader.CLIENT_IP);

            if (ipString != null) {
                // 使用 `,` 分割字符串，取最后一段，就是从 API 网关处获取的客户端 IP 地址
                String[] ips = ipString.split(",");
                String clientIp = ips[ips.length - 1].trim();
                CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.attributeName);

                context.setClientIp(clientIp);
            }
        }

        chain.doFilter(request, response);
    }
}
