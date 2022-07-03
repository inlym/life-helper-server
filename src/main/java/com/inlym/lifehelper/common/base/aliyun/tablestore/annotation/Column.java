package com.inlym.lifehelper.common.base.aliyun.tablestore.annotation;

import java.lang.annotation.*;

/**
 * 表格存储的列
 *
 * <h2>主要用途
 * <p>标记属性在数据表中的字段别名
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/7/2
 * @since 1.3.0
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    /** 列名 */
    String value() default "";
}
