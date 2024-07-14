package com.weutil.common.service;

import com.weutil.common.model.CustomHttpHeader;
import com.weutil.common.model.IdentityCertificate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 身份证书服务
 *
 * <h2>主要用途
 * <p>对访问凭证再做一层封装，用于登录后返回给客户端使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/15
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class IdentityCertificateService {
    /** 默认有效期：10天 */
    private static final Duration timeout = Duration.ofDays(10L);
    
    private final AccessTokenService accessTokenService;

    /**
     * 创建身份证书
     *
     * @param userId 用户 ID
     *
     * @date 2024/7/15
     * @since 3.0.0
     */
    public IdentityCertificate create(long userId) {
        String token = accessTokenService.create(userId, timeout);

        return IdentityCertificate.builder()
                .token(token)
                .headerName(CustomHttpHeader.ACCESS_TOKEN)
                .createTime(LocalDateTime.now())
                .expireTime(LocalDateTime.now().plus(timeout))
                .build();
    }
}
