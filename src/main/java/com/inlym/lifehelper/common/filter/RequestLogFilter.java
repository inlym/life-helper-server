package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
import com.inlym.lifehelper.common.constant.SpecialPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求日志过滤器
 *
 * <h2>主要用途
 * <p>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/9/18
 * @since 1.x.x
 **/
@Order(1000)
@Slf4j
@WebFilter(urlPatterns = "/*")
public class RequestLogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!SpecialPath.HEALTH_CHECK_PATH.equals(request.getServletPath())) {
            String method = request.getMethod();
            String path = request.getQueryString() == null ? request.getServletPath() : request.getServletPath() + "?" + request.getQueryString();
            String ip = (String) request.getAttribute(CustomRequestAttribute.CLIENT_IP);
            String requestId = (String) request.getAttribute(CustomRequestAttribute.REQUEST_ID);

            int userId = 0;
            if (request.getUserPrincipal() instanceof SimpleAuthentication) {
                userId = (int) ((SimpleAuthentication) request.getUserPrincipal()).getPrincipal();
            }

            log.trace("{} {} {} {} {}", requestId, ip, userId, method, path);
        }

        filterChain.doFilter(request, response);
    }
}
