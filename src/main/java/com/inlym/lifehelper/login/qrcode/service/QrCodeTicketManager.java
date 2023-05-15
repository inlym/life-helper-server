package com.inlym.lifehelper.login.qrcode.service;

import com.inlym.lifehelper.login.qrcode.constant.QrCodeTicketStatus;
import com.inlym.lifehelper.login.qrcode.entity.QrCodeTicket;
import com.inlym.lifehelper.login.qrcode.event.QrCodeTicketConsumedEvent;
import com.inlym.lifehelper.login.qrcode.exception.QrCodeTicketNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 二维码登录凭据生命周期管理器
 *
 * <h2>主要用途
 * <p>管理二维码登录凭据的生命周期。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/15
 * @since 2.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class QrCodeTicketManager {
    private final QrCodeTicketRepository repository;

    private final LoginQrCodeGenerator loginQrCodeGenerator;

    private final ApplicationContext applicationContext;

    /**
     * 创建
     *
     * @date 2023/5/15
     * @since 2.0.0
     */
    public QrCodeTicket create() {
        String id = loginQrCodeGenerator.getOne();

        // 备注（2023.05.15）
        // 实际上这一步不应该放在这个方法内，但是由于这个方法被设定为异步方法，无法放在原有类中调用，
        // 如果要封装则需要额外增加一个服务类，有点累赘，因此直接放在这里，省事。
        loginQrCodeGenerator.batchGenerateIfNeedAsync();

        QrCodeTicket ticket = QrCodeTicket
                                  .builder()
                                  .id(id)
                                  .status(QrCodeTicketStatus.CREATED)
                                  .createdTime(LocalDateTime.now())
                                  .build();

        repository.save(ticket);
        return ticket;
    }

    /**
     * 「扫码」操作
     *
     * @param ticketId 二维码凭据 ID
     *
     * @date 2023/5/15
     * @since 2.0.0
     */
    public QrCodeTicket scan(String ticketId) {
        Optional<QrCodeTicket> result = repository.findById(ticketId);
        if (result.isEmpty()) {
            throw new QrCodeTicketNotFoundException(ticketId);
        }

        QrCodeTicket ticket = result.get();

        // 只有「已创建未扫码」状态需要处理，其他状态均不做任何处理
        if (ticket.getStatus() == QrCodeTicketStatus.CREATED) {
            ticket.setStatus(QrCodeTicketStatus.SCANNED);
            ticket.setScannedTime(LocalDateTime.now());
            repository.save(ticket);
        }

        return ticket;
    }

    /**
     * 「确认登录」操作
     *
     * @param ticketId 二维码凭据 ID
     * @param userId   用户 ID
     *
     * @date 2023/5/15
     * @since 2.0.0
     */
    public QrCodeTicket confirm(String ticketId, int userId) {
        Optional<QrCodeTicket> result = repository.findById(ticketId);
        if (result.isEmpty()) {
            throw new QrCodeTicketNotFoundException(ticketId);
        }

        QrCodeTicket ticket = result.get();

        // 只有「已扫码未确认」状态需要处理，其他状态均不做任何处理
        if (ticket.getStatus() == QrCodeTicketStatus.SCANNED) {
            ticket.setStatus(QrCodeTicketStatus.CONFIRMED);
            ticket.setUserId(userId);
            ticket.setConfirmedTime(LocalDateTime.now());
            repository.save(ticket);
        }

        return ticket;
    }

    /**
     * 「消费」二维码凭据
     *
     * @param ticketId 二维码凭据 ID
     *
     * @date 2023/5/15
     * @since 2.0.0
     */
    public QrCodeTicket consume(String ticketId) {
        Optional<QrCodeTicket> result = repository.findById(ticketId);
        if (result.isEmpty()) {
            throw new QrCodeTicketNotFoundException(ticketId);
        }

        QrCodeTicket ticket = result.get();
        if (ticket.getStatus() == QrCodeTicketStatus.CONFIRMED) {
            ticket.setStatus(QrCodeTicketStatus.CONSUMED);
            // 发布「二维码凭据消费事件」
            applicationContext.publishEvent(new QrCodeTicketConsumedEvent(ticket));
            repository.deleteById(ticketId);
        }

        return ticket;
    }
}
