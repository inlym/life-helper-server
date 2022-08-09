package com.inlym.lifehelper.login.qrcode;

import com.inlym.lifehelper.common.auth.core.AuthenticationCredential;
import com.inlym.lifehelper.common.auth.jwt.JwtService;
import com.inlym.lifehelper.login.qrcode.entity.QrCodeTicket;
import com.inlym.lifehelper.login.qrcode.exception.InvalidQrCodeTicketException;
import com.inlym.lifehelper.login.qrcode.pojo.QrCodeTicketVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 扫码登录封装服务
 *
 * <h2>主要用途
 * <p>对接 {@link com.inlym.lifehelper.login.qrcode.pojo.QrCodeTicketVO} 控制器，封装相关方法。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/6
 * @since 1.3.0
 **/
@Service
@RequiredArgsConstructor
public class QrCodeLoginService {
    private final QrCodeTicketService qrCodeTicketService;

    private final JwtService jwtService;

    /**
     * 获取用于被扫码端扫码登录的相关资源
     *
     * @param ip 被扫码端的 IP 地址
     *
     * @since 1.3.0
     */
    public QrCodeTicketVO getQrCodeTicketVO(String ip) {
        QrCodeTicket qt = qrCodeTicketService.create();

        // 异步执行地区信息赋值
        qrCodeTicketService.setIpRegionAsync(qt.getId(), ip);

        return QrCodeTicketVO
            .builder()
            .id(qt.getId())
            .url(qt.getUrl())
            .build();
    }

    /**
     * 检查扫码登录凭据状态，如果已确认登录，则发放鉴权凭证
     *
     * @param id 扫码登录凭据 ID
     *
     * @since 1.3.0
     */
    public QrCodeTicketVO checkAndGrant(String id) {
        QrCodeTicket qt = qrCodeTicketService.getEntity(id);

        if (qt.getStatus() == QrCodeTicket.Status.CREATED) {
            return QrCodeTicketVO
                .builder()
                .id(id)
                .status(QrCodeTicket.Status.CREATED)
                .build();
        } else if (qt.getStatus() == QrCodeTicket.Status.SCANNED) {
            return QrCodeTicketVO
                .builder()
                .id(id)
                .status(QrCodeTicket.Status.SCANNED)
                .build();
        } else if (qt.getStatus() == QrCodeTicket.Status.CONFIRMED) {
            int userId = qt.getUserId();
            AuthenticationCredential ac = jwtService.createAuthenticationCredential(userId);

            // 表示已使用该凭据，后续执行销毁
            qrCodeTicketService.consume(id);

            return QrCodeTicketVO
                .builder()
                .id(id)
                .status(QrCodeTicket.Status.CONFIRMED)
                .credential(ac)
                .build();
        } else {
            throw InvalidQrCodeTicketException.fromId(id);
        }
    }

    /**
     * 进行“扫码”操作
     *
     * @param id 扫码登录凭据 ID
     *
     * @since 1.3.0
     */
    public QrCodeTicketVO scan(String id) {
        qrCodeTicketService.scan(id);
        QrCodeTicket qt = qrCodeTicketService.getEntity(id);

        return QrCodeTicketVO
            .builder()
            .id(id)
            .status(QrCodeTicket.Status.SCANNED)
            .ip(qt.getIp())
            .region(qt.getRegion())
            .build();
    }

    /**
     * 进行“确认登录”操作
     *
     * @param id 扫码登录凭据 ID
     *
     * @since 1.3.0
     */
    public QrCodeTicketVO confirm(String id, int userId) {
        qrCodeTicketService.confirm(id, userId);

        return QrCodeTicketVO
            .builder()
            .id(id)
            .status(QrCodeTicket.Status.CONFIRMED)
            .build();
    }
}
