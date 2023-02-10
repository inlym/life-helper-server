package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.common.auth.jwt.JwtService;
import com.inlym.lifehelper.common.constant.CustomHttpHeader;
import com.inlym.lifehelper.common.constant.LogName;
import com.inlym.lifehelper.common.model.CustomRequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 鉴权过滤器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-22
 * @since 1.0.0
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
@RequiredArgsConstructor
public class JwtHandlerFilter extends OncePerRequestFilter {
    /** 传递 JWT 鉴权信息的请求头字段名 */
    private static final String HEADER_NAME = CustomHttpHeader.JWT_TOKEN;

    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 包含指定的请求头字段才进入过滤器处理
        return request.getHeader(HEADER_NAME) == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        // 从请求头获取鉴权凭证
        String token = request.getHeader(HEADER_NAME);
        log.trace("[Header] {}={}", HEADER_NAME, token);

        if (token != null) {
            try {
                SimpleAuthentication authentication = jwtService.parse(token);

                // 这一步是使用 Spring Security 框架必需的
                SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

                // 这一步是为了方便后续内部调用
                CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.NAME);
                context.setUserId(authentication.getUserId());
                MDC.put(LogName.USER_ID, String.valueOf(authentication.getUserId()));
            } catch (Exception e) {
                log.trace("[JWT 解析出错] " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
