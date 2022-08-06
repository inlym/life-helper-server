package com.inlym.lifehelper.login.qrcode;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.login.qrcode.pojo.QrCodeTicketDTO;
import com.inlym.lifehelper.login.qrcode.pojo.QrCodeTicketVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 扫码登录控制器
 *
 * <h2>主要用途
 * <p>管理“扫码登录”的相关接口
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/6
 * @since 1.3.0
 **/
@RestController
@RequiredArgsConstructor
public class QrCodeLoginController {
    private final QrCodeLoginService qrCodeLoginService;

    /**
     * （被扫码端）获取用于扫码登录的相关资源
     *
     * @since 1.3.0
     */
    @GetMapping("/login/qrcode")
    public QrCodeTicketVO getQrCodeTicketVO(@ClientIp String ip) {
        return qrCodeLoginService.getQrCodeTicketVO(ip);
    }

    /**
     * （被扫码端）检查扫码登录状态
     *
     * <h2>主要逻辑
     * <p>二维码被扫码则告知该状态，若已确认，则直接返回鉴权凭证。
     *
     * @since 1.3.0
     */
    @PostMapping("/login/qrcode")
    public QrCodeTicketVO loginByQrCodeTicket(@Valid @RequestBody QrCodeTicketDTO dto) {
        String id = dto.getId();
        return qrCodeLoginService.checkAndGrant(id);
    }

    /**
     * （扫码端）进行“扫码”操作
     *
     * @since 1.3.0
     */
    @PutMapping(value = "/login/qrcode", params = "operator=scan")
    @UserPermission
    public QrCodeTicketVO scan(@Valid @RequestBody QrCodeTicketDTO dto) {
        String id = dto.getId();
        return qrCodeLoginService.scan(id);
    }

    /**
     * （扫码端）进行“确认登录”操作
     *
     * @since 1.3.0
     */
    @PutMapping(value = "/login/qrcode", params = "operator=confirm")
    @UserPermission
    public QrCodeTicketVO confirm(@Valid @RequestBody QrCodeTicketDTO dto, @UserId int userId) {
        String id = dto.getId();
        return qrCodeLoginService.confirm(id, userId);
    }
}
