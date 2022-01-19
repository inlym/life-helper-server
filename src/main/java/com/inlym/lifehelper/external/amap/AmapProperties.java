package com.inlym.lifehelper.external.amap;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 高德开放平台配置信息
 *
 * @author inlym
 * @see <a href="https://lbs.amap.com/">高德开放平台</a>
 * @since 2022-01-19 19:04
 **/
@Component
@ConfigurationProperties(prefix = "amap")
@Data
public class AmapProperties {
    /** 开发版密钥 */
    private String key;
}
