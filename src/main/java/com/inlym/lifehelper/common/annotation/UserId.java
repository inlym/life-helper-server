package com.inlym.lifehelper.common.annotation;

import java.lang.annotation.*;

/**
 * 用户 ID 注入器
 *
 * <ul>
 *     <li> 需要结合 {@code @UserPermission} 注解使用（登录鉴权通过才会有用户 ID）。
 *     <li> 在控制器方法参数注入 {@code @UserId int userId} 。
 * </ul>
 *
 * @author inlym
 * @date 2022-02-14
 * @see UserIdMethodArgumentResolver
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserId {
    String value() default "";
}
