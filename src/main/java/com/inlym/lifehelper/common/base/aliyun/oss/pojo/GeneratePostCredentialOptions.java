package com.inlym.lifehelper.common.base.aliyun.oss.pojo;

import com.inlym.lifehelper.common.base.aliyun.oss.OssDir;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * 客户端直传文件至 OSS 的临时鉴权凭证配置信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/8
 * @since 1.2.3
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratePostCredentialOptions {
    /** 凭证有效时长，默认 2 天 */
    private Duration ttl = Duration.ofHours(2);

    /** 上传文件的最大体积，默认 50MB */
    private Long maxSize = 50L * 1024 * 1024;

    /** 上传的目录，为防止误传到根目录，默认临时目录 */
    private String dirname = OssDir.TEMP;
}
