package com.inlym.lifehelper.extern.wechat.service;

import com.inlym.lifehelper.extern.wechat.pojo.WeChatGetAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 微信服务端接口调用凭据服务
 *
 * <h2>说明
 * <p>围绕着微信服务端接口调用凭据的方法较多，因此将它们剥离出来，单独弄一个类。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/3
 * @since 1.3.0
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class WeChatAccessTokenService {
    /** 在 Redis 中存储微信服务端接口调用凭证的键名 */
    private static final String ACCESS_TOKEN_KEY = "wechat:access_token";

    /** 生产环境配置名称 */
    private static final String PRODUCTION_PROFILE = "prod";

    private final WeChatHttpService weChatHttpService;

    private final StringRedisTemplate stringRedisTemplate;

    private final Environment environment;

    /**
     * 强制刷新存储在 Redis 中的凭证
     *
     * <h2>说明
     * <p>无论之前是否存在或者是否正确，均强制获取并存储一个新的。
     *
     * @since 1.3.0
     */
    public String refresh() {
        String activeProfile = environment.getProperty("spring.profiles.active");

        // 备注（2022.11.29）
        // 由于微信服务端的冲突机制，设定只有在线上生产环境才能更新凭证，避免在开发环境更新导致线上生产环境中的凭证失效。
        if (PRODUCTION_PROFILE.equals(activeProfile)) {
            WeChatGetAccessTokenResponse data = weChatHttpService.getAccessToken();
            stringRedisTemplate
                .opsForValue()
                .set(ACCESS_TOKEN_KEY, data.getAccessToken(), Duration.ofSeconds(data.getExpiresIn()));

            return data.getAccessToken();
        }

        log.error("开发测试环境不允许自主更新微信服务端凭证，请从线上生产环境的 Redis 中获取并存储在当前环境的 Redis 中！");
        throw new RuntimeException("开发测试环境不允许自主更新微信服务端凭证");
    }

    /**
     * 异步刷新存储在 Redis 中的凭证
     *
     * <h2>说明
     * <p>用于当检测到凭证错误时，在错误捕获器中异步刷新。
     *
     * @since 1.3.0
     */
    @Async
    public void refreshAsync() {
        refresh();
    }

    /**
     * 获取接口调用凭证
     *
     * <h2>说明
     * <p>用于外部服务调用使用。
     *
     * @since 1.3.0
     */
    public String get() {
        // 从 Redis 中获取
        String token = stringRedisTemplate
            .opsForValue()
            .get(ACCESS_TOKEN_KEY);

        // Redis 中存在则直接返回，否则获取一个新的
        return token == null ? refresh() : token;
    }
}
