package com.inlym.lifehelper.login.qrcode.controller;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.login.qrcode.entity.LoginTicket;
import com.inlym.lifehelper.login.qrcode.model.LoginByQrCodeDTO;
import com.inlym.lifehelper.login.qrcode.model.LoginByQrCodeResultVO;
import com.inlym.lifehelper.login.qrcode.model.LoginQrCode;
import com.inlym.lifehelper.login.qrcode.model.ScanLoginQrCodeResultVO;
import com.inlym.lifehelper.login.qrcode.service.QrcodeLoginService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 扫码登录控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@RestController
@RequiredArgsConstructor
@Validated
public class QrcodeLoginController {
    private final QrcodeLoginService qrcodeLoginService;

    /**
     * 被扫码端获取用于扫码登录的资源
     *
     * <h2>操作端
     * <p>被扫码端（Web）
     *
     * @param ip IP 地址
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    @GetMapping("/login/qrcode")
    public LoginQrCode getLoginQrCode(@ClientIp String ip) {
        return qrcodeLoginService.create(ip);
    }

    /**
     * 登录操作
     *
     * <h2>操作端
     * <p>被扫码端（Web）
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    @PostMapping("/login/qrcode")
    public LoginByQrCodeResultVO loginByQrCode(@Valid @RequestBody LoginByQrCodeDTO dto) {
        String ticketId = dto.getId();
        return qrcodeLoginService.loginByQrCode(ticketId);
    }

    /**
     * 扫码端（小程序）操作 - 扫码，同时获取被扫码端信息
     *
     * <h2>操作端
     * <p>扫码端（小程序）
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    @GetMapping("/login-ticket/{id}")
    @UserPermission
    public ScanLoginQrCodeResultVO scan(@NotEmpty @PathVariable("id") String ticketId) {
        return qrcodeLoginService.scan(ticketId);
    }

    /**
     * 扫码端（小程序）操作 - 确认登录
     *
     * <h2>操作端
     * <p>扫码端（小程序）
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    @PutMapping("/login-ticket/{id}")
    @UserPermission
    public LoginTicket confirm(@NotEmpty @PathVariable("id") String ticketId, @UserId long userId) {
        return qrcodeLoginService.confirm(ticketId, userId);
    }
}
