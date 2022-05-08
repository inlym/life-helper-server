package com.inlym.lifehelper.login.weixinlogin;

import com.auth0.jwt.JWT;
import com.inlym.lifehelper.login.weixinlogin.pojo.WeixinCodeDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author inlym
 * @date 2022-01-23
 **/
@RestController
@Validated
public class WeixinLoginController {
    private final WeixinLoginService weixinLoginService;

    public WeixinLoginController(WeixinLoginService weixinLoginService) {
        this.weixinLoginService = weixinLoginService;
    }

    /**
     * 微信登录
     *
     * @since 1.0.0
     */
    @PostMapping("/login/weixin")
    public Map<String, Object> loginByCode(@Validated @RequestBody WeixinCodeDTO dto) {
        String token = weixinLoginService.loginByCode(dto.getCode());

        Map<String, Object> map = new HashMap<>(16);
        map.put("token", token);

        // 登录凭证有效时间（到期时间的时间戳）
        map.put("expiration", JWT
            .decode(token)
            .getExpiresAt()
            .getTime());

        return map;
    }
}
