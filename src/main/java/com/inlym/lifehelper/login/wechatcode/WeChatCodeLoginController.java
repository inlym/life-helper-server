package com.inlym.lifehelper.login.wechatcode;

import com.inlym.lifehelper.common.auth.core.AuthenticationCredential;
import com.inlym.lifehelper.login.wechatcode.pojo.WeChatCodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 类名称
 *
 * <h2>说明
 * <p>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/4
 * @since 1.3.0
 **/
@RestController
@RequiredArgsConstructor
public class WeChatCodeLoginController {
    private final WeChatCodeLoginService weChatCodeLoginService;

    /**
     * 微信小程序登录
     *
     * @since 1.3.0
     */
    @PostMapping("/login/wechat")
    public AuthenticationCredential loginByCode(@Valid @RequestBody WeChatCodeDTO dto) {
        return weChatCodeLoginService.loginByCode(dto.getCode());
    }
}
