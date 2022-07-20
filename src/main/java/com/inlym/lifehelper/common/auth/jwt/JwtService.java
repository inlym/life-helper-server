package com.inlym.lifehelper.common.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.inlym.lifehelper.common.auth.core.AuthenticationCredential;
import com.inlym.lifehelper.common.auth.core.SimpleAuthentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

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

    /**
     * 为普通用户创建登录凭证（JWT 字符串）
     *
     * <h2>备注（2022.07.20）
     * <p>该方法已弃用，待下次上线后可删除。
     *
     * @param userId 用户 ID
     *
     * @since 1.1.0
     */
    public String createTokenForUser(int userId) {
        // 当前时间的时间戳
        long now = System.currentTimeMillis();

        // 到期时间的时间戳
        long expireTime = now + DEFAULT_JWT_DURATION.toMillis();

        JWTCreator.Builder jwt = JWT
            .create()
            .withIssuer(ISSUER)
            .withIssuedAt(new Date(now))
            .withJWTId(UUID
                .randomUUID()
                .toString()
                .toLowerCase())
            .withExpiresAt(new Date(expireTime))
            .withClaim(USER_ID_FIELD, userId);

        return jwt.sign(algorithm);
    }

    /**
     * 创建鉴权凭证
     *
     * @param userId 用户 ID
     *
     * @since 1.3.0
     */
    public AuthenticationCredential createAuthenticationCredential(int userId) {
        long createTime = System.currentTimeMillis();
        long expireTime = createTime + DEFAULT_JWT_DURATION.toMillis();

        String token = JWT
            .create()
            .withIssuer(ISSUER)
            .withIssuedAt(new Date(createTime))
            .withJWTId(UUID
                .randomUUID()
                .toString()
                .toLowerCase())
            .withExpiresAt(new Date(expireTime))
            .withClaim(USER_ID_FIELD, userId)
            .sign(algorithm);

        return AuthenticationCredential
            .builder()
            .token(token)
            .createTime(createTime)
            .expireTime(expireTime)
            .type(AuthenticationCredential.Types.JWT)
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
        int userId = jwt
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
