package com.inlym.lifehelper.common.base.aliyun.tablestore.widecolumn;

/**
 * 宽表模型抽象存储库
 *
 * <h2>说明
 * <p>继承这个父类，然后直接调用增删改查方法。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/27
 * @since 1.3.0
 **/
public class WideColumnRepository<T> {
    private final T entity;

    public WideColumnRepository(T entity) {
        this.entity = entity;
    }
}
