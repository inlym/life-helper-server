package com.inlym.lifehelper.photoalbum.album.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.ColumnName;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 相册实体
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @since 1.3.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    // ================================= 主键列 =================================

    /** 所属用户 ID - 分区键 */
    @JsonIgnore
    @PrimaryKeyColumn(order = 1, hashed = true)
    @ColumnName("uid")
    private Integer userId;

    /** 相册 ID */
    @PrimaryKeyColumn(order = 2)
    private String albumId;

    // ================================= 属性列 =================================

    /** 相册名称 */
    private String name;

    /** 相册描述 */
    private String description;

    /** 最近一次上传照片的时间 */
    private Long lastUploadTime;

    // ======================= 以下列有存储库控制，不要手动修改 =======================

    /** 是否被删除 */
    private Boolean deleted;

    /** 创建时间 */
    private Long createTime;

    /** 更新时间 */
    private Long updateTime;

    /** 删除时间 */
    private Long deleteTime;
}
