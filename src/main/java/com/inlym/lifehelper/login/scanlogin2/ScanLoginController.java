package com.inlym.lifehelper.login.scanlogin2;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.login.scanlogin2.pojo.QrcodeTicketDTO;
import com.inlym.lifehelper.login.scanlogin2.pojo.ScanLoginResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 扫码登录控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/13
 * @since 1.1.0
 **/
@RestController
@Validated
public class ScanLoginController {
    private final ScanLoginService scanLoginService;

    public ScanLoginController(ScanLoginService scanLoginService) {
        this.scanLoginService = scanLoginService;
    }

    /**
     * 获取用于扫码登录的凭证信息
     *
     * <h2>说明
     * <p>用于 Web 端。
     *
     * @since 1.1.0
     */
    @GetMapping("/login/qrcode")
    public ScanLoginResult getQrcodeCredentialBO(@ClientIp String ip) {
        return scanLoginService.createCredential(ip);
    }

    /**
     * 扫码登录
     *
     * <h2>说明
     * <li>用于 Web 端。
     * <li>Web 端将会以 1次/秒 的速度查询登录状态，这是一个高频接口。
     *
     * @param dto 请求数据
     * @param ip  客户端 IP 地址
     *
     * @since 1.1.0
     */
    @PostMapping("/login/qrcode")
    public ScanLoginResult loginByTicket(@RequestBody @Validated QrcodeTicketDTO dto, @ClientIp String ip) {
        String ticket = dto.getTicket();
        return scanLoginService.checkTicket(ticket, ip);
    }

    /**
     * 小程序端进行 “扫码” 操作
     *
     * <h2>说明
     * <p>用于小程序端。
     *
     * @param dto 请求数据
     *
     * @since 1.1.0
     */
    @PutMapping(path = "/scan_login", params = "operator=scan")
    @UserPermission
    public ScanLoginResult scan(@RequestBody @Validated QrcodeTicketDTO dto, @UserId int userId) {
        String ticket = dto.getTicket();
        return scanLoginService.scan(ticket, userId);
    }

    /**
     * 小程序端进行 “确认登录” 操作
     *
     * <h2>说明
     * <p>用于小程序端。
     *
     * @param dto 请求数据
     *
     * @since 1.1.0
     */
    @PutMapping(path = "/scan_login", params = "operator=confirm")
    @UserPermission
    public ScanLoginResult confirm(@RequestBody @Validated QrcodeTicketDTO dto, @UserId int userId) {
        String ticket = dto.getTicket();
        return scanLoginService.confirm(ticket, userId);
    }
}
