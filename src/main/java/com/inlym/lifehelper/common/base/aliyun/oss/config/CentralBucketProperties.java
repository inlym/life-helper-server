package com.inlym.lifehelper.common.base.aliyun.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 主存储空间配置信息
 *
 * <h2>介绍
 *
 * <h3>主要用途
 * <p>存储项目运行中产生的所有文件资源。
 *
 * <h3>使用说明
 * <p>经过校验（内容安全检测）的资源才允许存入当前存储空间。
 *
 * <h3>目录规范
 * <p>根据用途只建立一级目录，例如 {@code avatar}，所有资源放在均放在对应的一级目录下。
 *
 * <h3>文件名规范
 * <p>文件名由程序随机产生，需要包含后缀名，例如 {@code nUztcRQGBetU.webp}
 *
 * <h3>读写权限
 * <p>私有，资源通过
 * <a href="https://help.aliyun.com/zh/oss/user-guide/how-to-obtain-the-url-of-a-single-object-or-the-urls-of-multiple-objects">使用文件URL分享文件</a>
 * 的方式生成访问链接。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/7
 * @since 2.3.0
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.oss.central")
@Data
public class CentralBucketProperties {
    /**
     * 存储空间名称
     *
     * <h4>示例
     * <p>{@code weutil-central}
     */
    private String bucketName;

    /**
     * 绑定的自定义域名 URL
     *
     * <h4>示例
     * <p>{@code https://res.weutil.com}
     */
    private String customUrl;

    /** 访问密钥 ID */
    private String accessKeyId;

    /** 访问密钥口令 */
    private String accessKeySecret;
}
