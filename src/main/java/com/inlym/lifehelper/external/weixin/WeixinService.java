package com.inlym.lifehelper.external.weixin;

import com.inlym.lifehelper.external.weixin.model.WeixinGetAccessTokenResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 微信服务
 *
 * @author inlym
 * @since 2022-01-23 01:43
 */
@Service
@Slf4j
public class WeixinService {
    /** 在 Redis 中存储微信服务端接口调用凭证的键名 */
    public static final String WEIXIN_ACCESS_TOKEN_KEY = "weixin:token";

    private final WeixinHttpService weixinHttpService;

    private final StringRedisTemplate stringRedisTemplate;

    public WeixinService(WeixinHttpService weixinHttpService, StringRedisTemplate stringRedisTemplate) {
        this.weixinHttpService = weixinHttpService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 通过从微信小程序中获取的 code 换取用户对应的 openid
     *
     * @param code 微信小程序端通过 wx.login 获取的 code
     */
    @SneakyThrows
    public String getOpenidByCode(String code) {
        return weixinHttpService
            .code2Session(code)
            .getOpenid();
    }

    /**
     * 更新在 Redis 中的微信服务端接口调用凭证
     */
    @SneakyThrows
    private String updateAccessToken() {
        WeixinGetAccessTokenResponse data = weixinHttpService.getAccessToken();
        String accessToken = data.getAccessToken();
        int expiration = data.getExpiresIn();

        stringRedisTemplate
            .opsForValue()
            .set(WEIXIN_ACCESS_TOKEN_KEY, accessToken, Duration.ofSeconds(expiration));

        return accessToken;
    }

    /**
     * 用于内部使用获取微信服务端接口调用凭证
     */
    @SneakyThrows
    private String getAccessTokenInternal() {
        String token = stringRedisTemplate
            .opsForValue()
            .get(WEIXIN_ACCESS_TOKEN_KEY);
        return token != null ? token : updateAccessToken();
    }

    /**
     * 更新在 Redis 中的微信服务端接口调用凭证定时任务
     * <p>
     * 任务每 60 分钟执行一次
     */
    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.MINUTES)
    private void updateAccessTokenCron() {
        String token = updateAccessToken();
        log.info("[定时任务] 更新在 Redis 中的微信服务端接口调用凭证，新的凭证=" + token);
    }
}
