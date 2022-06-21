package com.inlym.lifehelper.common.base.aliyun.tablestore.annotation;

import java.lang.annotation.*;

/**
 * 表格存储主键注解
 *
 * <h2>说明
 * <p>用于注解主键字段，分区键直接使用 {@link TableStorePartitionKey}，无需再使用当前注解。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/21
 * @since 1.3.0
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableStorePrimaryKey {
    String value() default "";
}
