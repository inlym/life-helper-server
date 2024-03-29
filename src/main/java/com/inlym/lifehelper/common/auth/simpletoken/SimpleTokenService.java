package com.inlym.lifehelper.common.auth.simpletoken;

import cn.hutool.core.util.IdUtil;
import com.inlym.lifehelper.common.auth.core.IdentityCertificate;
import com.inlym.lifehelper.common.auth.core.SimpleAuthentication;
import com.inlym.lifehelper.common.auth.simpletoken.entity.SimpleToken;
import com.inlym.lifehelper.common.auth.simpletoken.exception.InvalidSimpleTokenException;
import com.inlym.lifehelper.common.auth.simpletoken.repository.SimpleTokenRepository;
import com.inlym.lifehelper.common.constant.CustomHttpHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 简易登录令牌服务
 *
 * <h2>主要用途
 * <p>管理简易登录令牌的生命周期。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/24
 * @since 1.7.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleTokenService {
    private final SimpleTokenRepository repository;

    /**
     * 创建登录鉴权安全令牌
     *
     * @param userId 用户 ID
     *
     * @since 1.7.0
     */
    public IdentityCertificate generateIdentityCertificate(long userId) {
        SimpleToken simpleToken = create(userId);

        return IdentityCertificate
            .builder()
            .token(simpleToken.getId())
            .headerName(CustomHttpHeader.AUTH_TOKEN)
            .createTime(simpleToken.getCreateTime())
            .expireTime(simpleToken.getExpireTime())
            .build();
    }

    /**
     * 解析获取鉴权凭证
     *
     * @param token 登录鉴权令牌
     *
     * @since 1.7.0
     */
    public SimpleAuthentication parse(String token) {
        Optional<SimpleToken> result = repository.findById(token);
        if (result.isEmpty()) {
            throw InvalidSimpleTokenException.of(token);
        }

        SimpleToken simpleToken = result.get();
        return new SimpleAuthentication(simpleToken.getUserId());
    }

    /**
     * 创建一个简易登录令牌
     *
     * @param userId 用户 ID
     *
     * @since 1.7.0
     */
    private SimpleToken create(long userId) {
        SimpleToken simpleToken = SimpleToken
            .builder()
            .id(IdUtil.simpleUUID())
            .createTime(LocalDateTime.now())
            .expireTime(LocalDateTime
                            .now()
                            .plusSeconds(SimpleToken.expiration.toSeconds()))
            .userId(userId)
            .build();

        repository.save(simpleToken);

        return simpleToken;
    }
}
