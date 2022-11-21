package com.inlym.lifehelper.common.base.aliyun.ots.core.annotation;

import java.lang.annotation.*;

/**
 * 表格存储时序模型的时间线标签信息
 *
 * <h2>主要用途
 * <p>用于标记时序模型时间线的标签字段
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/21
 * @see <a href="https://help.aliyun.com/document_detail/341805.html">写入时序数据</a>
 * @since 1.7.0
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tag {
    /** 标签名 */
    String name() default "";
}
