package com.inlym.lifehelper.common.base.aliyun.ots.core.annotation;

import java.lang.annotation.*;

/**
 * 表格存储主键列注解
 *
 * <h2>什么时候使用？
 * <p>凡是主键列，均需要使用该注解标注。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/21
 * @since 1.3.0
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrimaryKeyColumn {
    /** 主键排序 */
    int order();

    /** 是否进行哈希化 */
    boolean hashed() default false;

    /** 列名 */
    String name() default "";

    /** 主键列生成模式 */
    PrimaryKeyMode mode() default PrimaryKeyMode.SIMPLE_UUID;
}
