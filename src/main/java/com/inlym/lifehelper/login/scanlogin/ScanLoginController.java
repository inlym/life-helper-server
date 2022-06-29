package com.inlym.lifehelper.login.scanlogin;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.login.scanlogin.pojo.LoginCredentialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
     * @since 1.3.0
     */
    @GetMapping("/login/credential")
    public LoginCredentialVO getCredential(@ClientIp String ip) {
        return scanLoginService.getCredential(ip);
    }
}
