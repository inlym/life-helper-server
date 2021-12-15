package com.inlym.lifehelper.common;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 获取客户端 IP 地址
 */
@Order(2)
@WebFilter(filterName = "GetClientIpFilter", urlPatterns = "/*")
public class GetClientIpFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest req) {
            String ipString = req.getHeader(CustomHttpHeader.CLIENT_IP);

            if (ipString != null) {
                String[] ips = ipString.split(",");
                String clientIp = ips[ips.length - 1].trim();

                if (!clientIp.isEmpty()) {
                    request.setAttribute(RequestAttributeName.CLIENT_IP, clientIp);
                }
            }
        }

        if (request.getAttribute(RequestAttributeName.CLIENT_IP) == null) {
            String remoteIp = request.getRemoteAddr();
            request.setAttribute(RequestAttributeName.CLIENT_IP, remoteIp);
        }

        chain.doFilter(request, response);
    }
}
