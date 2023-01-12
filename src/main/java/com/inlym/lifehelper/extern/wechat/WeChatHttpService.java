package com.inlym.lifehelper.extern.wechat;

import com.inlym.lifehelper.common.constant.RedisCacheCollector;
import com.inlym.lifehelper.extern.wechat.exception.WeChatCommonException;
import com.inlym.lifehelper.extern.wechat.exception.WeChatInvalidAccessTokenException;
import com.inlym.lifehelper.extern.wechat.pojo.UnlimitedQrCodeOptions;
import com.inlym.lifehelper.extern.wechat.pojo.WeChatCode2SessionResponse;
import com.inlym.lifehelper.extern.wechat.pojo.WeChatCommonResponse;
import com.inlym.lifehelper.extern.wechat.pojo.WeChatGetAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 微信小程序服务端 HTTP 请求服务
 *
 * <h2>注意事项
 * <p>当前类中的方法只是将 HTTP 请求封装为内部可以调用的方法，不要对返回数据做二次处理。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/3
 * @since 1.3.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class WeChatHttpService {
    private final RestTemplate restTemplate;

    private final WeChatProperties properties;

    /**
     * 校验相应数据是否正常
     *
     * @param data 响应数据
     *
     * @since 1.3.0
     */
    private boolean validateResponse(WeChatCommonResponse data) {
        return data != null && (data.getErrorCode() == null || data.getErrorCode() == 0);
    }

    /**
     * 获取接口调用凭据
     *
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-access-token/getAccessToken.html">获取接口调用凭据</a>
     * @since 1.3.0
     */
    public WeChatGetAccessTokenResponse getAccessToken() {
        String baseUrl = "https://api.weixin.qq.com/cgi-bin/token";

        String url = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("grant_type", "client_credential")
            .queryParam("appid", properties.getAppid())
            .queryParam("secret", properties.getSecret())
            .build()
            .toUriString();

        WeChatGetAccessTokenResponse data = restTemplate.getForObject(url, WeChatGetAccessTokenResponse.class);

        assert data != null;
        if (validateResponse(data)) {
            return data;
        }

        throw WeChatCommonException.create(data.getErrorCode(), data.getErrorMessage());
    }

    /**
     * 小程序登录，通过在小程序端 `wx.login` 接口获得临时登录凭证 code 获取身份信息
     *
     * @param code 临时登录凭证
     *
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html">小程序登录</a>
     * @since 1.3.0
     */
    @Cacheable(RedisCacheCollector.WECHAT_SESSION)
    public WeChatCode2SessionResponse code2Session(String code) {
        String baseUrl = "https://api.weixin.qq.com/sns/jscode2session";

        String url = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("appid", properties.getAppid())
            .queryParam("secret", properties.getSecret())
            .queryParam("js_code", code)
            .queryParam("grant_type", "authorization_code")
            .build()
            .toUriString();

        WeChatCode2SessionResponse data = restTemplate.getForObject(url, WeChatCode2SessionResponse.class);

        assert data != null;
        if (validateResponse(data)) {
            return data;
        }

        throw WeChatCommonException.create(data.getErrorCode(), data.getErrorMessage());
    }

    /**
     * 生成小程序码
     *
     * @param accessToken 微信服务端接口调用凭证
     * @param options     小程序码配置信息
     *
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/qrcode-link/qr-code/getUnlimitedQRCode.html">获取不限制的小程序码</a>
     * @since 1.3.0
     */
    public byte[] getUnlimitedQrCode(String accessToken, UnlimitedQrCodeOptions options) {
        String baseUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";

        String url = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("access_token", accessToken)
            .build()
            .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UnlimitedQrCodeOptions> request = new HttpEntity<>(options, headers);

        byte[] data = restTemplate.postForObject(url, request, byte[].class);

        // 实测 `data.length` 响应正常是 83748，异常是 118
        // 因此可以判断 `data.length` 是否大于 1000 来判定响应是否正常
        final int largeEnoughBytes = 1000;
        assert data != null;
        if (data.length > largeEnoughBytes) {
            return data;
        } else {
            throw WeChatInvalidAccessTokenException.create(accessToken);
        }
    }
}
