package com.inlym.lifehelper.common.base.aliyun.ots.core.annotation;

import java.lang.annotation.*;

/**
 * 表格存储时序模型的表注解
 *
 * <h2>主要用途
 * <p>用于注解时序模型使用的实体类
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/21
 * @since 1.7.0
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeseriesTable {
    /** 表名 */
    String name() default "";

    /**
     * 度量名称
     *
     * <h2>说明
     * <p>用于固定度量名称的情况，若字段中包含度量名称字段，将覆盖该值。
     */
    String measurementName() default "";

    /**
     * 数据源信息
     *
     * <h2>说明
     * <p>用于固定数据源信息的情况，若字段中包含数据源信息字段，将覆盖该值。
     */
    String dataSource() default "";
}
