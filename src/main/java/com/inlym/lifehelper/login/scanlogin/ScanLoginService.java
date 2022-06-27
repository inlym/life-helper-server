package com.inlym.lifehelper.login.scanlogin;

import com.inlym.lifehelper.login.scanlogin.credential.LoginCredential;
import com.inlym.lifehelper.login.scanlogin.credential.LoginCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 类名称
 *
 * <h2>说明
 * <p>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/27
 * @since 1.x.x
 **/
@Service
@RequiredArgsConstructor
public class ScanLoginService {
    private final LoginCredentialService loginCredentialService;

    public LoginCredential getCredential() {
        return loginCredentialService.getOne();
    }
}
