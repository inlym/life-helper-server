package com.weutil.oss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * 生成直传凭据的配置项
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/15
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratingPostCredentialOptions {
    /**
     * 文件后缀名
     *
     * <h3>示例值
     * <p>{@code png}
     */
    private String extension;

    /** 限制最大上传文件大小，单位为 MD */
    private Long sizeMB;

    /** 直传凭据有效时长 */
    private Duration duration;
}
