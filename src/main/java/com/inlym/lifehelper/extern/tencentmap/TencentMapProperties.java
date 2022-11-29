package com.inlym.lifehelper.extern.tencentmap;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 腾讯位置服务配置信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-14
 * @since 1.0.0
 **/
@Component
@ConfigurationProperties(prefix = "lifehelper.tencent-map")
@Data
public class TencentMapProperties {
    /** 开发者密钥列表（这是一个密钥数组） */
    private String[] keys;
}
