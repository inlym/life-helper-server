package com.inlym.lifehelper.photoalbum.album.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inlym.lifehelper.common.base.aliyun.tablestore.annotation.TableStoreColumn;
import com.inlym.lifehelper.common.base.aliyun.tablestore.annotation.TableStorePartitionKey;
import com.inlym.lifehelper.common.base.aliyun.tablestore.annotation.TableStorePrimaryKey;
import com.inlym.lifehelper.common.base.aliyun.tablestore.annotation.TableStoreTable;
import lombok.Data;

/**
 * 相册实体
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @since 1.3.0
 **/
@Data
@TableStoreTable("album")
public class Album {
    // ================================= 主键列 =================================

    /** 所属用户 ID - 分区键 */
    @JsonIgnore
    @TableStorePartitionKey
    @TableStoreColumn("uid")
    private String hashedUserId;

    /** 相册 ID */
    @TableStorePrimaryKey
    @TableStoreColumn("album_id")
    private String albumId;

    // ================================= 属性列 =================================

    /** 相册名称 */
    @TableStoreColumn("name")
    private String name;

    /** 相册描述 */
    @TableStoreColumn("description")
    private String description;

    /** 是否被删除 */
    @TableStoreColumn("deleted")
    private Boolean deleted;

    /** 创建时间 */
    @TableStoreColumn("create_time")
    private Long createTime;

    /** 更新时间 */
    @TableStoreColumn("update_time")
    private Long updateTime;

    /** 删除时间 */
    @TableStoreColumn("delete_time")
    private Long deleteTime;
}
