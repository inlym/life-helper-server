package com.inlym.lifehelper.common.base.aliyun.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 用户直传专用存储空间配置信息
 *
 * <h2>介绍
 *
 * <h3>主要用途
 * <p>对于用户上传的资源，先通过“直传”的方式，存入当前存储空间，再经过校验后，转储至主存储空间。
 *
 * <h3>使用说明
 * <p>由服务器生成
 * <a href="https://help.aliyun.com/zh/oss/use-cases/obtain-signature-information-from-the-server-and-upload-data-to-oss">直传凭据</a>，
 * 客户端再使用凭据将资源直传至当前存储空间。
 *
 * <h3>目录规范
 * <p>根据日期只建立一级目录，例如 {@code 20240608}，所有资源放在均放在对应的一级目录下。
 *
 * <h3>文件名规范
 * <p>文件名由程序随机产生，需要包含后缀名，例如 {@code nUztcRQGBetU.webp}。
 *
 * <h3>读写权限
 * <p>私有，不支持用户访问。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @since 2024/6/8
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.oss.user-upload")
@Data
public class UserUploadBucketProperties {
    /**
     * 存储空间名称
     *
     * <h4>示例
     * <p>{@code weutil-upload}
     */
    private String bucketName;

    /**
     * 绑定的自定义域名
     *
     * <h4>示例
     * <p>{@code upload.weutil.com}
     */
    private String customDomain;

    /** 访问密钥 ID */
    private String accessKeyId;

    /** 访问密钥口令 */
    private String accessKeySecret;
}
