package com.inlym.lifehelper.account.login.qrcode.service;

import com.inlym.lifehelper.account.login.qrcode.constant.LoginTicketStatus;
import com.inlym.lifehelper.account.login.qrcode.entity.LoginTicket;
import com.inlym.lifehelper.account.login.qrcode.model.LoginByQrCodeResultVO;
import com.inlym.lifehelper.account.login.qrcode.model.LoginQrCode;
import com.inlym.lifehelper.account.login.qrcode.model.ScanLoginQrCodeResultVO;
import com.inlym.lifehelper.common.auth.simpletoken.SimpleTokenService;
import com.inlym.lifehelper.common.exception.UnpredictableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 扫码登录服务
 *
 * <h2>主要用途
 * <p>直接对接控制器处理逻辑。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@Service
@RequiredArgsConstructor
public class QrcodeLoginService {
    private final LoginTicketService loginTicketService;

    private final SimpleTokenService simpleTokenService;

    /**
     * 创建环节
     *
     * @param ip 客户端 IP 地址
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    public LoginQrCode create(String ip) {
        LoginTicket ticket = loginTicketService.create(ip);

        return LoginQrCode.builder().id(ticket.getId()).url(ticket.getUrl()).build();
    }

    /**
     * 扫码环节
     *
     * <h2>说明
     * <p>换个角度可以理解为这里是用于“扫码端”获取“被扫码端”的基本信息。
     *
     * @param ticketId 扫码登录凭据 ID
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    public ScanLoginQrCodeResultVO scan(String ticketId) {
        LoginTicket ticket = loginTicketService.scan(ticketId);

        return ScanLoginQrCodeResultVO.builder().ip(ticket.getIp()).location(ticket.getLocation()).build();
    }

    /**
     * 确认登录环节
     *
     * @param ticketId 扫码登录凭据 ID
     * @param userId   用户 ID
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    public LoginTicket confirm(String ticketId, long userId) {
        LoginTicket ticket = loginTicketService.confirm(ticketId, userId);

        return LoginTicket.builder().id(ticket.getId()).build();
    }

    /**
     * 被扫码端的登录操作
     *
     * <h2>说明
     * <p>该方法会被高频调用。
     *
     * @param ticketId 扫码登录凭据 ID
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    public LoginByQrCodeResultVO loginByQrCode(String ticketId) {
        LoginTicket ticket = loginTicketService.getOrThrow(ticketId);
        LoginTicketStatus status = ticket.getStatus();
        LoginByQrCodeResultVO vo = new LoginByQrCodeResultVO();

        if (status == LoginTicketStatus.CREATED) {
            vo.setStatus(1);
        } else if (status == LoginTicketStatus.SCANNED) {
            vo.setStatus(2);
        } else if (status == LoginTicketStatus.CONFIRMED) {
            loginTicketService.consume(ticketId);
            vo.setStatus(3);
            vo.setCertificate(simpleTokenService.generateIdentityCertificate(ticket.getUserId()));
        } else if (status == LoginTicketStatus.CONSUMED) {
            vo.setStatus(4);
        } else {
            throw new UnpredictableException("出现了未定义的 LoginTicketStatus 值");
        }

        return vo;
    }
}
