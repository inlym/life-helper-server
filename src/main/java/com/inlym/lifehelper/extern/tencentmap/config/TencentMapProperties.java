package com.inlym.lifehelper.extern.tencentmap.config;

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
    /**
     * 开发者密钥列表
     *
     * <h3>为什么要使用一个密钥列表而不是单个密钥？
     * <p>在调用 API 时，存在并发限制，使用一个密钥列表进行轮询调用避免达到并发限制。
     *
     * @see <a href="https://lbs.qq.com/service/webService/webServiceGuide/webServiceQuota">配额限制说明</a>
     */
    private String[] keys;
}
