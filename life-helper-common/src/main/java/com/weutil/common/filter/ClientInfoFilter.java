package com.weutil.common.filter;

import com.weutil.common.model.CustomHttpHeader;
import com.weutil.common.model.CustomRequestAttribute;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 客户端信息过滤器
 *
 * <h2>客户端信息
 * <p>1. [type] -> [web, miniprogram]
 * <p>2. [id] -> web 为域名地址，miniprogram 为小程序的 appId
 * <p>3. [version] -> 示例值: 3.0.0
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/12
 * @since 3.0.0
 **/
@Component
public class ClientInfoFilter extends OncePerRequestFilter {
    /** 传递客户端信息的请求头字段 */
    private static final String HEADER_NAME = CustomHttpHeader.CLIENT_INFO;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 指定请求头为空则不进行任何处理
        return request.getHeader(HEADER_NAME) == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 文本格式为: `key1=value1; key2=value2; key3=value3`
        String str = request.getHeader(HEADER_NAME);

        for (String item : str.split("; ")) {
            String[] keypair = item.split("=");

            String name = keypair[0];
            String value = keypair[1];

            if ("type".equalsIgnoreCase(name)) {
                request.setAttribute(CustomRequestAttribute.CLIENT_TYPE, value);
            } else if ("id".equalsIgnoreCase(name)) {
                request.setAttribute(CustomRequestAttribute.CLIENT_ID, value);
            } else if ("version".equalsIgnoreCase(name)) {
                request.setAttribute(CustomRequestAttribute.CLIENT_VERSION, value);
            }
        }

        chain.doFilter(request, response);
    }
}
