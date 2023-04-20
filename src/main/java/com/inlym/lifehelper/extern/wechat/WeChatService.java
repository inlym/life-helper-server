package com.inlym.lifehelper.extern.wechat;

import com.inlym.lifehelper.extern.wechat.pojo.UnlimitedQrCodeOptions;
import com.inlym.lifehelper.extern.wechat.service.WeChatAccessTokenService;
import com.inlym.lifehelper.extern.wechat.service.WeChatHttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 微信服务
 *
 * <h2>说明
 * <p>对微信服务端服务的封装，外部调用时只可调用当前类，不可直接调用 {@link WeChatHttpService} 等类。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/4
 * @since 1.3.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class WeChatService {
    private final WeChatHttpService weChatHttpService;

    private final WeChatAccessTokenService weChatAccessTokenService;

    /**
     * 通过从微信小程序中获取的 code 换取用户对应的 openid
     *
     * @param code 微信小程序端通过 `wx.login` 获取的 code
     *
     * @since 1.3.0
     */
    public String getOpenidByCode(String code) {
        return weChatHttpService
            .code2Session(code)
            .getOpenid();
    }

    /**
     * 生成小程序码
     *
     * @param options 配置选项
     *
     * @since 1.3.0
     */
    public byte[] getUnlimitedQrCode(UnlimitedQrCodeOptions options) {
        String token = weChatAccessTokenService.get();
        return weChatHttpService.getUnlimitedQrCode(token, options);
    }
}
