package com.inlym.lifehelper.login.weixinlogin;

import com.inlym.lifehelper.common.auth.core.AuthenticationCredential;
import com.inlym.lifehelper.common.auth.jwt.JwtService;
import com.inlym.lifehelper.external.weixin.WeixinService;
import com.inlym.lifehelper.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 微信登录服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-23
 * @since 1.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class WeixinLoginService {
    private final UserService userService;

    private final WeixinService weixinService;

    private final JwtService jwtService;

    /**
     * 通过微信获取的 code 登录，并返回登录凭证
     *
     * @param code 微信小程序中获取的 code
     *
     * @return JWT 字符串
     *
     * @since 1.0.0
     */
    public String loginByCode(String code) {
        String openid = weixinService.getOpenidByCode(code);
        int userId = userService.getUserIdByOpenid(openid);
        String token = jwtService.createTokenForUser(userId);

        log.info("[用户登录] code={}, openid={}, userId={}", code, openid, userId);

        return token;
    }

    /**
     * 通过微信获取的 code 登录
     *
     * @param code 微信小程序中获取的 code
     *
     * @since 1.3.0
     */
    public AuthenticationCredential loginByCode2(String code) {
        String openid = weixinService.getOpenidByCode(code);
        int userId = userService.getUserIdByOpenid(openid);
        AuthenticationCredential ac = jwtService.createAuthenticationCredential(userId);

        log.info("[用户登录] code={}, openid={}, userId={}", code, openid, userId);

        return ac;
    }
}
