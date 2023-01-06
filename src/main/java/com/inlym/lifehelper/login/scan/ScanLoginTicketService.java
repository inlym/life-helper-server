package com.inlym.lifehelper.login.scan;

import com.inlym.lifehelper.login.scan.constant.ScanLoginTicketStatus;
import com.inlym.lifehelper.login.scan.entity.ScanLoginTicket;
import com.inlym.lifehelper.login.scan.exception.ScanLoginTicketInvalidOperationException;
import com.inlym.lifehelper.login.scan.exception.ScanLoginTicketNotFoundException;
import com.inlym.lifehelper.login.scan.repository.ScanLoginTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
     * 检查票据状态（如果是「已使用」状态的票据则报错）
     *
     * @param ticket 扫码登录票据
     *
     * @since 1.9.0
     */
    private void throwIfTicketConsumed(ScanLoginTicket ticket) {
        if (ticket.getStatus() == ScanLoginTicketStatus.CONSUMED) {
            throw ScanLoginTicketInvalidOperationException.of(ticket.getId());
        }
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
        scanLoginQrcodeService.batchGenerateIfLessThanAsync(10);

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
     * 「扫码」操作
     *
     * @param id 扫码登录凭据 ID
     *
     * @since 1.9.0
     */
    public ScanLoginTicket scan(String id) {
        ScanLoginTicket ticket = getOrElseThrow(id);
        throwIfTicketConsumed(ticket);

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
        throwIfTicketConsumed(ticket);

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
