package com.inlym.lifehelper.common.base.aliyun.oss.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 阿里云 OSS 临时上传凭证
 *
 * <h2>主要用途
 * <p>用于客户端将资源直传阿里云的 OSS 提供鉴权凭证。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/13
 * @since 1.4.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssPostCredential {
    /** 客户端使用时参数改为 `OSSAccessKeyId` */
    @JsonProperty("OSSAccessKeyId")
    private String accessKeyId;

    private String url;

    private String key;

    private String policy;

    private String signature;
}
