package com.inlym.lifehelper.photoalbum.media.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加图片的请求数据
 *
 * <h2>主要用途
 * <p>发起向相册新增图片时使用的请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/9/4
 * @since 1.4.0
 **/
@Data
public class AddImageDTO {
    /** 资源路径 */
    @NotBlank
    private String path;

    /** 文件大小 */
    private Long size;

    /** 上传时间 */
    @NotNull
    private Long uploadTime;
}
