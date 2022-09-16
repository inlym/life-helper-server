package com.inlym.lifehelper.photoalbum.album.pojo;

import com.inlym.lifehelper.photoalbum.media.pojo.MediaVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 相册对象
 *
 * <h2>主要用途
 * <p>封装用于客户端使用的相册对象
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/12
 * @since 1.4.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumVO {
    // =========================== 以下字段来源于实体对象直接复制 ============================

    /** 相册 ID */
    private String id;

    /** 相册名称 */
    private String name;

    /** 相册描述 */
    private String description;

    /** 创建时间 */
    private Long createTime;

    /** 更新时间 */
    private Long updateTime;

    /** 相册文件总大小（单位：B） */
    private Long size;

    /** 图片总数 */
    private Integer imageCount;

    /** 视频总数 */
    private Integer videoCount;

    // ========================= 以下字段来源于对实体对象字段的处理后获取 =========================

    /** 相册封面图地址 */
    private String coverImageUrl;

    /** 资源（照片和视频）数量 */
    private Integer count;

    // ============================ 以下字段仅在获取相册详情时输出 ===========================

    /** 媒体文件列表 */
    private List<MediaVO> medias;
}
