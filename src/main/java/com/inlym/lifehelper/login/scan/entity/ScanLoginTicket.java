package com.inlym.lifehelper.login.scan.entity;

import com.inlym.lifehelper.login.scan.constant.ScanLoginTicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 扫码登录凭据实体
 *
 * <h2>主要用途
 * <p>「扫码登录」的整个生命周期都围绕着这个实体的增删改查操作。
 *
 * <h2>注意事项
 * <p>该实体存储于 Redis 中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/4
 * @since 1.9.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "database:scan-login-ticket")
public class ScanLoginTicket {
    /**
     * 票据 ID
     *
     * <h2>来源
     * <p>使用的小程序码的对应 ID。
     */
    @Id
    private String id;

    /**
     * 凭据状态
     *
     * <h2>状态值
     * <li>[CREATED]   - 已创建
     * <li>[SCANNED]   - 已扫码
     * <li>[CONFIRMED] - 已确认
     * <li>[CONSUMED]  - 已使用（用于生成登录凭证），后续该实体将会销毁。
     */
    private ScanLoginTicketStatus status;

    /**
     * 扫码端操作「确认登录」的用户 ID
     */
    private Integer userId;

    /**
     * 发起「扫码登录」操作的客户端的 IP 地址
     *
     * <h2>说明
     * <p>目前仅为 Web 端，IP 地址用于转换为地区信息，用于查看确认。
     */
    private String ip;

    /**
     * 地区信息，用于扫码端查看
     *
     * <h2>说明
     * <p>地区信息由 IP 地址转换而来。
     *
     * <h2>示例
     * <li>浙江 杭州
     */
    private String region;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 扫码时间
     *
     * <h2>说明
     * <p>扫码端（目前为微信小程序）扫码的时间。
     */
    private LocalDateTime scanTime;

    /**
     * 确认时间
     *
     * <h2>说明
     * <p>扫码端（目前为微信小程序）点击「确认登录」的时间。
     */
    private LocalDateTime confirmTime;

    /**
     * 使用时间
     *
     * <h2>说明
     * <p>被扫码端（目前为 Web）根据 ID 转换获得登录凭证的时间。
     */
    private LocalDateTime consumeTime;

    /**
     * 获取到期时间
     *
     * <h2>说明
     * <p>当前方法用于配置实体在 Redis 中的有效期。
     */
    @TimeToLive
    public long getTimeToLive() {
        return Duration
            .ofMinutes(30L)
            .toSeconds();
    }
}
