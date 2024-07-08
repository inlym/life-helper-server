package com.weutil.external.wechat.service;

import com.weutil.external.wechat.config.WeChatProperties;
import com.weutil.external.wechat.pojo.WeChatGetAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 微信稳定版接口调用凭据服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/4/24
 * @since 2.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class WeChatStableAccessTokenService {
    /** 在 Redis 中使用的键名 */
    private static final String REDIS_KEY = "wechat:stable-access-token";

    private final WeChatHttpService weChatHttpService;

    private final StringRedisTemplate stringRedisTemplate;

    private final WeChatProperties weChatProperties;

    /**
     * 获取微信稳定版接口调用凭据
     *
     * <h2>说明
     * <p>封装用于外部调用。
     *
     * @date 2023/4/24
     * @since 2.0.0
     */
    public String get(String appId) {
        String token = stringRedisTemplate
                .opsForValue()
                .get(REDIS_KEY + ":" + appId);

        // Redis 中存在则直接返回，否则获取一个新的
        return token == null ? refresh(appId) : token;
    }

    /**
     * 刷新微信稳定版接口调用凭据
     *
     * @date 2023/4/24
     * @since 2.0.0
     */
    public String refresh(String appId) {
        WeChatGetAccessTokenResponse response = weChatHttpService.getStableAccessToken(appId);
        String token = response.getAccessToken();
        int expiresIn = response.getExpiresIn();

        stringRedisTemplate
                .opsForValue()
                .set(REDIS_KEY + ":" + appId, token, Duration.ofSeconds(expiresIn));

        return token;
    }

    /**
     * 对所有小程序进行刷新
     *
     * @since 2.1.0
     */
    public void refreshAll() {
        refresh(weChatProperties
                .getMainApp()
                .getAppId());
        refresh(weChatProperties
                .getAiApp()
                .getAppId());
        refresh(weChatProperties
                .getWeatherApp()
                .getAppId());
    }
}
