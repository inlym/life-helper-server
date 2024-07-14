package com.weutil.common.annotation.resolver;

import com.weutil.common.annotation.ClientIp;
import com.weutil.common.exception.UnpredictableException;
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
 * @date 2024/7/14
 * @since 3.0.0
 **/
public class ClientIpMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(String.class) && parameter.hasParameterAnnotation(ClientIp.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest webRequest, WebDataBinderFactory factory) {
        String clientIp = (String) webRequest.getAttribute("CLIENT_IP", RequestAttributes.SCOPE_REQUEST);
        
        if (clientIp != null) {
            return clientIp;
        }

        throw new UnpredictableException("未获取到客户端 IP 地址");
    }
}
