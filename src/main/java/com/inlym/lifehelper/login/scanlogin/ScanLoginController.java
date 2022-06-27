package com.inlym.lifehelper.login.scanlogin;

import com.inlym.lifehelper.login.scanlogin.credential.LoginCredential;
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

    @GetMapping("/login/credential")
    public LoginCredential getCredential() {
        return scanLoginService.getCredential();
    }
}
