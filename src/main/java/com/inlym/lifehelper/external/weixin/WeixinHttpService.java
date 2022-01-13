package com.inlym.lifehelper.external.weixin;

import com.inlym.lifehelper.external.weixin.model.WeixinCode2SessionResult;
import com.inlym.lifehelper.external.weixin.model.WeixinGetAccessTokenResult;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeixinHttpService {
    private final WeixinProperties weixinProperties;

    private final RestTemplate restTemplate;

    public WeixinHttpService(WeixinProperties weixinProperties) {
        this.weixinProperties = weixinProperties;
        this.restTemplate = new RestTemplate();

        this.restTemplate
            .getMessageConverters()
            .add(new WeixinMappingJackson2HttpMessageConverter());
    }

    /**
     * 通过 code 获取鉴权信息
     *
     * @param code 小程序端通过 wx.login 获取的 code
     *
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">auth.code2Session</a>
     */
    public WeixinCode2SessionResult code2Session(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";

        UriComponents uriBuilder = UriComponentsBuilder
            .fromHttpUrl(url)
            .queryParam("appid", weixinProperties.getAppid())
            .queryParam("secret", weixinProperties.getSecret())
            .queryParam("js_code", code)
            .queryParam("grant_type", "authorization_code")
            .build();

        WeixinCode2SessionResult session = restTemplate.getForObject(uriBuilder.toUriString(), WeixinCode2SessionResult.class);
        System.out.println(session);
        return session;
    }

    /**
     * 微信获取小程序全局唯一后台接口调用凭据（access_token）
     *
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/access-token/auth.getAccessToken.html">auth.getAccessToken</a>
     */
    public WeixinGetAccessTokenResult getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token";

        UriComponents uriComponents = UriComponentsBuilder
            .fromHttpUrl(url)
            .queryParam("grant_type", "client_credential")
            .queryParam("appid", weixinProperties.getAppid())
            .queryParam("secret", weixinProperties.getSecret())
            .build();

        WeixinGetAccessTokenResult result = restTemplate.getForObject(uriComponents.toUriString(), WeixinGetAccessTokenResult.class);
        System.out.println(result);
        return result;
    }

    public static class WeixinMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public WeixinMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.TEXT_HTML);
            setSupportedMediaTypes(mediaTypes);
        }
    }
}
