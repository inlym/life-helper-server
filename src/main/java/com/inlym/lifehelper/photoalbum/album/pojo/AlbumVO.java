package com.inlym.lifehelper.photoalbum.album.pojo;

import com.inlym.lifehelper.photoalbum.media.pojo.MediaVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
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
public class AlbumVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2452325710093884864L;

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

    /** 资源（照片和视频）数量 */
    private Integer total;

    /**
     * 媒体文件列表
     *
     * <h2>主要用途
     * <p>在输出相册详情时，会输出该字段。输出相册列表时，不使用该字段。
     */
    private List<MediaVO> medias;
}
