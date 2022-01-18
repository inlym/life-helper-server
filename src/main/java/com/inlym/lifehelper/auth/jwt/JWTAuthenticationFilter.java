package com.inlym.lifehelper.auth.jwt;

import com.inlym.lifehelper.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.common.constant.CustomHttpHeader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;

    public JWTAuthenticationFilter(JWTService jwtService) {this.jwtService = jwtService;}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String HEADER_NAME = CustomHttpHeader.JWT_TOKEN;
        String token = request.getHeader(HEADER_NAME);

        if (token != null) {
            SimpleAuthentication authentication = jwtService.parse(token);
            SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
