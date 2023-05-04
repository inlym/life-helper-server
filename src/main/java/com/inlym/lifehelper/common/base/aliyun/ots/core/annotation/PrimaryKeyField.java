package com.inlym.lifehelper.common.base.aliyun.ots.core.annotation;

import java.lang.annotation.*;

/**
 * 表格存储主键列字段的注解
 *
 * <h2>什么时候使用？
 * <p>凡是主键列字段，均需要使用该注解标注。
 *
 * <h2>备注（2022.08.19）
 * <p>原本命名为 {@code PrimaryKeyColumn}，但是为了避免和 {@link com.alicloud.openservices.tablestore.model.PrimaryKeyColumn} 重名，因此改名。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @since 1.3.0
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrimaryKeyField {
    /** 主键排序 */
    int order();

    /** 是否进行哈希化 */
    boolean hashed() default false;

    /** 列名 */
    String name() default "";

    /** 主键列生成模式 */
    PrimaryKeyMode mode() default PrimaryKeyMode.REQUIRED;
}
