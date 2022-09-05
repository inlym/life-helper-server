package com.inlym.lifehelper.photoalbum.album.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

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
public class CreateAlbumDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 304148674946754017L;

    /** 相册名称 */
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    /** 相册描述 */
    @NotNull
    @Size(max = 200)
    private String description;
}
