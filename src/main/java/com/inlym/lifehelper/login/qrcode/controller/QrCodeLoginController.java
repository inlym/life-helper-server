package com.inlym.lifehelper.login.qrcode.controller;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.validation.SimpleUUID;
import com.inlym.lifehelper.login.qrcode.model.QrCodeTicketVO;
import com.inlym.lifehelper.login.qrcode.model.ScanLoginDTO;
import com.inlym.lifehelper.login.qrcode.model.ScanLoginOperationDTO;
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
        return null;
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
    public QrCodeTicketVO loginByQrCode(@Valid @RequestBody ScanLoginDTO dto) {
        return null;
    }

    /**
     * 扫码端（小程序）操作
     *
     * <h2>操作端
     * <p>扫码端（小程序）
     */
    @PutMapping("/login/qrcode/{id}")
    @UserPermission
    public Object operate(@UserId int userId, @SimpleUUID @PathVariable String id, @Valid @RequestBody ScanLoginOperationDTO dto) {
        return null;
    }
}
