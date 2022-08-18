package com.inlym.lifehelper.common.base.aliyun.ots.core.annotation;

import java.lang.annotation.*;

/**
 * 表格存储的数据表注解
 *
 * <h2>说明
 * <p>用于注解表格存储的数据表
 *
 * <h2>什么时候使用？
 * <p>在表名不是类名的驼峰命名时，使用该注解自定义表名。
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
    String name();
}
