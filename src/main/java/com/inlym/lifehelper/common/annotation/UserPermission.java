package com.inlym.lifehelper.common.annotation;

import com.inlym.lifehelper.common.constant.Role;
import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

/**
 * 用户登录权限鉴权注解
 *
 * <ul>
 *     <li> 该注解放在需要用户身份鉴权的控制器方法上。
 *     <li> 该注解无解析器。
 * </ul>
 *
 * @author inlym
 * @date 2022-01-21
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Secured({Role.USER})
public @interface UserPermission {
}
