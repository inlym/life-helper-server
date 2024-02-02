package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.common.auth.supertoken.SuperTokenService;
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
 * 超级登录令牌处理过滤器
 *
 * <h2>主要用途
 * <p>若请求头中携带有超级登录令牌则解析并处理（处理失败也不要报错）。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/3
 * @since 1.7.2
 **/
@WebFilter(urlPatterns = "/*")
@Slf4j
@RequiredArgsConstructor
public class SuperTokenHandlerFilter extends OncePerRequestFilter {
    /** 传递超级登录令牌的请求头字段名 */
    private static final String HEADER_NAME = CustomHttpHeader.SUPER_TOKEN;

    /** 令牌字符串的组成部分 */
    private static final int TOKEN_STRING_PARTS = 2;

    private final SuperTokenService superTokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 包含指定的请求头字段才进入过滤器处理
        return request.getHeader(HEADER_NAME) == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        // 从请求中获取待校验的令牌字符串（包含多个组成部分）
        String tokenString = request.getHeader(HEADER_NAME);
        log.trace("[Header] {}={}", HEADER_NAME, tokenString);

        if (tokenString != null) {
            // 该字符串中包含了实际的令牌和用户 ID，使用英文逗号（`,`）分隔
            String[] strings = tokenString.split(",");
            if (strings.length == TOKEN_STRING_PARTS) {
                String token = strings[0];
                long userId = Integer.parseInt(strings[1]);

                // 令牌有效则继续
                if (superTokenService.check(token)) {
                    SimpleAuthentication authentication = new SimpleAuthentication(userId);

                    // 这一步是使用 Spring Security 框架必需的
                    SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

                    // 这一步是为了方便后续内部调用
                    CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.NAME);
                    context.setUserId(authentication.getUserId());
                    MDC.put(LogName.USER_ID, String.valueOf(authentication.getUserId()));
                } else {
                    log.trace("超级登录令牌（{}）无效！", token);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
