package com.inlym.lifehelper.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 经纬度字符串检验注解
 *
 * @author inlym
 * @since 2022-01-22 01:09
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {LocationStringValidator.class})
public @interface LocationString {
    String message() default "经纬度字符串的格式应为 `lng,lat`，例如 `120.12,30.45`";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
