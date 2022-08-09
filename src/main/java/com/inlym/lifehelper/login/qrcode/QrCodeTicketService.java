package com.inlym.lifehelper.login.qrcode;

import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.login.qrcode.entity.QrCodeTicket;
import com.inlym.lifehelper.login.qrcode.entity.QrCodeTicketRepository;
import com.inlym.lifehelper.login.qrcode.exception.InvalidQrCodeTicketException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 扫码登录凭据管理服务
 *
 * <h2>主要用途
 * <p>用于管理扫码登录凭据的整个生命周期（包含扫码端和被扫码端）。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/6
 * @since 1.3.0
 **/
@Service
@RequiredArgsConstructor
public class QrCodeTicketService {
    private final QrCodeTicketRepository repository;

    private final WeChatQrCodeService weChatQrCodeService;

    private final LocationService locationService;

    /**
     * 生成一个扫码登录凭据
     *
     * @since 1.3.0
     */
    public QrCodeTicket create() {
        // 说明：非本方法内流程，只是放在此处检测比较合适
        // 检测当剩余可用数量较少时，则批量生成一批新的
        weChatQrCodeService.batchGenerateIfNeedAsync();

        String id = weChatQrCodeService.getOne();

        QrCodeTicket qt = QrCodeTicket
            .builder()
            .id(id)
            .url(weChatQrCodeService.getUrl(id))
            .status(QrCodeTicket.Status.CREATED)
            .createTime(System.currentTimeMillis())
            .build();

        repository.save(qt);

        return qt;
    }

    /**
     * 获取扫码登录凭据实体
     *
     * @param id 实体 ID
     *
     * @since 1.3.0
     */
    public QrCodeTicket getEntity(String id) {
        return repository
            .findById(id)
            .orElseThrow(() -> InvalidQrCodeTicketException.fromId(id));
    }

    /**
     * 给扫码登录凭证设置 IP 地址和对应地区信息
     *
     * <h2>说明
     * <p>从流程上来讲，这一步不应该分离出来，而应该在方法 {@link QrCodeTicketService#create()} 方法中处理的，分离出来异步处理的优缺点是：
     * <p>优点：更快地返回凭证编码，用于 Web 端展示。
     * <p>缺点：小程序端扫码展示信息时，根据 IP 获得地区信息不一定已获取，此时展示为空。（由于中间时间差较大，因此发生的概率较小）
     *
     * @param id 扫码登录凭据 ID
     * @param ip 发起者（被扫码端）的 IP 地址
     *
     * @since 1.3.0
     */
    @Async
    public void setIpRegionAsync(String id, String ip) {
        QrCodeTicket qt = getEntity(id);

        qt.setIp(ip);
        qt.setRegion(locationService.getRoughIpRegion(ip));

        repository.save(qt);
    }

    /**
     * 进行“扫码”操作
     *
     * <h2>说明
     * <p>用于扫码端。
     *
     * @param id 扫码登录凭据 ID
     */
    public void scan(String id) {
        QrCodeTicket qt = getEntity(id);
        if (qt.getStatus() == QrCodeTicket.Status.CREATED) {
            qt.setStatus(QrCodeTicket.Status.SCANNED);
            qt.setScanTime(System.currentTimeMillis());
            repository.save(qt);
        }
    }

    /**
     * 进行“确认登录”操作
     *
     * <h2>说明
     * <p>用于扫码端。
     *
     * @param id 扫码登录凭据 ID
     */
    public void confirm(String id, int userId) {
        QrCodeTicket qt = getEntity(id);
        if (qt.getStatus() <= QrCodeTicket.Status.CONFIRMED) {
            qt.setStatus(QrCodeTicket.Status.CONFIRMED);
            qt.setConfirmTime(System.currentTimeMillis());
            qt.setUserId(userId);
            repository.save(qt);
        }
    }

    /**
     * 使用扫码登录凭据，使用后立即销毁
     *
     * @param id 扫码登录凭据 ID
     *
     * @since 1.3.0
     */
    public void consume(String id) {
        QrCodeTicket qt = getEntity(id);
        repository.delete(qt);
    }
}
