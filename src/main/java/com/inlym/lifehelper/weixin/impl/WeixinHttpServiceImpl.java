package com.inlym.lifehelper.weixin.impl;

import com.inlym.lifehelper.weixin.WeixinHttpService;
import com.inlym.lifehelper.weixin.WeixinProperties;
import com.inlym.lifehelper.weixin.model.WeixinCode2SessionResult;
import com.inlym.lifehelper.weixin.model.WeixinGetAccessTokenResult;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeixinHttpServiceImpl implements WeixinHttpService {
    private final WeixinProperties weixinProperties;

    private final RestTemplate restTemplate;

    public WeixinHttpServiceImpl(WeixinProperties weixinProperties) {
        this.weixinProperties = weixinProperties;
        this.restTemplate = new RestTemplate();

        this.restTemplate
            .getMessageConverters()
            .add(new WeixinMappingJackson2HttpMessageConverter());
    }

    @Override
    @Cacheable(cacheNames = "weixin:session:code#1d", key = "#code")
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

    @Override
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
