package com.weutil.oss.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 生成直传凭据的请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/15
 * @since 3.0.0
 **/
@Data
public class GeneratingPostCredentialDTO {
    /**
     * 文件后缀名
     *
     * <h3>示例值
     * <p>{@code png}
     */
    @NotBlank
    @Pattern(regexp = "^(png|jpg|jpeg|gif|webp)$", message = "请选择正确的图片上传！")
    private String extension;
}
