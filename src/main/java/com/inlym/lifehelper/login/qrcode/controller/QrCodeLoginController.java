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
        return qrCodeLoginService.createQrCodeTicket();
    }

    /**
     * 登录操作
     *
     * <h2>操作端
     * <p>被扫码端（Web）
     *
     * <h2>可能的业务异常
     * <p>由于客户端采用“轮询”请求，可能中途凭据已使用变成了已失效状态，对于该情况的处理是：不报错，使用响应的 `invalid` 字段表示。
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
    public QrCodeTicketVO operate(@UserId long userId, @PathVariable("id") String ticketId) {
        return qrCodeLoginService.confirm(ticketId, userId);
    }
}
