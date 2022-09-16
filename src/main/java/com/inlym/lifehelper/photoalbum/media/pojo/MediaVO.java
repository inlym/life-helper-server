package com.inlym.lifehelper.photoalbum.media.pojo;

import com.inlym.lifehelper.photoalbum.media.constant.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 媒体文件模型
 *
 * <h2>主要用途
 * <p>用户封装实体用于客户端展示
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/9/4
 * @since 1.4.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaVO {
    // =========================== 以下字段来源于实体对象直接复制 ============================

    /** 媒体文件 ID */
    private String id;

    /**
     * 媒体文件类型
     *
     * <h2>说明 {@link MediaType}
     * <li>1. `image` => 图片
     * <li>2. `video` => 视频
     */
    private String type;

    /** 上传时间 */
    private Long uploadTime;

    /** 视频时长，单位：秒 */
    private Long duration;

    // ========================= 以下字段来源于对实体对象字段的处理后获取 =========================

    /** 文件地址 */
    private String url;

    /** 视频缩略图地址 */
    private String thumbUrl;
}
