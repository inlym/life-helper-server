package com.inlym.lifehelper.login.qrcode2.entity;

import com.inlym.lifehelper.login.qrcode2.constant.QrCodeTicketStatus;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

/**
 * 二维码凭据实体
 *
 * <h2>主要用途
 * <p>「扫码登录」的整个生命周期都围绕着这个实体的增删改查操作。
 *
 * <h2>注意事项
 * <p>该实体存储于 Redis 中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/15
 * @since 2.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "database:qrcode2-ticket", timeToLive = 60 * 60)
public class QrCodeTicket {
    /**
     * 凭据 ID
     *
     * <h2>来源
     * <p>使用的小程序码的对应 ID。
     */
    @Id
    private String id;

    /** 凭据状态 */
    private QrCodeTicketStatus status;

    /** 扫码端操作「确认登录」的用户 ID */
    private Long userId;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /**
     * 扫码时间
     *
     * <h2>说明
     * <p>扫码端（目前为微信小程序）扫码的时间。
     */
    private LocalDateTime scannedTime;

    /**
     * 确认时间
     *
     * <h2>说明
     * <p>扫码端（目前为微信小程序）点击「确认登录」的时间。
     */
    private LocalDateTime confirmedTime;
}
