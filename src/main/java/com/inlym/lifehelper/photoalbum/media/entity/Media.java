package com.inlym.lifehelper.photoalbum.media.entity;

import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyField;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyMode;
import com.inlym.lifehelper.photoalbum.media.constant.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 媒体文件（包含照片和视频）实体
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/28
 * @since 1.4.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    // ================================= 主键列 =================================

    /** 媒体文件所属的相册 ID */
    @PrimaryKeyField(order = 1)
    private String albumId;

    /** 媒体文件 ID */
    @PrimaryKeyField(order = 2, mode = PrimaryKeyMode.SIMPLE_UUID)
    private String mediaId;

    // ================================= 属性列 =================================

    /**
     * 媒体文件类型
     *
     * <h2>说明 {@link MediaType}
     * <li> `image` => 图片
     * <li> `video` => 视频
     */
    private String type;

    /** 文件路径 */
    private String path;

    /** 宽度 */
    private Integer width;

    /** 高度 */
    private Integer height;

    /** 文件大小 */
    private Long size;

    /**
     * 上传时间
     *
     * <h2>说明
     * <li>上传时间应该在这条数据库记录创建之前。
     */
    private Long uploadTime;

    /**
     * 创建时间
     *
     * <h2>说明
     * <li>特指操作新增这条数据库记录的时间。
     */
    private Long createTime;

    // ------------------------------------------------------------------------
    // ----------------------------  【视频】专属字段  ---------------------------
    // ------------------------------------------------------------------------

    /** 视频缩略图路径 */
    private String thumbPath;

    /** 视频时长 */
    private Long duration;
}
