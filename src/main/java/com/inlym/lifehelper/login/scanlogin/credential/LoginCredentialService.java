package com.inlym.lifehelper.login.scanlogin.credential;

import com.inlym.lifehelper.common.base.aliyun.oss.OssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.external.weixin.WeixinService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 登录凭证服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/27
 * @since 1.3.0
 **/
@Service
@RequiredArgsConstructor
public class LoginCredentialService {
    /** 批量生成数量 */
    private static final int BATCH_CREATE_QUANTITY = 5;

    /** 未发放的凭证编码列表 */
    private static final String NOT_OFFERED_CREDENTIAL_LIST = "database:login_credential:not_offered";

    private final LoginCredentialRepository repository;

    private final WeixinService weixinService;

    private final OssService ossService;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 创建一个新的登录凭证
     *
     * @since 1.3.0
     */
    public LoginCredential create() {
        String id = UUID
            .randomUUID()
            .toString()
            .toLowerCase()
            .replaceAll("-", "");

        String path = OssDir.WXACODE + "/" + id + ".png";

        LoginCredential lc = new LoginCredential();
        lc.setId(id);
        lc.setPath(path);
        lc.setUrl(ossService.concatUrl(path));
        lc.setStatus(LoginCredential.Status.CREATED);
        lc.setCreateTime(System.currentTimeMillis());

        // 小程序码宽度，单位：px
        int width = 430;

        // 小程序的“扫码登录页”路径
        String page = "pages/scan/login";

        // 从微信服务器获取小程序码
        byte[] bytes = weixinService.generateWxacode(page, lc.getId(), width);

        // 将图片上传 OSS
        ossService.upload(path, bytes);

        repository.save(lc);

        return lc;
    }

    /**
     * 根据需要异步生成登录凭证
     *
     * @since 1.3.0
     */
    @Async
    public void batchCreateIfNeedAsync() {
        Long size = stringRedisTemplate
            .opsForList()
            .size(NOT_OFFERED_CREDENTIAL_LIST);

        assert size != null;
        if (size < BATCH_CREATE_QUANTITY) {
            for (int i = 0; i < BATCH_CREATE_QUANTITY; i++) {
                LoginCredential credential = create();
                stringRedisTemplate
                    .opsForList()
                    .leftPush(NOT_OFFERED_CREDENTIAL_LIST, credential.getId());
            }
        }
    }

    /**
     * 发放一个登录凭证
     *
     * @since 1.3.0
     */
    public LoginCredential offer() {
        String id = stringRedisTemplate
            .opsForList()
            .rightPop(NOT_OFFERED_CREDENTIAL_LIST);

        LoginCredential lc;

        if (id != null) {
            lc = repository
                .findById(id)
                .orElseThrow();
        } else {
            lc = create();
        }

        if (lc.getStatus() != LoginCredential.Status.CREATED) {
            throw InvalidLoginCredentialException.defaultMessage();
        }

        lc.setStatus(LoginCredential.Status.OFFERED);
        lc.setOfferTime(System.currentTimeMillis());
        repository.save(lc);

        return lc;
    }

    public LoginCredential scan(LoginCredential loginCredential) {
        LoginCredential lc = repository
            .findById(loginCredential.getId())
            .orElseThrow(InvalidLoginCredentialException::defaultMessage);

        if (lc.getStatus() != LoginCredential.Status.OFFERED) {
            throw InvalidLoginCredentialException.defaultMessage();
        }

        lc.setStatus(LoginCredential.Status.SCANNED);
        lc.setScanTime(System.currentTimeMillis());

        repository.save(lc);

        return lc;
    }

    public LoginCredential confirm(LoginCredential loginCredential) {
        LoginCredential lc = repository
            .findById(loginCredential.getId())
            .orElseThrow(InvalidLoginCredentialException::defaultMessage);

        if (lc.getStatus() != LoginCredential.Status.SCANNED && lc.getStatus() != LoginCredential.Status.OFFERED) {
            throw InvalidLoginCredentialException.defaultMessage();
        }

        lc.setStatus(LoginCredential.Status.CONFIRMED);
        lc.setConfirmTime(System.currentTimeMillis());
        lc.setUserId(loginCredential.getUserId());

        repository.save(lc);

        return lc;
    }
}
