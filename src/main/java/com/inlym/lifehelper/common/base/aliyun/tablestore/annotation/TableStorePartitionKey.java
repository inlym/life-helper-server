package com.inlym.lifehelper.common.base.aliyun.tablestore.annotation;

import java.lang.annotation.*;

/**
 * 表格存储分区键字段注解
 *
 * <h2>说明
 * <p>用于注解分区键字段
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/21
 * @since 1.3.0
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableStorePartitionKey {
    String value() default "";
}
