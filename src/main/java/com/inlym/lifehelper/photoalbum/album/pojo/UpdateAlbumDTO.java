package com.inlym.lifehelper.photoalbum.album.pojo;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 更新相册请求数据
 *
 * <h2>主要用途
 * <p>发起更新相册请求时所需要传输的数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/9/4
 * @since 1.4.0
 **/
@Data
public class UpdateAlbumDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 259323035005890926L;

    /** 相册名称 */
    @Size(min = 1, max = 20)
    private String name;

    /** 相册描述 */
    @Size(max = 200)
    private String description;
}
