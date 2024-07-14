package com.weutil.common.annotation;

import java.lang.annotation.*;

/**
 * 用户 ID 注入器
 *
 * <h2>主要用途
 * <p>在控制器方法的参数中注入用户 ID，以便在方法内部快捷获取和使用。
 *
 * <h2>注意事项
 * <li> 需要结合 {@link UserPermission @UserPermission} 注解使用（登录鉴权通过才会有用户 ID）。
 * <li> 在控制器方法参数注入 {@code @UserId long userId}。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserId {
    String value() default "";
}
