package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.common.auth.jwt.JwtService;
import com.inlym.lifehelper.common.constant.CustomHttpHeader;
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
 * @since 2022-01-22 20:19
 */
@Order(100)
@WebFilter(urlPatterns = "/*")
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {this.jwtService = jwtService;}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(CustomHttpHeader.JWT_TOKEN);

        if (token != null) {
            try {
                SimpleAuthentication authentication = jwtService.parse(token);
                SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
            } catch (Exception e) {
                log.trace("[JWT 解析出错] " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
