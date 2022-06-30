package com.inlym.lifehelper.login.scanlogin;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.login.scanlogin.pojo.LoginTicketVO;
import com.inlym.lifehelper.login.scanlogin.pojo.ScanLoginDTO;
import com.inlym.lifehelper.login.scanlogin.pojo.ScanLoginResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 扫码登录控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/27
 * @since 1.3.0
 **/
@RestController
@RequiredArgsConstructor
public class ScanLoginController {
    private final ScanLoginService scanLoginService;

    /**
     * 获取一个扫码登录凭证
     *
     * <h2>说明
     * <p>用于 Web 端
     *
     * @since 1.3.0
     */
    @GetMapping("/login/credential")
    public LoginTicketVO getCredential(@ClientIp String ip) {
        return scanLoginService.getCredential(ip);
    }

    /**
     * 获取扫码登录结果
     *
     * <h2>说明
     * <p>用于 Web 端
     *
     * @since 1.3.0
     */
    @PostMapping("/login/credential")
    public ScanLoginResultVO loginByCredential(@RequestBody ScanLoginDTO dto) {
        return scanLoginService.checkCredential(dto.getId());
    }
}
