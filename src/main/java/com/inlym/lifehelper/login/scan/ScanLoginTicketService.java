package com.inlym.lifehelper.login.scan;

import com.inlym.lifehelper.location.position.LocationService;
import com.inlym.lifehelper.login.scan.constant.ScanLoginTicketStatus;
import com.inlym.lifehelper.login.scan.entity.ScanLoginTicket;
import com.inlym.lifehelper.login.scan.exception.ScanLoginTicketNotFoundException;
import com.inlym.lifehelper.login.scan.repository.ScanLoginTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 扫码登录凭据服务
 *
 * <h2>主要用途
 * <p>管理「扫码登录凭据」的创建、扫码、确认、使用、销毁等生命周期。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/5
 * @since 1.9.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ScanLoginTicketService {
    private final ScanLoginTicketRepository repository;

    private final ScanLoginQrcodeService scanLoginQrcodeService;

    private final LocationService locationService;

    /**
     * 根据 ID 获取凭据（若不存在则报错）
     *
     * @param id 凭据 ID
     *
     * @since 1.9.0
     */
    public ScanLoginTicket getOrElseThrow(String id) {
        Optional<ScanLoginTicket> result = repository.findById(id);
        if (result.isEmpty()) {
            throw ScanLoginTicketNotFoundException.of(id);
        } else {
            return result.get();
        }
    }

    /**
     * 根据 ID 获取凭据（若不存在则报错），同时对 IP 进行校验，若不同则报错
     *
     * @param id 凭据 ID
     * @param ip 客户端 IP 地址
     *
     * @since 1.9.0
     */
    public ScanLoginTicket getOrElseThrow(String id, String ip) {
        ScanLoginTicket ticket = getOrElseThrow(id);
        if (ticket
            .getIp()
            .equals(ip)) {
            return ticket;
        }
        throw ScanLoginTicketNotFoundException.of(id);
    }

    /**
     * 创建凭据
     *
     * @param ip 被扫码端（Web）的 IP 地址（由控制器传入）
     *
     * @since 1.9.0
     */
    public ScanLoginTicket create(String ip) {
        String id = scanLoginQrcodeService.getOne();

        // 备注（2023.01.05）
        // 实际上这一步不应该放在这个方法内，但是由于这个方法被设定为异步方法，无法放在原有类中调用，
        // 如果要封装则需要额外增加一个服务类，有点累赘，因此直接放在这里，省事。
        scanLoginQrcodeService.batchGenerateIfLessThanAsync(1);

        ScanLoginTicket ticket = ScanLoginTicket
            .builder()
            .id(id)
            .status(ScanLoginTicketStatus.CREATED)
            .ip(ip)
            .createTime(LocalDateTime.now())
            .build();

        repository.save(ticket);
        return ticket;
    }

    /**
     * （异步）根据 IP 地址设置所在地区
     *
     * <h2>说明
     * <p>这个方法实际上应该放在当前服务类的 {@code create} 方法中，但是为了能够更快地给被扫码端返回小程序码，
     * 将这个方法剥离出来，异步处理。
     *
     * @param id 凭据 ID
     *
     * @since 1.9.0
     */
    @Async
    public void setRegionAsync(String id) {
        ScanLoginTicket ticket = getOrElseThrow(id);
        String region = locationService.getRoughIpRegion(ticket.getIp());
        ticket.setRegion(region);
        repository.save(ticket);
    }

    /**
     * 「扫码」操作
     *
     * @param id 扫码登录凭据 ID
     *
     * @since 1.9.0
     */
    public ScanLoginTicket scan(String id) {
        ScanLoginTicket ticket = getOrElseThrow(id);

        // 只有「已创建未扫码」状态需要处理，其他状态均不做任何处理
        if (ticket.getStatus() == ScanLoginTicketStatus.CREATED) {
            ticket.setStatus(ScanLoginTicketStatus.SCANNED);
            ticket.setScanTime(LocalDateTime.now());
            repository.save(ticket);
        }

        return ticket;
    }

    /**
     * 「确认登录」操作
     *
     * @param id 扫码登录凭据 ID
     *
     * @since 1.9.0
     */
    public ScanLoginTicket confirm(String id, int userId) {
        ScanLoginTicket ticket = getOrElseThrow(id);

        // 只有「已扫码未确认」状态需要处理，其他状态均不做任何处理
        if (ticket.getStatus() == ScanLoginTicketStatus.SCANNED) {
            ticket.setStatus(ScanLoginTicketStatus.CONFIRMED);
            ticket.setConfirmTime(LocalDateTime.now());
            ticket.setUserId(userId);
            repository.save(ticket);
        }

        return ticket;
    }

    /**
     * 销毁扫码登录凭据
     *
     * <h2>说明
     * <p>在使用凭据换取登录凭证后，对凭据进行销毁操作。
     *
     * @param id 扫码登录凭据 ID
     *
     * @since 1.9.0
     */
    public void destroy(String id) {
        repository.deleteById(id);
    }
}
