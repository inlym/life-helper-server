package com.inlym.lifehelper.photoalbum.album.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
