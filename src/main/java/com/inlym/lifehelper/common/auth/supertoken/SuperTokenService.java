package com.inlym.lifehelper.common.auth.supertoken;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 超级登录令牌服务
 *
 * <h2>主要用途
 * <p>管理超级登录令牌的生命周期。
 *
 * <h2>重要说明
 * <p>超级登录令牌可用于模拟任意用户，仅用于管理员开发调试，开发时主要做好防泄密处理。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/3
 * @since 1.7.2
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class SuperTokenService {
    public static final String SUPER_TOKEN_KEY = "system:super-token";

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 生成一个超级登录令牌
     *
     * <h2>注意事项
     * <p>生成的超级登录令牌只能人工登录 Redis 查看，无法通过其他任何方法获取。（目前用这个办法保证安全）
     *
     * @since 1.7.2
     */
    public void generate() {
        String token = IdUtil.simpleUUID();
        stringRedisTemplate
            .opsForValue()
            .set(SUPER_TOKEN_KEY, token, Duration.ofMinutes(30));
    }

    /**
     * 校验超级登录令牌有效性
     *
     * @param token 待校验的令牌
     *
     * @since 1.7.2
     */
    public boolean check(String token) {
        if (token == null) {
            return false;
        }

        String realToken = stringRedisTemplate
            .opsForValue()
            .get(SUPER_TOKEN_KEY);

        return token.equals(realToken);
    }
}
