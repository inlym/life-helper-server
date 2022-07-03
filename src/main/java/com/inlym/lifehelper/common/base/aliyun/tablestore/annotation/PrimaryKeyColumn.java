package com.inlym.lifehelper.common.base.aliyun.tablestore.annotation;

import java.lang.annotation.*;

/**
 * 表格存储主键注解
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
}
