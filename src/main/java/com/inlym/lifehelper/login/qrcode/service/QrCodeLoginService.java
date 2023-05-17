package com.inlym.lifehelper.login.qrcode.service;

import com.inlym.lifehelper.common.auth.core.SecurityToken;
import com.inlym.lifehelper.common.auth.simpletoken.SimpleTokenService;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.login.qrcode.constant.QrCodeTicketStatus;
import com.inlym.lifehelper.login.qrcode.entity.QrCodeTicket;
import com.inlym.lifehelper.login.qrcode.model.QrCodeLoginResultVO;
import com.inlym.lifehelper.login.qrcode.model.QrCodeTicketVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 扫码登录服务
 *
 * <h2>主要用途
 * <p>对接控制器，整合内部服务。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/16
 * @since 2.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class QrCodeLoginService {
    private final LoginQrCodeGenerator loginQrCodeGenerator;

    private final QrCodeTicketManager qrCodeTicketManager;

    private final OssService ossService;

    private final SimpleTokenService simpleTokenService;

    /**
     * 获取用于扫码登录使用的二维码凭据信息
     *
     * @date 2023/5/16
     * @since 2.0.0
     */
    public QrCodeTicketVO getQrCodeTicket() {
        QrCodeTicket ticket = qrCodeTicketManager.create();

        String id = ticket.getId();
        String url = ossService.concatUrl(loginQrCodeGenerator.getOssPath(id));

        return QrCodeTicketVO
                   .builder()
                   .id(id)
                   .url(url)
                   .build();
    }

    /**
     * 「扫码」操作
     *
     * @param ticketId 二维码凭据 ID
     *
     * @date 2023/5/16
     * @since 2.0.0
     */
    public QrCodeTicketVO scan(String ticketId) {
        QrCodeTicket ticket = qrCodeTicketManager.scan(ticketId);

        return QrCodeTicketVO
                   .builder()
                   .id(ticketId)
                   .build();
    }

    /**
     * 「确认登录」操作
     *
     * @param ticketId 二维码凭据 ID
     * @param userId   用户 ID
     *
     * @date 2023/5/16
     * @since 2.0.0
     */
    public QrCodeTicketVO confirm(String ticketId, int userId) {
        QrCodeTicket ticket = qrCodeTicketManager.confirm(ticketId, userId);

        return QrCodeTicketVO
                   .builder()
                   .id(ticketId)
                   .build();
    }

    /**
     * 进行登录操作
     *
     * @param ticketId 二维码凭据 ID
     *
     * @date 2023/5/17
     * @since 2.0.0
     */
    public QrCodeLoginResultVO login(String ticketId) {
        QrCodeTicket ticket = qrCodeTicketManager.consume(ticketId);
        if (ticket.getStatus() == QrCodeTicketStatus.CONSUMED) {
            SecurityToken securityToken = simpleTokenService.generateSecurityToken(ticket.getUserId());
            return QrCodeLoginResultVO
                       .builder()
                       .scanned(true)
                       .logined(true)
                       .securityToken(securityToken)
                       .build();
        } else if (ticket.getStatus() == QrCodeTicketStatus.CONFIRMED || ticket.getStatus() == QrCodeTicketStatus.SCANNED) {
            return QrCodeLoginResultVO
                       .builder()
                       .scanned(true)
                       .logined(false)
                       .build();
        } else {
            return QrCodeLoginResultVO
                       .builder()
                       .scanned(false)
                       .logined(false)
                       .build();
        }
    }
}
