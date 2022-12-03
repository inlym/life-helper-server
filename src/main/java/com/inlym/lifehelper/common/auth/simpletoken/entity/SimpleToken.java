package com.inlym.lifehelper.common.auth.simpletoken.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 简易令牌
 *
 * <h2>主要用途
 * <p>用做登录凭证。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/24
 * @since 1.7.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "database:simple-token")
public class SimpleToken {
    /** 有效时长 */
    public static Duration expiration = Duration.ofDays(10L);

    /** 令牌 ID */
    @Id
    private String id;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 过期时间 */
    private LocalDateTime expireTime;

    /** 用户 ID */
    private Integer userId;

    /**
     * 获取到期时间
     *
     * <h2>说明
     * <p>当前方法用于配置实体在 Redis 中的有效期。
     */
    @TimeToLive
    public long getTimeToLive() {
        // 多加了10分钟作为预留时间
        return expiration.toSeconds() + Duration
            .ofMillis(10L)
            .toSeconds();
    }
}
