package com.inlym.lifehelper.login.scanlogin.credential;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;

/**
 * 登录凭证
 *
 * <h2>说明
 * <li>整个扫码登录流程都是围绕着这个登录凭证进行。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/27
 * @since 1.3.0
 **/
@Data
@NoArgsConstructor
@RedisHash("database:login_credential")
public class LoginCredential {
    /** 凭证编号，目前为去掉短横线的 UUID */
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

    @TimeToLive
    public long getTimeToLive() {
        return Duration
            .ofMinutes(30)
            .toSeconds();
    }

    /** 凭证状态 */
    public static class Status {
        /** 已创建 */
        public static final int CREATED = 0;

        /** 已扫码但未确认 */
        public static final int SCANNED = 1;

        /** 已扫码确认 */
        public static final int CONFIRMED = 2;

        /** 已使用（用于生成登录凭证） */
        public static final int CONSUMED = 3;

        /** 无效的，用于找不到对应的凭证编码情况 */
        public static final int INVALID = -1;
    }
}
