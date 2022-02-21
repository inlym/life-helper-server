package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.constant.CustomHttpHeader;
import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 客户端 IP 地址过滤器
 *
 * @author inlym
 * @date 2022-01-18
 **/
@Order(2)
@WebFilter(urlPatterns = "/*")
public class ClientIpFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 在正式环境，请求通过 API 网关时，会在指定字段（`X-Client-Ip`）添加客户端 IP 地址，
        // 同时，客户端可能伪造请求，直接传递该字段，在 API 网关做的处理是：
        // 直接该请求头值尾部加上 `, ` 和客户端 IP 地址
        String ipString = request.getHeader(CustomHttpHeader.CLIENT_IP);

        if (ipString != null) {
            // 使用 `,` 分割字符串，取最后一段，就是从 API 网关处获取的客户端 IP 地址
            String[] ips = ipString.split(",");

            String clientIp = ips[ips.length - 1].trim();
            request.setAttribute(CustomRequestAttribute.CLIENT_IP, clientIp);
        } else {
            // 该情况用于本地开发环境，兼容无 API 网关情况
            String remoteIp = request.getRemoteAddr();
            request.setAttribute(CustomRequestAttribute.CLIENT_IP, remoteIp);
        }

        chain.doFilter(request, response);
    }
}
