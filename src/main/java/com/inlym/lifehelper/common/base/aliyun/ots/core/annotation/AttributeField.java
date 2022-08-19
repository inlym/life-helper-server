package com.inlym.lifehelper.common.base.aliyun.ots.core.annotation;

import java.lang.annotation.*;

/**
 * 表格存储属性列注解
 *
 * <h2>说明
 * <p>本期内将所有列均标注，后期优化成不标注优先使用下划线变量名。
 *
 * <h2>备注（2022.08.19）
 * <p>原本命名为 {@code AttributeColumn}，但是为了和 {@link PrimaryKeyField} 命名结构相同，因此改名。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/21
 * @since 1.3.0
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AttributeField {
    /** 列名 */
    String name() default "";
}
