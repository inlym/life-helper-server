package com.weutil.aliyun.apigw.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.weutil.aliyun.apigw.config.AliyunApigwProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * 阿里云 API 网站 JWT 认证插件服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/11/18
 * @see <a href="https://help.aliyun.com/zh/api-gateway/traditional-api-gateway/user-guide/jwt-authentication">JWT 认证插件</a>
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AliyunApigwJwtAuthenticationService {
    private final AliyunApigwProperties properties;

    /**
     * 封装访问凭证
     *
     * <h2>说明
     * <p>将原有的访问凭证封装到 JWT 里面。
     *
     * @param accessToken 访问凭证
     *
     * @date 2024/11/18
     * @since 3.0.0
     */
    public String packageAccessToken(String accessToken) {
        Algorithm algorithm = Algorithm.RSA256(getPrivateKey());

        return JWT.create()
            .withKeyId(properties.getKid())
            .withIssuer("weutil.com")
            .withClaim("token", accessToken)
            .sign(algorithm);
    }

    /**
     * 构建 RSA 私钥
     *
     * @date 2024/11/18
     * @since 3.0.0
     */
    @SneakyThrows
    private RSAPrivateKey getPrivateKey() {
        String text = properties.getPrivateKey().replaceAll("\\n", "")
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(text);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
}
