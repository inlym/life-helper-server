package com.inlym.lifehelper.common.base.aliyun.ots.annotation;

import java.lang.annotation.*;

/**
 * 表格存储的数据表注解
 *
 * <h2>说明
 * <p>用于注解表格存储的数据表
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/21
 * @since 1.3.0
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    /** 表名 */
    String value();
}
