package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.common.auth.jwt.JwtService;
import com.inlym.lifehelper.common.constant.CustomHttpHeader;
import com.inlym.lifehelper.common.constant.SpecialPath;
import com.inlym.lifehelper.common.model.CustomRequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // “健康检查”请求由负载均衡发起，用于检查服务器是否正常运行，该请求直接放过，不做任何处理。
        return SpecialPath.HEALTH_CHECK_PATH.equalsIgnoreCase(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.attributeName);

        // 从请求头获取鉴权凭证
        String token = request.getHeader(CustomHttpHeader.JWT_TOKEN);

        if (token != null) {
            try {
                SimpleAuthentication authentication = jwtService.parse(token);

                // 这一步是使用 Spring Security 框架必需的
                SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

                context.setUserId(authentication.getUserId());
            } catch (Exception e) {
                log.trace("[JWT 解析出错] " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
