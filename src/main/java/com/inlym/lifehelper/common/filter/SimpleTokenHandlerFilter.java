package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.common.auth.simpletoken.SimpleTokenService;
import com.inlym.lifehelper.common.auth.simpletoken.exception.InvalidSimpleTokenException;
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
 * 简易鉴权凭证处理过滤器
 *
 * <h2>主要用途
 * <p>若请求头中携带有简易登录凭证则解析并处理（处理失败也不要报错）。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/27
 * @since 1.7.0
 **/
@Slf4j
@WebFilter(urlPatterns = "/*")
@RequiredArgsConstructor
public class SimpleTokenHandlerFilter extends OncePerRequestFilter {
    /** 传递简易鉴权凭证的请求头字段名 */
    private static final String HEADER_NAME = CustomHttpHeader.AUTH_TOKEN;

    private final SimpleTokenService simpleTokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 包含指定的请求头字段才进入过滤器处理
        return request.getHeader(HEADER_NAME) == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException,
        IOException {
        // 从请求头获取鉴权凭证
        String token = request.getHeader(HEADER_NAME);

        if (token != null) {
            try {
                SimpleAuthentication authentication = simpleTokenService.parse(token);

                // 这一步是使用 Spring Security 框架必需的
                SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

                // 这一步是为了方便后续内部调用
                CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.NAME);
                context.setUserId(authentication.getUserId());
                MDC.put(LogName.USER_ID, String.valueOf(authentication.getUserId()));
            } catch (InvalidSimpleTokenException e) {
                // 用户伪造请求可能进入这一步，因此不要使用强提醒日志
                log.trace("[简易鉴权凭证鉴权失败，错误原因]：{}", e.getMessage());
            } catch (Exception e) {
                // 理论上不会进入到这一步，如果发生，则表示出现了无法预料的错误
                log.error("[意料之外的错误]：{}", e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
