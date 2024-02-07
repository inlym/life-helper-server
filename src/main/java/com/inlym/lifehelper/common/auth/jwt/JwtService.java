package com.inlym.lifehelper.common.auth.jwt;

import cn.hutool.core.util.IdUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.inlym.lifehelper.common.auth.core.IdentityCertificate;
import com.inlym.lifehelper.common.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.common.constant.CustomHttpHeader;
import com.inlym.lifehelper.common.constant.SecurityTokenType;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * JWT 服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-22
 * @since 1.0.0
 */
@Service
public class JwtService {
    /** 默认 JWT 有效期：30天 */
    public static final Duration DEFAULT_JWT_DURATION = Duration.ofDays(30);

    /** 在 JWT 中存储用户 ID 的字段名 */
    private static final String USER_ID_FIELD = "uid";

    /** 在 JWT 中存储权限信息的字段名 */
    private static final String AUTHORITIES_FIELD = "aut";

    /** JWT 签发者，这里放了官网域名 */
    private static final String ISSUER = "lifehelper.com.cn";

    /** JWT 算法，用于签名和验证环节 */
    private final Algorithm algorithm;

    public JwtService(JwtProperties jwtProperties) {
        this.algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
    }

    public IdentityCertificate generateSecurityToken(long userId) {
        LocalDateTime createTime = LocalDateTime.now();
        LocalDateTime expireTime = createTime.plusSeconds(DEFAULT_JWT_DURATION.toSeconds());

        String token = JWT
            .create()
            .withIssuer(ISSUER)
            .withJWTId(IdUtil.simpleUUID())
            .withIssuedAt(createTime.toInstant(ZoneOffset.ofHours(8)))
            .withExpiresAt(expireTime.toInstant(ZoneOffset.ofHours(8)))
            .withClaim(USER_ID_FIELD, userId)
            .sign(algorithm);

        return IdentityCertificate
            .builder()
            .token(token)
            .type(SecurityTokenType.JSON_WEB_TOKEN)
            .headerName(CustomHttpHeader.JWT_TOKEN)
            .createTime(createTime)
            .expireTime(expireTime)
            .build();
    }

    /**
     * 解析 JWT 字符串，并返回 {@linkplain SimpleAuthentication 自定义的鉴权凭证}
     *
     * @param token JWT 字符串
     *
     * @since 1.0.0
     */
    public SimpleAuthentication parse(String token) {
        JWTVerifier verifier = JWT
            .require(algorithm)
            .withIssuer(ISSUER)
            .build();

        DecodedJWT jwt = verifier.verify(token);

        // 用户 ID
        long userId = jwt
            .getClaim(USER_ID_FIELD)
            .asInt();

        // 权限字符串
        String rolesStr = jwt
            .getClaim(AUTHORITIES_FIELD)
            .asString();

        if (rolesStr == null) {
            return new SimpleAuthentication(userId);
        } else {
            String[] roles = rolesStr.split(",");
            return new SimpleAuthentication(userId, roles);
        }
    }
}
