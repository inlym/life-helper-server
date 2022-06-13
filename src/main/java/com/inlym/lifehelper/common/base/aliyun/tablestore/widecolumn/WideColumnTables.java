package com.inlym.lifehelper.common.base.aliyun.tablestore.widecolumn;

/**
 * 宽表模型实例数据表
 *
 * <h2>说明
 * <p>将所有宽表模型实例数据表在此注册。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @since 1.3.0
 **/
public abstract class WideColumnTables {
    /** 相册表 */
    public static final String ALBUM = "album";

    /** 相册表数据字段 */
    public static class Album {
        public static final String USER_ID = "user_id";

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String DESCRIPTION = "description";
    }
}
