package com.inlym.lifehelper.login.scanlogin2;

import com.inlym.lifehelper.common.auth.jwt.JwtService;
import com.inlym.lifehelper.login.scanlogin2.pojo.QrcodeCredential;
import com.inlym.lifehelper.login.scanlogin2.pojo.ScanLoginResult;
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
     * 创建一个新的凭证
     *
     * @param ip 发起者 IP 地址
     *
     * @since 1.1.0
     */
    public ScanLoginResult createCredential(String ip) {
        QrcodeCredential qc = qrcodeCredentialService.create(ip);

        ScanLoginResult bo = new ScanLoginResult();
        bo.setTicket(qc.getTicket());
        bo.setUrl(qc.getUrl());

        return bo;
    }

    /**
     * 查询对应凭证编码的状态，并根据状态返回对应的数据
     *
     * @param ticket 凭证编码
     * @param ip     发起者 IP 地址
     *
     * @since 1.1.0
     */
    public ScanLoginResult checkTicket(String ticket, String ip) {
        QrcodeCredential qc = qrcodeCredentialService.get(ticket);

        // 根据不同的状态，执行不同的操作
        if (qc.getStatus() == QrcodeCredential.Status.INVALID || !qc
            .getIp()
            .equals(ip)) {
            return ScanLoginResult.invalid();
        } else if (qc.getStatus() == QrcodeCredential.Status.CREATED) {
            return ScanLoginResult.fromStatus(qc.getStatus());
        } else if (qc.getStatus() == QrcodeCredential.Status.SCANNED) {
            return ScanLoginResult.fromStatus(qc.getStatus());
        } else if (qc.getStatus() == QrcodeCredential.Status.CONFIRMED) {
            qrcodeCredentialService.consume(qc.getTicket());

            int userId = qc.getUserId();
            String token = jwtService.createTokenForUser(userId);

            ScanLoginResult bo = new ScanLoginResult();
            bo.setStatus(qc.getStatus());
            bo.setToken(token);

            return bo;
        } else {
            log.error("未知的状态：{}", qc.getStatus());
            return ScanLoginResult.invalid();
        }
    }

    /**
     * 进行扫码操作（仅扫码，未点击确定）
     *
     * @param ticket 凭证编码
     * @param userId 用户 ID
     *
     * @since 1.1.0
     */
    public ScanLoginResult scan(String ticket, int userId) {
        QrcodeCredential qc = qrcodeCredentialService.scan(ticket, userId);

        if (qc.getStatus() == QrcodeCredential.Status.INVALID) {
            return ScanLoginResult.invalid();
        }

        ScanLoginResult result = new ScanLoginResult();
        result.setStatus(qc.getStatus());
        result.setIp(qc.getIp());
        result.setRegion(qc.getRegion());

        return result;
    }

    /**
     * 进行确认登录操作（仅扫码，未点击确定）
     *
     * @param ticket 凭证编码
     * @param userId 用户 ID
     *
     * @since 1.1.0
     */
    public ScanLoginResult confirm(String ticket, int userId) {
        QrcodeCredential qc = qrcodeCredentialService.confirm(ticket, userId);

        if (qc.getStatus() == QrcodeCredential.Status.INVALID) {
            return ScanLoginResult.invalid();
        }

        return ScanLoginResult.fromStatus(qc.getStatus());
    }
}
