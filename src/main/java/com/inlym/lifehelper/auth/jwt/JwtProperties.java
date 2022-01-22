package com.inlym.lifehelper.auth.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置项
 *
 * @author inlym
 * @since 2022-01-22 19:43
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    /** 签名密钥 */
    private String secret;
}
