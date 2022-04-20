package com.inlym.lifehelper.common.annotation;

import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
import com.inlym.lifehelper.common.exception.UnauthorizedAccessException;
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
            .isAssignableFrom(int.class) && parameter.hasParameterAnnotation(UserId.class);
    }

    /**
     * 解析参数处理
     *
     * <h2>处理逻辑
     *
     * <p>在过滤器链中，如果鉴权通过，则会在 {@link org.springframework.web.context.request.RequestAttributes RequestAttributes}
     * 赋值，其中会将解析后的用户 ID 赋值在 {@code CustomRequestAttribute.USER_ID} 参数上，将其获取后进行返回即可。
     */
    @Override
    public Object resolveArgument(@SuppressWarnings("NullableProblems") MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws UnauthorizedAccessException {
        Integer userId = (Integer) webRequest.getAttribute(CustomRequestAttribute.USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (userId != null && userId > 0) {
            return userId;
        }

        throw new UnauthorizedAccessException("用户未登录");
    }
}
