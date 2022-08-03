package com.inlym.lifehelper.external.wechat;

import com.inlym.lifehelper.external.wechat.exception.WeChatCommonException;
import com.inlym.lifehelper.external.wechat.pojo.WeChatGetAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        if (data.getErrorCode() == null || data.getErrorCode() == 0) {
            return data;
        }

        throw WeChatCommonException.create(data.getErrorCode(), data.getErrorMessage());
    }
}
