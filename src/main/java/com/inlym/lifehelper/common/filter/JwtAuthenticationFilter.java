package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.auth.jwt.JwtService;
import com.inlym.lifehelper.common.constant.CustomHttpHeader;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {this.jwtService = jwtService;}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println(SecurityContextHolder
            .getContext()
            .getAuthentication());

        String token = request.getHeader(CustomHttpHeader.JWT_TOKEN);

        if (token != null) {
            SimpleAuthentication authentication = jwtService.parse(token);
            SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
