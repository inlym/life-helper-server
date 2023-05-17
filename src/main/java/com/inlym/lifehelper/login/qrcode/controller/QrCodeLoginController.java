package com.inlym.lifehelper.login.qrcode.controller;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.login.qrcode.model.QrCodeLoginDTO;
import com.inlym.lifehelper.login.qrcode.model.QrCodeLoginResultVO;
import com.inlym.lifehelper.login.qrcode.model.QrCodeTicketVO;
import com.inlym.lifehelper.login.qrcode.service.QrCodeLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 二维码登录控制器
 *
 * <h2>主要用途
 * <p>管理「扫码登录」的相关流程。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/16
 * @since 2.0.0
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class QrCodeLoginController {
    private final QrCodeLoginService qrCodeLoginService;

    /**
     * 获取用于「扫码登录」的小程序码信息
     *
     * <h2>操作端
     * <p>被扫码端（Web）
     *
     * @date 2023/5/16
     * @since 2.0.0
     */
    @GetMapping("/login/qrcode")
    public QrCodeTicketVO getQrCode() {
        return qrCodeLoginService.getQrCodeTicket();
    }

    /**
     * 登录操作
     *
     * <h2>操作端
     * <p>被扫码端（Web）
     *
     * <h2>主要逻辑
     * <p>扫码端（小程序）点击「确认登录」，这里就会发放登录凭证。
     *
     * @date 2023/5/16
     * @since 2.0.0
     */
    @PostMapping("/login/qrcode")
    public QrCodeLoginResultVO loginByQrCode(@Valid @RequestBody QrCodeLoginDTO dto) {
        String ticketId = dto.getId();
        return qrCodeLoginService.login(ticketId);
    }

    /**
     * 扫码端（小程序）操作 - 扫码
     *
     * <h2>操作端
     * <p>扫码端（小程序）
     *
     * @date 2023/5/17
     * @since 2.0.0
     */
    @PutMapping("/login/qrcode/scan/{id}")
    @UserPermission
    public QrCodeTicketVO operate(@PathVariable("id") String ticketId) {
        return qrCodeLoginService.scan(ticketId);
    }

    /**
     * 扫码端（小程序）操作 - 确认登录
     *
     * <h2>操作端
     * <p>扫码端（小程序）
     *
     * @date 2023/5/17
     * @since 2.0.0
     */
    @PutMapping("/login/qrcode/confirm/{id}")
    @UserPermission
    public QrCodeTicketVO operate(@UserId int userId, @PathVariable("id") String ticketId) {
        return qrCodeLoginService.confirm(ticketId, userId);
    }
}
