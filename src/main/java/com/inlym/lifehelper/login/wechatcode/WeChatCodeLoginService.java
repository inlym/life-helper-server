package com.inlym.lifehelper.login.wechatcode;

import com.inlym.lifehelper.common.auth.core.SecurityToken;
import com.inlym.lifehelper.common.auth.simpletoken.SimpleTokenService;
import com.inlym.lifehelper.extern.wechat.WeChatService;
import com.inlym.lifehelper.user.account.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 微信小程序登录服务
 *
 * <h2>说明
 * <p>使用在小程序中调用 `wx.login` 方法获取 `code` 进行登录。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/4
 * @since 1.3.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class WeChatCodeLoginService {
    private final UserAccountService userAccountService;

    private final WeChatService weChatService;

    private final SimpleTokenService simpleTokenService;

    /**
     * 通过微信获取的 code 登录
     *
     * @param code 微信小程序中获取的 code
     *
     * @since 1.3.0
     */
    public SecurityToken loginByCode(String code) {
        String openid = weChatService.getOpenidByCode(code);
        long userId = userAccountService.getUserIdByOpenid(openid);
        SecurityToken securityToken = simpleTokenService.generateSecurityToken(userId);

        log.info("[小程序用户登录] code={}, openid={}, userId={}", code, openid, userId);

        return securityToken;
    }
}
