package com.inlym.lifehelper.external.weixin;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import org.springframework.stereotype.Service;

@Service
public class WeixinService {
    private final WeixinHttpService weixinHttpService;

    public WeixinService(WeixinHttpService weixinHttpService) {this.weixinHttpService = weixinHttpService;}

    /**
     * 通过从微信小程序中获取的 code 换取用户对应的 openid
     *
     * @param code 微信小程序端通过 wx.login 获取的 code
     */
    public String getOpenidByCode(String code) throws ExternalHttpRequestException {
        return weixinHttpService
            .code2Session(code)
            .getOpenid();
    }
}
