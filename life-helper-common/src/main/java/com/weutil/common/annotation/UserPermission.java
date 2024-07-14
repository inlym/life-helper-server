package com.weutil.common.annotation;

import com.weutil.common.model.Role;
import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

/**
 * 用户登录权限鉴权注解
 *
 * <h2>主要用途
 * <p>拦截需要用户登录的控制器方法，未携带有效登录信息则直接报错。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Secured({Role.USER})
public @interface UserPermission {}
