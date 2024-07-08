package com.weutil.common.annotation.resolver;

import com.weutil.common.annotation.UserId;
import com.weutil.common.exception.UnauthorizedAccessException;
import com.weutil.common.model.CustomRequestContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用户 ID 注入器注解解析器
 *
 * @author inlym
 * @date 2022-02-14
 * @see UserId
 * @since 1.0.0
 **/
public class UserIdMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter
                .getParameterType()
                .isAssignableFrom(long.class) && parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        if (webRequest.getAttribute(CustomRequestContext.NAME, RequestAttributes.SCOPE_REQUEST) instanceof CustomRequestContext context) {
            if (context.getUserId() != null && context.getUserId() > 0) {
                return context.getUserId();
            }
        }

        throw new UnauthorizedAccessException("用户未登录");
    }
}
