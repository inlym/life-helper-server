package com.inlym.lifehelper.login.scanlogin;

import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.login.scanlogin.credential.LoginCredential;
import com.inlym.lifehelper.login.scanlogin.credential.LoginCredentialService;
import com.inlym.lifehelper.login.scanlogin.pojo.LoginCredentialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 扫码登录服务
 *
 * <h2>说明
 * <p>在这个服务中处理扫码登录逻辑。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/27
 * @since 1.x.x
 **/
@Service
@RequiredArgsConstructor
public class ScanLoginService {
    private final LoginCredentialService loginCredentialService;

    private final OssService ossService;

    /**
     * 获取登录凭证
     *
     * @since 1.3.0
     */
    public LoginCredentialVO getCredential() {
        loginCredentialService.batchCreateIfNeedAsync();

        LoginCredential credential = loginCredentialService.offer();
        LoginCredentialVO vo = new LoginCredentialVO();
        vo.setId(credential.getId());
        vo.setImageUrl(ossService.concatUrl(credential.getPath()));

        return vo;
    }
}
