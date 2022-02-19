package com.inlym.lifehelper.external.tencentmap;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 腾讯位置服务配置信息
 *
 * @author inlym
 * @date 2022-02-14 18:59
 **/
@Component
@ConfigurationProperties(prefix = "lifehelper.tencent-map")
@Data
public class TencentMapProperties {
    /** 开发者密钥列表 */
    private String[] keys;
}
