package com.inlym.lifehelper.common.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置项
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @version 1.3.0
 * @date 2022-01-22
 * @since 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "lifehelper.auth.jwt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtProperties {
    /** 签名密钥 */
    private String secret;
}
