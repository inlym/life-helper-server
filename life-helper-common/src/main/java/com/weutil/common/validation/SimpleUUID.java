package com.weutil.common.validation;

import com.weutil.common.validation.validator.SimpleUUIDValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 简易 UUID 字符串校验注解
 *
 * <h2>主要用途
 * <p>校验字符串是否是无短横线的 UUID 字符串。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/7
 * @since 1.8.0
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {SimpleUUIDValidator.class})
public @interface SimpleUUID {
    String message() default "ID 格式不正确！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
