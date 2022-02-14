package com.inlym.lifehelper.common.annotation;

import java.lang.annotation.*;

/**
 * 用户 ID 注入器
 * <p>
 * [使用说明]
 * 1. 需要结合 `@UserPermission` 注解使用。
 * 2. 在控制器方法参数注入 `@UserId int userId`。
 *
 * @author inlym
 * @date 2022-02-14 22:12
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserId {
    String value() default "";
}
