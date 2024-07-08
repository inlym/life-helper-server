package com.weutil.common.base.aliyun.oss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * 生成直传凭据的配置项
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/8
 * @since 2.3.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratePostCredentialOptions {
    /**
     * 上传至 OSS 后的文件路径
     *
     * <h3>示例值
     * <p>{@code 20240608/r7OPIjuso1rR.png}
     */
    private String key;

    /** 限制最大上传文件大小，单位为 MD */
    private Long sizeMB;

    /** 直传凭据有效时长 */
    private Duration duration;
}
