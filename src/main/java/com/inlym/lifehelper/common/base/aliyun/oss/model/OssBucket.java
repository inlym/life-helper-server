package com.inlym.lifehelper.common.base.aliyun.oss.model;

import lombok.Data;

/**
 * 单个阿里云OSS存储桶配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/24
 * @since 2.2.0
 **/
@Data
public class OssBucket {
    /** 访问密钥 ID */
    private String accessKeyId;

    /** 访问密钥口令 */
    private String accessKeySecret;

    /** 访问域名 */
    private String endpoint;

    /** 存储空间名称 */
    private String bucketName;

    /** 自定义 URL 地址 */
    private String aliasUrl;
}
