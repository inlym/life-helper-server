package com.inlym.lifehelper.common.validation;

import com.inlym.lifehelper.common.validation.validator.LocationStringValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 经纬度字符串检验注解
 *
 * <h2>主要用途
 * <p>校验请求参数是否为指定格式
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-22
 * @see LocationStringValidator
 * @since 1.0.0
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {LocationStringValidator.class})
public @interface LocationString {
    String message() default "经纬度字符串的格式应为 `lng,lat`，例如 `120.12,30.45`";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
