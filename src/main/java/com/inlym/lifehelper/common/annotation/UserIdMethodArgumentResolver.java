package com.inlym.lifehelper.common.annotation;

import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
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
 * @date 2022-02-14 22:17
 **/
public class UserIdMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter
            .getParameterType()
            .isAssignableFrom(int.class) && parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Integer userId = (Integer) webRequest.getAttribute(CustomRequestAttribute.USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (userId != null && userId > 0) {
            return userId;
        }
        throw new Exception("鉴权失败，未获取用户 ID");
    }
}
