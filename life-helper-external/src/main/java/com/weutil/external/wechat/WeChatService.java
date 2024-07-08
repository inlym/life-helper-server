package com.weutil.external.wechat;

import com.weutil.external.wechat.pojo.UnlimitedQrCodeOptions;
import com.weutil.external.wechat.pojo.WeChatCode2SessionResponse;
import com.weutil.external.wechat.pojo.WeChatSession;
import com.weutil.external.wechat.service.WeChatHttpService;
import com.weutil.external.wechat.service.WeChatStableAccessTokenService;
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

    private final WeChatStableAccessTokenService weChatStableAccessTokenService;

    /**
     * 获取小程序会话
     *
     * @param code 微信小程序端通过 `wx.login` 获取的 code
     *
     * @since 2.1.0
     */
    public WeChatSession getSession(String appId, String code) {
        WeChatCode2SessionResponse response = weChatHttpService.code2Session(appId, code);

        return WeChatSession
                .builder()
                .openId(response.getOpenId())
                .unionId(response.getUnionId())
                .sessionKey(response.getSessionKey())
                .build();
    }

    /**
     * 生成小程序码
     *
     * @param options 配置选项
     *
     * @since 1.3.0
     */
    public byte[] getUnlimitedQrCode(String appId, UnlimitedQrCodeOptions options) {
        String token = weChatStableAccessTokenService.get(appId);
        return weChatHttpService.getUnlimitedQrCode(token, options);
    }
}
