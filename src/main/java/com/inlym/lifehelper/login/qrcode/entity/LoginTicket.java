package com.inlym.lifehelper.login.qrcode.entity;

import com.inlym.lifehelper.login.qrcode.constant.LoginTicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

/**
 * 扫码登录凭据
 *
 * <h2>注意事项
 * <p>该实体存储于 Redis 中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "database:login-ticket", timeToLive = 60 * 60)
public class LoginTicket {
    /** 凭据 ID */
    @Id
    private String id;

    /** 登录端的 IP 地址 */
    private String ip;

    /** IP 定位获取的位置，最低到市级，例如中国、浙江省、杭州市等。 */
    private String location;

    /** 小程序码（二维码）图片资源的完整 URL 地址 */
    private String url;

    /** 凭据状态 */
    private LoginTicketStatus status;

    /** 扫码端操作「确认登录」的用户 ID */
    private Long userId;

    /** 创建时间 */
    private LocalDateTime createTime;

    /**
     * 扫码时间
     *
     * <h2>说明
     * <p>扫码端（目前为微信小程序）扫码的时间。
     */
    private LocalDateTime scanTime;

    /**
     * 操作“确认登录”时间
     *
     * <h2>说明
     * <p>扫码端（目前为微信小程序）点击「确认登录」的时间。
     */
    private LocalDateTime confirmeTime;

    /**
     * 消费时间
     *
     * <h2>说明
     * <p>被消费用于生成登录凭证。
     */
    private LocalDateTime consumeTime;
}
