package com.inlym.lifehelper.external.lbsqq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "lbsqq")
@Data
public class LbsqqProperties {
    /**
     * 开发者密钥列表
     */
    private String[] keys;
}
