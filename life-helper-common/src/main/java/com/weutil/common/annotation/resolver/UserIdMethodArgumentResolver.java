package com.weutil.common.annotation.resolver;

import com.weutil.common.annotation.UserId;
import com.weutil.common.exception.UnauthorizedAccessException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用户 ID 注入器注解解析器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
public class UserIdMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(long.class) && parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest webRequest, WebDataBinderFactory factory) {
        Long userId = (Long) webRequest.getAttribute("USER_ID", RequestAttributes.SCOPE_REQUEST);

        if (userId != null && userId > 0) {
            return userId;
        }

        throw new UnauthorizedAccessException();
    }
}
