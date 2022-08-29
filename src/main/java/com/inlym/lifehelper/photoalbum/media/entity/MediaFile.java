package com.inlym.lifehelper.photoalbum.media.entity;

import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyField;
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
public class MediaFile {
    // ================================= 主键列 =================================

    /** 媒体文件所属的相册 ID */
    @PrimaryKeyField(order = 1)
    private String albumId;

    /** 媒体文件 ID */
    @PrimaryKeyField(order = 2)
    private String fileId;

    // ================================= 属性列 =================================

    /**
     * 媒体文件类型
     *
     * <h2>说明 {@link com.inlym.lifehelper.photoalbum.media.constant.MediaFileType}
     * <li> `1` => 图片（默认值）
     * <li> `2` => 视频
     */
    private Integer type;

    /** 图片宽度 */
    private Integer width;

    /** 图片高度 */
    private Integer height;

    /** 文件大小 */
    private Integer size;
}
