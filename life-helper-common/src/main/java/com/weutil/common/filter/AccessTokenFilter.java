package com.weutil.common.filter;

import com.weutil.common.model.AccessTokenDetail;
import com.weutil.common.model.CustomHttpHeader;
import com.weutil.common.model.SimpleAuthentication;
import com.weutil.common.service.AccessTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 类名称
 *
 * <h2>说明
 * <p>说明文本内容
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@Component
@RequiredArgsConstructor
public class AccessTokenFilter extends OncePerRequestFilter {
    /** 请求头 */
    private static final String HEADER_NAME = CustomHttpHeader.ACCESS_TOKEN;

    private final AccessTokenService accessTokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 指定请求头为空则不进行任何处理
        return request.getHeader(HEADER_NAME) == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(HEADER_NAME);
        if (token != null) {
            AccessTokenDetail detail = accessTokenService.parse(token);
            if (detail != null) {
                SimpleAuthentication authentication = new SimpleAuthentication(detail.getUserId());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("USER_ID", detail.getUserId());
            }
        }

        chain.doFilter(request, response);
    }
}
