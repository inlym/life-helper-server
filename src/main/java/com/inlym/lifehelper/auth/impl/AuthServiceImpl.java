package com.inlym.lifehelper.auth.impl;

import com.inlym.lifehelper.auth.AuthService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final StringRedisTemplate stringRedisTemplate;

    public AuthServiceImpl(StringRedisTemplate stringRedisTemplate) {this.stringRedisTemplate = stringRedisTemplate;}

    /**
     * 为指定用户 ID 生成登录凭证
     *
     * @param userId 用户 ID
     */
    @Override
    public String createToken(int userId) {
        // 生成登录凭证
        String token = UUID
            .randomUUID()
            .toString()
            .replaceAll("-", "");

        // Redis 键名
        String redisKey = "auth:user_id:token:" + token;

        // Redis 值
        String redisValue = String.valueOf(userId);

        // 存入 Redis
        stringRedisTemplate
            .opsForValue()
            .set(redisKey, redisValue, Duration.ofDays(10));

        return token;
    }

    /**
     * 从登录凭证中解析用户 ID，若凭证无效则返回 0
     *
     * @param token 登录凭证
     */
    @Override
    public int getUserIdByToken(String token) {
        // Redis 键名
        String redisKey = "auth:user_id:token:" + token;

        String value = stringRedisTemplate
            .opsForValue()
            .get(redisKey);

        if (value != null) {
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }
}
