package com.inlym.lifehelper.common.annotation;

import com.inlym.lifehelper.common.constant.Role;
import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

/**
 * 用户登录权限鉴权注解
 * <p>
 * [使用说明]
 * 放在需要用户身份鉴权的控制器方法上。
 *
 * @author inlym
 * @since 2022-01-21 00:31
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Secured({Role.USER})
public @interface UserPermission {
}
