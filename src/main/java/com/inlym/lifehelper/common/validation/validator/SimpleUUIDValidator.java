package com.inlym.lifehelper.common.validation.validator;

import com.inlym.lifehelper.common.validation.SimpleUUID;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 简易 UUID 字符串校验器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/7
 * @since 1.8.0
 **/
public class SimpleUUIDValidator implements ConstraintValidator<SimpleUUID, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 允许不传该字段，如果要传则必须符合格式要求
        if (s == null) {
            return true;
        }

        // 注：字母仅支持小写
        String regex = "[0-9a-f]{32}";

        return s.matches(regex);
    }
}
