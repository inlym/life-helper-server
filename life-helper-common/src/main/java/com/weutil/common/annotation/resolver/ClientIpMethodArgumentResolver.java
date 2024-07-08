package com.weutil.common.annotation.resolver;

import com.weutil.common.annotation.ClientIp;
import com.weutil.common.exception.UnpredictableException;
import com.weutil.common.model.CustomRequestContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 客户端 IP 地址注入器注解解析器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/3/27
 * @see ClientIp
 * @since 1.0.0
 **/
public class ClientIpMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter
                .getParameterType()
                .isAssignableFrom(String.class) && parameter.hasParameterAnnotation(ClientIp.class);
    }

    /**
     * 解析参数处理
     * <p>
     * <h2>处理逻辑
     * <p>在过滤器中已获取客户端 IP 地址，并附在了自定义请求域属性上，直接获取即可。
     */
    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        if (webRequest.getAttribute(CustomRequestContext.NAME, RequestAttributes.SCOPE_REQUEST) instanceof CustomRequestContext context) {
            if (context.getClientIp() != null) {
                return context.getClientIp();
            }
        }

        throw new UnpredictableException("未获取到客户端 IP 地址");
    }
}
