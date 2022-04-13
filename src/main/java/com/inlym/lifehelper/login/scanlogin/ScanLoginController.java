package com.inlym.lifehelper.login.scanlogin;

import com.inlym.lifehelper.login.scanlogin.pojo.QrcodeCredentialBO;
import com.inlym.lifehelper.login.scanlogin.pojo.QrcodeTicketDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
     * @since 1.1.0
     */
    @GetMapping("/login/qrcode")
    public QrcodeCredentialBO getQrcodeCredentialBO() {
        return scanLoginService.createCredential();
    }

    /**
     * 扫码登录
     *
     * @since 1.1.0
     */
    @PostMapping("/login/qrcode")
    public QrcodeCredentialBO loginByTicket(@RequestBody @Validated QrcodeTicketDTO dto) {
        String ticket = dto.getTicket();
        return scanLoginService.checkTicket(ticket);
    }
}
