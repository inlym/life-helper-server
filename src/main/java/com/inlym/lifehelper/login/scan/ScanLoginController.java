package com.inlym.lifehelper.login.scan;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.validation.SimpleUUID;
import com.inlym.lifehelper.login.scan.exception.ScanLoginTicketNotFoundException;
import com.inlym.lifehelper.login.scan.pojo.ScanLoginDTO;
import com.inlym.lifehelper.login.scan.pojo.ScanLoginOperationDTO;
import com.inlym.lifehelper.login.scan.pojo.ScanLoginResultVO;
import com.inlym.lifehelper.login.scan.pojo.ScanLoginTicketVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 扫码登录控制器
 *
 * <h2>主要用途
 * <p>管理「扫码登录」的相关流程。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/5
 * @since 1.9.0
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class ScanLoginController {
    private static final String SCAN = "scan";

    private static final String CONFIRM = "confirm";

    private final ScanLoginService scanLoginService;

    /**
     * 获取用于「扫码登录」的小程序码信息
     *
     * <h2>操作端
     * <p>被扫码端（Web）
     *
     * @since 1.9.0
     */
    @GetMapping("/login/qrcode")
    public ScanLoginTicketVO getQrcode(@ClientIp String ip) {
        return scanLoginService.getQrcode(ip);
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
     * @since 1.9.0
     */
    @PostMapping("/login/qrcode")
    public ScanLoginResultVO loginByQrcode(@Valid @RequestBody ScanLoginDTO dto) {
        String id = dto.getId();
        return scanLoginService.getScanLoginResult(id);
    }

    /**
     * 扫码端（小程序）操作
     *
     * <h2>操作端
     * <p>扫码端（小程序）
     */
    @PutMapping("/login/qrcode/{id}")
    @UserPermission
    public ScanLoginTicketVO scan(@UserId int userId, @SimpleUUID @PathVariable String id, @Valid @RequestBody ScanLoginOperationDTO dto) {
        String type = dto.getType();

        if (SCAN.equals(type)) {
            return scanLoginService.scan(id);
        } else if (CONFIRM.equals(type)) {
            return scanLoginService.confirm(id, userId);
        } else {
            throw ScanLoginTicketNotFoundException.of(id);
        }
    }
}
