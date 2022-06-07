package com.inlym.lifehelper.login.scanlogin;

import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.login.scanlogin.pojo.QrcodeCredential;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 小程序码凭证服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/13
 * @since 1.1.0
 **/
@Service
@Slf4j
public class QrcodeCredentialService {
    private final QrcodeTicketService qrcodeTicketService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final OssService ossService;

    private final LocationService locationService;

    public QrcodeCredentialService(QrcodeTicketService qrcodeTicketService, RedisTemplate<String, Object> redisTemplate, OssService ossService, LocationService locationService) {
        this.qrcodeTicketService = qrcodeTicketService;
        this.redisTemplate = redisTemplate;
        this.ossService = ossService;
        this.locationService = locationService;
    }

    /**
     * 获取 Redis 键名
     *
     * @param ticket 凭证编码
     *
     * @since 1.1.0
     */
    private String getRedisKey(String ticket) {
        return "scan_login:ticket:" + ticket;
    }

    /**
     * 从 Redis 中获取小程序码凭证对象
     *
     * <h2>说明
     * <p>返回值可能为 {@code null}。
     *
     * @param ticket 凭证编码
     *
     * @since 1.1.0
     */
    private QrcodeCredential getFromRedis(String ticket) {
        return (QrcodeCredential) redisTemplate
            .opsForValue()
            .get(getRedisKey(ticket));
    }

    /**
     * 创建一个新的小程序码鉴权凭证
     *
     * @since 1.1.0
     */
    public QrcodeCredential create(String ip) {
        // 异步流程判断是否需要额外去生成一批凭证编码
        qrcodeTicketService.createTicketsWhenFewAsync(10, 10);

        // 获取一个凭证编码
        String ticket = qrcodeTicketService.getTicket();

        QrcodeCredential qc = new QrcodeCredential();
        qc.setTicket(ticket);
        qc.setUrl(ossService.concatUrl(qrcodeTicketService.getPathname(ticket)));
        qc.setIp(ip);

        String region = locationService.getRoughIpRegion(ip);

        qc.setRegion(region);

        // 存入 Redis，有效期 10 分钟
        redisTemplate
            .opsForValue()
            .set(getRedisKey(ticket), qc, 10, TimeUnit.MINUTES);

        return qc;
    }

    /**
     * 获取小程序码凭证对象
     *
     * @param ticket 凭证编码
     *
     * @since 1.1.0
     */
    public QrcodeCredential get(String ticket) {
        QrcodeCredential qc = getFromRedis(ticket);

        if (qc == null) {
            return QrcodeCredential.invalid();
        }

        return qc;
    }

    /**
     * 进行扫码操作（仅扫码，未点击确定）
     *
     * @param ticket 凭证编码
     * @param userId 用户 ID
     *
     * @since 1.1.0
     */
    public QrcodeCredential scan(String ticket, int userId) {
        QrcodeCredential qc = getFromRedis(ticket);

        if (qc == null) {
            return QrcodeCredential.invalid();
        }

        qc.setUserId(userId);
        qc.setScanTime(System.currentTimeMillis());
        qc.setStatus(QrcodeCredential.Status.SCANNED);

        // 存入 Redis，有效期 10 分钟
        redisTemplate
            .opsForValue()
            .set(getRedisKey(ticket), qc, 10, TimeUnit.MINUTES);

        return qc;
    }

    /**
     * 进行确认登录操作
     *
     * @param ticket 凭证编码
     * @param userId 用户 ID
     *
     * @since 1.1.0
     */
    public QrcodeCredential confirm(String ticket, int userId) {
        QrcodeCredential qc = getFromRedis(ticket);

        if (qc == null) {
            return QrcodeCredential.invalid();
        }

        qc.setUserId(userId);
        qc.setConfirmTime(System.currentTimeMillis());
        qc.setStatus(QrcodeCredential.Status.CONFIRMED);

        // 存入 Redis，有效期 10 分钟
        redisTemplate
            .opsForValue()
            .set(getRedisKey(ticket), qc, 10, TimeUnit.MINUTES);

        return qc;
    }

    /**
     * 标记鉴权凭证已使用（即不能用于再次新的登录凭证）
     *
     * <h2>说明
     * <p>在调用这个方法前已经对凭证对象进行了验证，不会出现 {@code null} 值。
     *
     * @param ticket 凭证编码
     *
     * @since 1.1.0
     */
    public void consume(String ticket) {
        QrcodeCredential qc = getFromRedis(ticket);

        qc.setConsumeTime(System.currentTimeMillis());
        qc.setStatus(QrcodeCredential.Status.CONSUMED);

        // 存入 Redis，有效期 10 分钟
        redisTemplate
            .opsForValue()
            .set(getRedisKey(ticket), qc, 10, TimeUnit.MINUTES);
    }
}
