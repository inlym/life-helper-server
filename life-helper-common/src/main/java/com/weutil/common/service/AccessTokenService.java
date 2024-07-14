package com.weutil.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weutil.common.model.AccessTokenDetail;
import com.weutil.common.util.RandomStringUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 访问凭证服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AccessTokenService {
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 创建访问凭证
     *
     * @param userId 用户 ID
     *
     * @return 访问凭证文本
     * @date 2024/7/14
     * @since 3.0.0
     */
    public String create(long userId) {
        // 默认有效期10天
        Duration duration = Duration.ofDays(10L);
        return create(userId, duration);
    }

    /**
     * 创建访问凭证
     *
     * @param userId   用户 ID
     * @param duration 有效时长
     *
     * @return 访问凭证文本
     * @date 2024/7/14
     * @since 3.0.0
     */
    @SneakyThrows
    public String create(long userId, Duration duration) {
        String token = RandomStringUtil.generate(32);
        AccessTokenDetail accessTokenDetail = AccessTokenDetail.builder().userId(userId).build();
        stringRedisTemplate.opsForValue().set(generateKey(token), objectMapper.writeValueAsString(accessTokenDetail), duration);
        log.info("[生成访问凭证] userId={}, token={}", userId, token);

        return token;
    }

    /**
     * 生成在 Redis 中使用的键名
     *
     * @param token 访问凭证
     *
     * @date 2024/7/14
     * @since 3.0.0
     */
    private final String generateKey(String token) {
        return "auth:access-token:" + token;
    }

    /**
     * 解析访问凭证
     *
     * @param token 访问凭证
     *
     * @date 2024/7/14
     * @since 3.0.0
     */
    @SneakyThrows
    public AccessTokenDetail parse(String token) {
        String key = generateKey(token);
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }

        return objectMapper.readValue(value, AccessTokenDetail.class);
    }
}
