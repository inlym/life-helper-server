package com.inlym.lifehelper.photoalbum.album.pojo;

import lombok.Data;

/**
 * 创建相册请求数据
 *
 * <h2>主要用途
 * <p>发起创建相册请求时所需要传输的数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/16
 * @since 1.3.0
 **/
@Data
public class CreateAlbumDTO {
    /** 相册名称 */
    private String name;

    /** 相册描述 */
    private String description;
}
