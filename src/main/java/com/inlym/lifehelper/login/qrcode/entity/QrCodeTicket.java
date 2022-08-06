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
 * <h2>生命周期
 * <li>[已创建 CREATED]：被扫码端发起申请获取一个凭据，状态值：`0`。
 * <li>[已扫码 SCANNED]：扫码端进行“扫码”操作但未点击“确认”，状态值：`1`。
 * <li>[已确认 CONFIRMED]：扫码端进行“确认”登录操作，状态值：`2`。
 * <li>[已使用 CONSUMED]：扫码端使用凭据 ID 获取登录凭证，完成登录。无状态值，因为使用完成后将整个实体删除了。
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

    /** 生成的小程序码存放在 OSS 的完整 URL 地址 */
    private String url;

    /**
     * 发起者（Web 端）的 IP 地址
     *
     * <h2>说明
     * <p>在 Web 端发起请求需要一个新的小程序码凭证时，会记录该 IP 地址，在获取登录状态时，也会带上 IP 地址，两者需一致。这样做可以从一定程度
     * 上降低登录凭证被冒领的风险。
     */
    private String ip;

    /** IP 地址所在区域，包含省和市，例如：浙江杭州 */
    private String region;

    /** 凭证状态 */
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
     */
    @TimeToLive
    public long getTimeToLive() {
        return Duration
            .ofMinutes(30)
            .toSeconds();
    }

    /** 凭据状态值 */
    public static class Status {
        /** 已创建 */
        public static final int CREATED = 0;

        /** 已扫码但未确认 */
        public static final int SCANNED = 1;

        /** 已扫码确认 */
        public static final int CONFIRMED = 2;

        /** 无效的，用于找不到对应的凭证编码情况 */
        public static final int INVALID = -1;
    }
}
