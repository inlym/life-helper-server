package com.inlym.lifehelper.login.wechat.controller;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.common.auth.core.IdentityCertificate;
import com.inlym.lifehelper.extern.wechat.config.WeChatProperties;
import com.inlym.lifehelper.login.wechat.pojo.WeChatCodeDTO;
import com.inlym.lifehelper.login.wechat.service.WeChatLoginService;
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
 * @date 2024/2/15
 * @since 2.1.0
 **/
@RestController
@RequiredArgsConstructor
public class WeChatLoginController {
    private final WeChatLoginService weChatLoginService;

    private final WeChatProperties weChatProperties;

    /**
     * 微信小程序登录
     *
     * @param ip  客户端 IP 地址
     * @param dto 请求数据
     *
     * @since 2.1.0
     */
    @PostMapping("/login/wechat")
    public IdentityCertificate loginByCode(@ClientIp String ip, @Valid @RequestBody WeChatCodeDTO dto) {
        // 过渡阶段 appId 处理，后续需移除该处理
        if (dto.getAppId() == null) {
            dto.setAppId(weChatProperties
                             .getMainApp()
                             .getAppId());
        }

        return weChatLoginService.loginByCode(dto.getAppId(), dto.getCode(), ip);
    }
}
