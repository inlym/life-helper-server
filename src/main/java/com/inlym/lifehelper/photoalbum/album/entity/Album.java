package com.inlym.lifehelper.photoalbum.album.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inlym.lifehelper.common.base.aliyun.tablestore.annotation.AttributeColumn;
import com.inlym.lifehelper.common.base.aliyun.tablestore.annotation.PrimaryKey;
import com.inlym.lifehelper.common.base.aliyun.tablestore.annotation.Table;
import lombok.Data;

/**
 * 相册实体
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @since 1.3.0
 **/
@Data
@Table("album")
public class Album {
    // ================================= 主键列 =================================

    /** 所属用户 ID - 分区键 */
    @JsonIgnore
    @PrimaryKey(value = "uid", order = 1)
    private String hashedUserId;

    /** 相册 ID */
    @PrimaryKey(order = 2)
    private String albumId;

    // ================================= 属性列 =================================

    /** 相册名称 */
    @AttributeColumn("name")
    private String name;

    /** 相册描述 */
    @AttributeColumn("description")
    private String description;

    /** 是否被删除 */
    @AttributeColumn("deleted")
    private Boolean deleted;

    /** 创建时间 */
    @AttributeColumn("create_time")
    private Long createTime;

    /** 更新时间 */
    @AttributeColumn("update_time")
    private Long updateTime;

    /** 删除时间 */
    @AttributeColumn("delete_time")
    private Long deleteTime;
}
