package com.inlym.lifehelper.login.qrcode.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;

/**
 * 扫码登录凭据
 *
 * <h2>主要用途
 * <p>管理扫码登录的整个生命周期
 *
 * <h2>实体存储
 * <p>该实体仅存储于 Redis 中。
 *
 * <h2>生命周期（状态值）
 * <li>[已创建 CREATED]：被扫码端发起申请获取一个凭据，状态值：`0` （初始状态值）
 * <li>[已扫码 SCANNED]：扫码端进行“扫码”操作但未点击“确认登录”，状态值：`1`。
 * <li>[已确认 CONFIRMED]：扫码端进行“确认登录”登录操作，状态值：`2`。
 * <li>[已使用 CONSUMED]：扫码端使用凭据 ID 获取登录凭证，完成登录。该生命周期无状态值，因为使用完成后将整个实体（从 Redis 中）删除了。
 *
 * <h2>备忘须知
 * <p>本模块内（`com.inlym.lifehelper.login.qrcode`）提到的“二维码”、“QrCode”等均指微信小程序码，而不是普通的二维码。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/6
 * @since 1.3.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("db:qrcode_ticket")
public class QrCodeTicket {
    /** 凭据 ID */
    @Id
    private String id;

    /** 生成的小程序码存放在 OSS 的完整 URL 地址，可直接通过该 URL 访问资源 */
    private String url;

    /**
     * 发起者（Web 端）的 IP 地址
     *
     * <h2>说明
     * <p>在 Web 端（被扫码端）发起请求需要一个新的“扫码登录凭据”时，会记录该请求者的 IP 地址，在获取登录状态时，也会带上 IP 地址，两者需一致。这样做可以从一定程度
     * 上降低“扫码登录凭据”被冒领的风险。
     */
    private String ip;

    /** IP 地址所在区域，包含省和市，例如：浙江杭州 */
    private String region;

    /** 状态 */
    private Integer status;

    /** 创建时间（时间戳） */
    private Long createTime;

    /** 扫码时间（时间戳） */
    private Long scanTime;

    /** 确认时间（时间戳） */
    private Long confirmTime;

    /** 使用时间（时间戳） */
    private Long consumeTime;

    /** 扫码操作者用户 ID */
    private Integer userId;

    /**
     * 有效期：30分钟
     *
     * <h2>备注
     * <p>用于配置该实体在 Redis 中的有效期。
     */
    @TimeToLive
    public long getTimeToLive() {
        return Duration
            .ofMinutes(30)
            .toSeconds();
    }

    /** 扫码登录凭据状态值 */
    public static class Status {
        /** 已创建 */
        public static final int CREATED = 0;

        /** 已操作“扫码”，但未操作“确认登录” */
        public static final int SCANNED = 1;

        /** 已操作“确认登录” */
        public static final int CONFIRMED = 2;

        /** 无效的，用于找不到对应的凭据编码情况 */
        public static final int INVALID = -1;
    }
}
