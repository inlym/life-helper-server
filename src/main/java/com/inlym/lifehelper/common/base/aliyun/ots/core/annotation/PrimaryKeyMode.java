package com.inlym.lifehelper.common.base.aliyun.ots.core.annotation;

/**
 * 表格存储主键列生成模式
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/18
 * @since 1.4.0
 **/
public enum PrimaryKeyMode {
    /** 必填，必须手动向实体赋值 */
    REQUIRED,

    /** 在创建时，若为空则自动填充“去掉短横线的 UUID” */
    SIMPLE_UUID;

    private PrimaryKeyMode() {}
}
