package com.inlym.lifehelper.common.base.aliyun.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 静态资源专用存储空间配置信息
 *
 * <h2>介绍
 *
 * <h3>主要用途
 * <p>存储文件地址不变的官方资源，例如 {@code /logo.png}。
 *
 * <h3>使用说明
 * <p>所有资源均需要管理员手动上传，不允许使用随机文件名。
 *
 * <h3>目录规范
 * <p>根据实际需要，分用途建立，不必在乎目录层级。
 *
 * <h3>文件名规范
 * <p>根据实际需要命名。
 *
 * <h3>读写权限
 * <p>公共读。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @since 2024/6/8
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.oss.static-resource")
@Data
public class StaticResourceBucketProperties {
    /**
     * 绑定的自定义域名
     *
     * <h4>示例
     * <p>{@code static.weutil.com}
     */
    private String customDomain;
}
