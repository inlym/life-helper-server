package com.inlym.lifehelper.common.annotation;

import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
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
     *
     * <h2>处理逻辑
     * <p>在过滤器中已获取客户端 IP 地址，并附在了请求域属性上，直接获取即可。
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String ip = (String) webRequest.getAttribute(CustomRequestAttribute.CLIENT_IP, RequestAttributes.SCOPE_REQUEST);
        if (ip != null) {
            return ip;
        }
        throw new Exception("未获取到客户端 IP 地址");
    }
}
