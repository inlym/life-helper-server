package com.weutil.common.base.aliyun.oss.constant;

import lombok.Getter;

/**
 * 阿里云 OSS 的访问端口（Endpoint）
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/8
 * @since 2.3.0
 **/
@Getter
public enum OssEndpoint {
    /** 公网访问 */
    PUBLIC_NETWORK("oss-cn-hangzhou.aliyuncs.com"),

    /** 内网（VPC）访问 */
    INTERNAL_NETWORK("oss-cn-hangzhou-internal.aliyuncs.com");

    private final String domain;

    OssEndpoint(String domain) {
        this.domain = domain;
    }
}
