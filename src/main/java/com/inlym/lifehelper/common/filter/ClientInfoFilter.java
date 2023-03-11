package com.inlym.lifehelper.common.filter;

import com.inlym.lifehelper.common.constant.ClientType;
import com.inlym.lifehelper.common.constant.CustomHttpHeader;
import com.inlym.lifehelper.common.model.CustomRequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 客户端信息处理过滤器
 *
 * <h2>主要用途
 * <p>目前用于解析客户端的类型和版本号。
 *
 * <h2>字段值格式示例
 * <p>`type=mini_program;version=1.0.0`
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/11
 * @since 1.9.5
 **/
@WebFilter(urlPatterns = "/*")
@Slf4j
public class ClientInfoFilter extends OncePerRequestFilter {
    /** 请求头字段名 */
    private static final String HEADER_NAME = CustomHttpHeader.CLIENT_INFO;

    /** 第1层分隔符 */
    private static final String SEPARATOR_1 = ";";

    /** 第2层分隔符 */
    private static final String SEPARATOR_2 = "=";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 包含指定的请求头字段才进入过滤器处理
        return request.getHeader(HEADER_NAME) == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.NAME);

        String str = request.getHeader(HEADER_NAME);
        log.trace("[Header] {}={}", HEADER_NAME, str);

        // 备注（2023.03.11）：
        // 下方有很多层的 `if` 嵌套，虽然代码更长了，但是逻辑上更好理解
        for (String field : str.split(SEPARATOR_1)) {
            if (field != null) {
                // field 是 `key=value` 的键值对字符串
                String[] pairs = field.split(SEPARATOR_2);
                if (pairs.length == 2) {
                    String key = pairs[0];
                    String value = pairs[1];
                    if (key != null && value != null) {
                        if ("type".equalsIgnoreCase(key)) {
                            if (ClientType.MINI_PROGRAM
                                .name()
                                .equalsIgnoreCase(value)) {
                                context.setClientType(ClientType.MINI_PROGRAM);
                            } else if (ClientType.WEB
                                .name()
                                .equalsIgnoreCase(value)) {
                                context.setClientType(ClientType.WEB);
                            }
                        } else if ("version".equalsIgnoreCase(key)) {
                            context.setClientVersion(value);
                        }
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }
}
