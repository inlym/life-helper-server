package com.inlym.lifehelper.extern.xfyun.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 讯飞开放平台配置信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/3/1
 * @since 2.3.0
 **/
@Component
@ConfigurationProperties("lifehelper.xfyun")
@Data
public class XfyunProperties {
    private String addId;

    private String key;

    private String secret;
}
