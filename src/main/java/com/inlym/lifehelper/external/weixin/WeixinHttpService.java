package com.inlym.lifehelper.external.weixin;

import com.inlym.lifehelper.common.constant.RedisCacheCollector;
import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.weixin.pojo.WeixinCode2SessionResponse;
import com.inlym.lifehelper.external.weixin.pojo.WeixinGetAccessTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序服务端 HTTP 请求服务
 *
 * <h2>注意事项
 * <li>当前类中的方法只是将 HTTP 请求封装为内部可以调用的方法，不要对返回数据做二次处理。
 * <li>数据处理在 `WeixinService` 类中的方法中执行。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-23
 * @since 1.0.0
 */
@Service
@Slf4j
public class WeixinHttpService {
    private final WeixinProperties weixinProperties;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeixinHttpService(WeixinProperties weixinProperties) {
        this.weixinProperties = weixinProperties;

        this.restTemplate
            .getMessageConverters()
            .add(new WeixinMappingJackson2HttpMessageConverter());
    }

    /**
     * 通过 code 获取鉴权信息
     *
     * @param code 微信小程序端通过 wx.login 获取的 code
     *
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">官方文档</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.WECHAT_SESSION)
    public WeixinCode2SessionResponse code2Session(String code) throws ExternalHttpRequestException {
        String baseUrl = "https://api.weixin.qq.com/sns/jscode2session";

        String url = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("appid", weixinProperties.getAppid())
            .queryParam("secret", weixinProperties.getSecret())
            .queryParam("js_code", code)
            .queryParam("grant_type", "authorization_code")
            .build()
            .toUriString();

        WeixinCode2SessionResponse data = restTemplate.getForObject(url, WeixinCode2SessionResponse.class);

        assert data != null;
        if (data.getErrCode() == null || data.getErrCode() == 0) {
            log.info("[HTTP] [code2Session] code={}, data={}", code, data);
            return data;
        }
        throw new ExternalHttpRequestException("code2Session", url, data.getErrCode(), data.getErrMsg());
    }

    /**
     * 微信获取小程序全局唯一后台接口调用凭据（access_token）
     *
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/access-token/auth.getAccessToken.html">官方文档</a>
     * @since 1.0.0
     */
    public WeixinGetAccessTokenResponse getAccessToken() throws ExternalHttpRequestException {
        String baseUrl = "https://api.weixin.qq.com/cgi-bin/token";

        String url = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("grant_type", "client_credential")
            .queryParam("appid", weixinProperties.getAppid())
            .queryParam("secret", weixinProperties.getSecret())
            .build()
            .toUriString();

        WeixinGetAccessTokenResponse data = restTemplate.getForObject(url, WeixinGetAccessTokenResponse.class);

        assert data != null;
        if (data.getErrCode() == null || data.getErrCode() == 0) {
            log.info("[HTTP] [getAccessToken] data={}", data);
            return data;
        }
        throw new ExternalHttpRequestException("getAccessToken", url, data.getErrCode(), data.getErrMsg());
    }

    /**
     * 获取数量无限制的小程序码
     *
     * @param accessToken 接口调用凭证
     * @param page        页面 page，根路径前不要填加 /，不能携带参数（参数请放在scene字段里）
     * @param scene       最大32个可见字符，只支持数字，大小写英文以及部分特殊字符
     * @param width       二维码的宽度，单位 px，最小 280px，最大 1280px
     *
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.getUnlimited.html">官方文档</a>
     * @since 1.0.0
     */
    public byte[] getUnlimitedWxacode(String accessToken, String page, String scene, int width) throws ExternalHttpRequestException {
        String baseUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";

        String url = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("access_token", accessToken)
            .build()
            .toUriString();

        Map<String, Object> map = new HashMap<>(16);
        map.put("scene", scene);
        map.put("page", page);
        map.put("width", width);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);

        byte[] data = restTemplate.postForObject(url, request, byte[].class);

        // 实测 `data.length` 响应正常是 83748，异常是 118
        // 因此可以判断 `data.length` 是否大于 1000 来判定响应是否正常
        final int largeEnoughBytes = 1000;
        assert data != null;
        if (data.length > largeEnoughBytes) {
            return data;
        }
        throw new ExternalHttpRequestException("获取小程序码", url, "xxx", new String(data));
    }

    /**
     * 微信请求数据转换器
     *
     * <h2>为什么需要这个转换器？
     * <p>微信部分服务端 API 的 `Content-Type` 为 `text/plain`，需要额外支持解析 JSON
     *
     * @since 1.0.0
     */
    private static class WeixinMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public WeixinMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.TEXT_HTML);
            setSupportedMediaTypes(mediaTypes);
        }
    }
}
