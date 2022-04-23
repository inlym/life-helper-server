package com.inlym.lifehelper.external.weixin;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 微信封装服务
 *
 * <h2>说明
 * <p>对微信 HTTP 请求服务做进一步封装，用于项目内部调用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-23
 * @since 1.0.0
 */
@Service
@Slf4j
public class WeixinService {
    private final WeixinHttpService weixinHttpService;

    private final WeixinTokenService weixinTokenService;

    public WeixinService(WeixinHttpService weixinHttpService, WeixinTokenService weixinTokenService) {
        this.weixinHttpService = weixinHttpService;
        this.weixinTokenService = weixinTokenService;
    }

    /**
     * 通过从微信小程序中获取的 code 换取用户对应的 openid
     *
     * @param code 微信小程序端通过 wx.login 获取的 code
     *
     * @since 1.0.0
     */
    @SneakyThrows
    public String getOpenidByCode(String code) {
        return weixinHttpService
            .code2Session(code)
            .getOpenid();
    }

    /**
     * 生成小程序码
     *
     * @param page  页面 page，根路径前不要填加 /，不能携带参数（参数请放在scene字段里）
     * @param scene 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符
     * @param width 二维码的宽度，单位 px，最小 280px，最大 1280px
     *
     * @since 1.1.0
     */
    @SneakyThrows
    public byte[] generateWxacode(String page, String scene, int width) {
        String token = weixinTokenService.getToken();
        return weixinHttpService.getUnlimitedWxacode(token, page, scene, width);
    }
}
