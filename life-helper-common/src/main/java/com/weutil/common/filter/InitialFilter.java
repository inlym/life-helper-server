package com.weutil.common.filter;

import com.weutil.common.model.CustomCachingRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 初始化过滤器
 *
 * <h2>说明
 * <p>放在最前面，用于初始化过滤器链。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/10/15
 * @since 3.0.0
 **/
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InitialFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        CustomCachingRequestWrapper wrapper = new CustomCachingRequestWrapper(request);

        chain.doFilter(wrapper, response);
    }
}
