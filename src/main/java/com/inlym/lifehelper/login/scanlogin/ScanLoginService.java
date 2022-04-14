package com.inlym.lifehelper.login.scanlogin;

import com.inlym.lifehelper.common.auth.jwt.JwtService;
import com.inlym.lifehelper.login.scanlogin.pojo.QrcodeCredential;
import com.inlym.lifehelper.login.scanlogin.pojo.QrcodeCredentialBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 扫码登录服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/14
 * @since 1.1.0
 **/
@Service
@Slf4j
public class ScanLoginService {
    private final QrcodeCredentialService qrcodeCredentialService;

    private final JwtService jwtService;

    public ScanLoginService(QrcodeCredentialService qrcodeCredentialService, JwtService jwtService) {
        this.qrcodeCredentialService = qrcodeCredentialService;
        this.jwtService = jwtService;
    }

    /**
     * 将小程序码凭证对象转换为客户端使用的业务对象
     *
     * @param qc 小程序码凭证对象
     *
     * @since 1.1.0
     */
    private QrcodeCredentialBO convertToBO(QrcodeCredential qc) {
        QrcodeCredentialBO bo = new QrcodeCredentialBO();
        bo.setTicket(qc.getTicket());
        bo.setUrl(qc.getUrl());
        bo.setStatus(qc.getStatus());

        return bo;
    }

    /**
     * 创建一个新的凭证
     *
     * @since 1.1.0
     */
    public QrcodeCredentialBO createCredential() {
        QrcodeCredential qc = qrcodeCredentialService.create();

        QrcodeCredentialBO bo = new QrcodeCredentialBO();
        bo.setTicket(qc.getTicket());
        bo.setUrl(qc.getUrl());

        return bo;
    }

    /**
     * 查询对应凭证编码的状态，并根据状态返回对应的数据
     *
     * @param ticket 凭证编码
     *
     * @since 1.1.0
     */
    public QrcodeCredentialBO checkTicket(String ticket) {
        QrcodeCredential qc = qrcodeCredentialService.get(ticket);

        // 根据不同的状态，执行不同的操作
        if (qc.getStatus() == QrcodeCredential.CredentialStatus.INVALID) {
            return QrcodeCredentialBO.invalid();
        } else if (qc.getStatus() == QrcodeCredential.CredentialStatus.CREATED) {
            return QrcodeCredentialBO.fromStatus(qc.getStatus());
        } else if (qc.getStatus() == QrcodeCredential.CredentialStatus.SCANNED) {
            return QrcodeCredentialBO.fromStatus(qc.getStatus());
        } else if (qc.getStatus() == QrcodeCredential.CredentialStatus.CONFIRMED) {
            int userId = qc.getUserId();
            String token = jwtService.createTokenForUser(userId);

            QrcodeCredentialBO bo = new QrcodeCredentialBO();
            bo.setStatus(qc.getStatus());
            bo.setToken(token);

            return bo;
        } else {
            log.error("未知的状态：{}", qc.getStatus());
            return QrcodeCredentialBO.invalid();
        }
    }
}
