package com.inlym.lifehelper.login.qrcode.service;

import com.inlym.lifehelper.location.position.LocationService;
import com.inlym.lifehelper.login.qrcode.constant.LoginTicketStatus;
import com.inlym.lifehelper.login.qrcode.entity.LoginTicket;
import com.inlym.lifehelper.login.qrcode.event.LoginTicketConsumedEvent;
import com.inlym.lifehelper.login.qrcode.event.LoginTicketCreatedEvent;
import com.inlym.lifehelper.login.qrcode.exception.LoginTicketNotFoundException;
import com.inlym.lifehelper.login.qrcode.model.LoginQrCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 登录凭据管理服务
 *
 * <h2>主要用途
 * <p>管理登录凭据生命周期
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginTicketService {
    private final LoginTicketRepository loginTicketRepository;

    private final LoginQrCodeService loginQrCodeService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final LocationService locationService;

    /**
     * 创建扫码登录凭据
     *
     * @param ip 客户端 IP 地址
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    public LoginTicket create(String ip) {
        LoginQrCode loginQrCode = loginQrCodeService.getOne();

        LoginTicket ticket = LoginTicket
            .builder()
            .id(loginQrCode.getId())
            .ip(ip)
            .url(loginQrCode.getUrl())
            .status(LoginTicketStatus.CREATED)
            .createTime(LocalDateTime.now())
            .build();
        loginTicketRepository.save(ticket);
        log.trace("创建扫码登录凭据, ticket={}", ticket);

        // 发布扫码登录凭据创建事件
        applicationEventPublisher.publishEvent(new LoginTicketCreatedEvent(ticket));

        return ticket;
    }

    /**
     * 监听扫码登录凭据创建事件
     *
     * <h2>主要用途
     * <p>这里使用事件监听而不是在原方法种处理，是为了将这一步异步处理，加快前一步返回响应的速度。
     *
     * @param event 扫码登录凭据创建事件
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    @EventListener(LoginTicketCreatedEvent.class)
    @Async
    public void listenLoginTicketCreatedEvent(LoginTicketCreatedEvent event) {
        LoginTicket ticket = event.getTicket();

        // 获取并存储 IP 定位对应的位置信息
        String locationName = locationService.getIpLocationName(ticket.getIp());
        LoginTicket ticketNow = getOrThrow(ticket.getId());
        ticketNow.setLocation(locationName);
        loginTicketRepository.save(ticketNow);
        log.trace("扫码登录凭据赋值定位信息，id={}, ip={}, location={}", ticketNow.getId(), ticketNow.getIp(), ticketNow.getLocation());
    }

    /**
     * 通过 ID 查找（若未找到责报错）
     *
     * @param ticketId 扫码登录凭据 ID
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    public LoginTicket getOrThrow(String ticketId) {
        Optional<LoginTicket> result = loginTicketRepository.findById(ticketId);
        if (result.isEmpty()) {
            String message = "未找到对应的扫码登录凭据，id=" + ticketId;
            log.trace(message);
            throw new LoginTicketNotFoundException(message);
        }
        log.trace("获取到的扫码登录凭据为：{}", result.get());
        return result.get();
    }

    /**
     * 「扫码」操作
     *
     * @param ticketId 扫码登录凭据 ID
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    public LoginTicket scan(String ticketId) {
        LoginTicket ticket = getOrThrow(ticketId);

        // 一般来讲，从创建到扫码的延迟，中间应该已经处理完，这里加一道保险
        if (ticket.getLocation() == null) {
            ticket.setLocation(locationService.getIpLocationName(ticket.getIp()));
        }

        // 只有「已创建未扫码」状态需要处理，其他状态均不做任何处理
        if (ticket.getStatus() == LoginTicketStatus.CREATED) {
            ticket.setStatus(LoginTicketStatus.SCANNED);
            ticket.setScanTime(LocalDateTime.now());
            loginTicketRepository.save(ticket);
            log.trace("扫码登录完成[扫码]操作，id={}", ticketId);
        }

        return ticket;
    }

    /**
     * 「确认登录」操作
     *
     * @param ticketId 扫码登录凭据 ID
     * @param userId   用户 ID
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    public LoginTicket confirm(String ticketId, long userId) {
        LoginTicket ticket = getOrThrow(ticketId);

        // 只有「已扫码未确认」状态需要处理，其他状态均不做任何处理
        if (ticket.getStatus() == LoginTicketStatus.SCANNED) {
            ticket.setStatus(LoginTicketStatus.CONFIRMED);
            ticket.setConfirmeTime(LocalDateTime.now());
            ticket.setUserId(userId);
            loginTicketRepository.save(ticket);
            log.trace("扫码登录完成[确认登录]操作，id={}", ticketId);
        }

        return ticket;
    }

    /**
     * 消费扫码登录凭据
     *
     * @param ticketId 扫码登录凭据 ID
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    public void consume(String ticketId) {
        LoginTicket ticket = getOrThrow(ticketId);

        if (ticket.getStatus() == LoginTicketStatus.CONFIRMED) {
            ticket.setStatus(LoginTicketStatus.CONSUMED);
            ticket.setConsumeTime(LocalDateTime.now());
            loginTicketRepository.save(ticket);
            log.trace("扫码登录凭据完成[被消费]操作，id={}", ticketId);

            // 发布消费事件
            applicationEventPublisher.publishEvent(new LoginTicketConsumedEvent(ticket));
            // 备注
            // 原本后续还有一步为在 Redis 中删除实体，改为不删，让它到期自动删除
        }
    }
}
