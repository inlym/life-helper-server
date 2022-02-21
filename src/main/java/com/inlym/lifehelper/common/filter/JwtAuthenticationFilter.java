package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.common.auth.jwt.JwtService;
import com.inlym.lifehelper.common.constant.CustomHttpHeader;
import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 鉴权过滤器
 *
 * @author inlym
 * @date 2022-01-22
 */
@Order(100)
@WebFilter(urlPatterns = "/*")
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 从请求头获取鉴权凭证
        String token = request.getHeader(CustomHttpHeader.JWT_TOKEN);

        if (token != null) {
            try {
                SimpleAuthentication authentication = jwtService.parse(token);
                SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

                // 将用户 ID 赋值到请求属性上
                request.setAttribute(CustomRequestAttribute.USER_ID, authentication.getPrincipal());
            } catch (Exception e) {
                log.trace("[JWT 解析出错] " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
