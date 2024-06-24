package com.inlym.lifehelper.account.login.wechat.controller;

import com.inlym.lifehelper.account.login.wechat.model.WeChatCodeDTO;
import com.inlym.lifehelper.account.login.wechat.service.WeChatLoginService;
import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.common.auth.core.IdentityCertificate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信登录控制器
 *
 * <h2>主要用途
 * <p>在微信内部（小程序、公众号网页等）登录的，均放在当前控制器下。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/10
 * @since 2.3.0
 **/
@RestController
@RequiredArgsConstructor
public class WeChatLoginController {
    private final WeChatLoginService weChatLoginService;

    /**
     * 微信登录
     *
     * @param ip  客户端 IP 地址
     * @param dto 请求数据
     *
     * @date 2024/6/10
     * @since 2.3.0
     */
    @PostMapping("/login/wechat")
    public IdentityCertificate loginByCode(@ClientIp String ip, @Valid @RequestBody WeChatCodeDTO dto) {
        String appId = dto.getAppId();
        String code = dto.getCode();
        
        return weChatLoginService.loginByWeChatCode(appId, code, ip);
    }
}
