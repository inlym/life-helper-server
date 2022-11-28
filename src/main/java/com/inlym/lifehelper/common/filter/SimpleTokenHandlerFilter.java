package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.common.auth.st.SimpleTokenService;
import com.inlym.lifehelper.common.auth.st.exception.InvalidSimpleTokenException;
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
import org.springframework.core.annotation.Order;
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
@Order(102)
@WebFilter(urlPatterns = "/*")
@Slf4j
@RequiredArgsConstructor
public class SimpleTokenHandlerFilter extends OncePerRequestFilter {
    private final SimpleTokenService simpleTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 放过“健康检查”请求，不做任何处理
        if (!SpecialPath.HEALTH_CHECK_PATH.equals(request.getRequestURI())) {
            // 从请求头获取鉴权凭证
            String token = request.getHeader(CustomHttpHeader.SIMPLE_TOKEN);

            if (token != null) {
                try {
                    SimpleAuthentication authentication = simpleTokenService.parse(token);

                    // 这一步是使用 Spring Security 框架必需的
                    SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

                    // 这一步是为了方便后续内部调用
                    CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.attributeName);
                    context.setUserId(authentication.getUserId());
                } catch (InvalidSimpleTokenException e) {
                    // 用户伪造请求可能进入这一步，因此不要使用强提醒日志
                    log.trace(e.getMessage());
                } catch (Exception e) {
                    // 理论上不会进入到这一步，如果发生，则表示出现了无法预料的错误
                    log.error("[意料之外的错误]：{}", e.getMessage());
                }
            }
        }

        chain.doFilter(request, response);
    }
}
