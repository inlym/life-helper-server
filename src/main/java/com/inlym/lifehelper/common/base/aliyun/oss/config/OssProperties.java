package com.inlym.lifehelper.common.base.aliyun.oss.config;

import com.inlym.lifehelper.common.base.aliyun.oss.model.OssBucket;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云 OSS 配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/24
 * @since 2.2.0
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class OssProperties {
    /** 主仓库 */
    private OssBucket main;

    /** 用户直连仓库 */
    private OssBucket ugc;
}
