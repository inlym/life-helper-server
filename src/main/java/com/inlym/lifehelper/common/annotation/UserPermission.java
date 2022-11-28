package com.inlym.lifehelper.common.annotation;

import com.inlym.lifehelper.common.constant.Role;
import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

/**
 * 用户登录权限鉴权注解
 *
 * <h2>主要用途
 *
 * <p>拦截需要用户登录的控制器方法，未携带有效登录信息则直接报错。
 *
 * <h2>使用示例
 * <p>
 * <pre class="code">
 * &#064;GetMapping("/userid")
 * &#064;UserPermission
 * public int getUserId(@UserId int userId) {
 *     return userId;
 * }
 * </pre>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-21
 * @since 1.0.0
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Secured({Role.USER})
public @interface UserPermission {}
