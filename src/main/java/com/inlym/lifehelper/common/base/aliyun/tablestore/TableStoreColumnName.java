package com.inlym.lifehelper.common.base.aliyun.tablestore;

/**
 * 表格存储特殊列名集合
 *
 * <h2>主要用途
 * <p>将所有表格都可能用到的字段集合在这里。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/10
 * @since 1.4.0
 **/
public abstract class TableStoreColumnName {
    /** 哈希化的用户 ID */
    public static final String HASHED_USER_ID = "uid";

    /** 是否删除 */
    public static final String DELETED = "deleted";

    /** 创建时间 */
    public static final String CREATE_TIME = "create_time";

    /** 更新时间 */
    public static final String UPDATE_TIME = "update_time";

    /** 删除时间 */
    public static final String DELETE_TIME = "delete_time";
}
