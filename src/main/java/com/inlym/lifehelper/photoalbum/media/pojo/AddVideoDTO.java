package com.inlym.lifehelper.photoalbum.media.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 添加视频的请求数据
 *
 * <h2>主要用途
 * <p>发起向相册新增视频时使用的请求数据
 *
 * <h2>部分字段未加校验的原因：
 * <p>这些字段均从微信的 API 获取，虽然文档注明会给出，但考虑到字段为空的情况，此时不影响主逻辑，因此不校验保证流程不阻断。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/9/4
 * @since 1.4.0
 **/
@Data
public class AddVideoDTO {
    /** 资源路径 */
    @NotBlank
    private String path;

    /** 文件大小 */
    private Long size;

    /** 上传时间 */
    @NotNull
    private Long uploadTime;

    /** 宽度 */
    private Integer width;

    /** 高度 */
    private Integer height;

    /** 视频缩略图路径 */
    @NotBlank
    private String thumbPath;

    /** 视频时长 */
    private Long duration;
}
