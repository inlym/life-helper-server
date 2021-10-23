package com.inlym.lifehelper.weixin;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeixinServiceImpl implements WeixinService {
    private final WeixinConfig weixinConfig;

    public WeixinServiceImpl(WeixinConfig weixinConfig) {this.weixinConfig = weixinConfig;}

    private Code2SessionResponse getSession(String code) {

        String url = "https://api.weixin.qq.com/sns/jscode2session";

        UriComponents uriBuilder = UriComponentsBuilder
            .fromHttpUrl(url)
            .queryParam("appid", weixinConfig.getAppid())
            .queryParam("secret", weixinConfig.getSecret())
            .queryParam("js_code", code)
            .queryParam("grant_type", "authorization_code")
            .build();

        System.out.println(uriBuilder.toUriString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate
            .getMessageConverters()
            .add(new WeixinMappingJackson2HttpMessageConverter());

        Code2SessionResponse session = restTemplate.getForObject(uriBuilder.toUriString(), Code2SessionResponse.class);
        System.out.println(session);

        return session;
    }

    @Override
    public String getOpenId(String code) {
        Code2SessionResponse session = getSession(code);
        return session.getOpenid();
    }
}
