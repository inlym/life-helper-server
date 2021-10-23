package com.inlym.lifehelper.weixin;

import com.inlym.lifehelper.weixin.model.Code2SessionResponse;
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
    private final WeixinConfig weixinConfig;

    private final RestTemplate restTemplate;

    public WeixinHttpServiceImpl(WeixinConfig weixinConfig) {
        this.weixinConfig = weixinConfig;
        this.restTemplate = new RestTemplate();

        this.restTemplate
            .getMessageConverters()
            .add(new WeixinMappingJackson2HttpMessageConverter());
    }

    public static class WeixinMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public WeixinMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.TEXT_HTML);
            setSupportedMediaTypes(mediaTypes);
        }
    }

    @Override
    public Code2SessionResponse code2Session(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";

        UriComponents uriBuilder = UriComponentsBuilder
            .fromHttpUrl(url)
            .queryParam("appid", weixinConfig.getAppid())
            .queryParam("secret", weixinConfig.getSecret())
            .queryParam("js_code", code)
            .queryParam("grant_type", "authorization_code")
            .build();

        Code2SessionResponse session = restTemplate.getForObject(uriBuilder.toUriString(), Code2SessionResponse.class);
        System.out.println(session);
        return session;
    }
}
