package com.inlym.lifehelper.photoalbum.album.entity;

/**
 * 相册实体在表格存储中注册的列名
 *
 * <h2>说明
 * <p>与 {@link Album} 中的属性名保持一致。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/16
 * @since 1.3.0
 **/
public abstract class AlbumColumns {
    // ================================= 主键列 =================================

    public static final String USER_ID = "user_id";

    public static final String ALBUM_ID = "album_id";

    // ================================= 属性列 =================================

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";
}
