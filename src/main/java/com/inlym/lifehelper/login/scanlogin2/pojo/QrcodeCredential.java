package com.inlym.lifehelper.login.scanlogin2.pojo;

import lombok.Data;

/**
 * 小程序码鉴权凭证
 *
 * <h2>使用说明
 * <p>该对象仅用于内部逻辑判断，输出给客户端时，请转换为 {@link ScanLoginResult} 业务对象。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/12
 * @since 1.1.0
 **/
@Data
public class QrcodeCredential {
    /** 凭证编号，一般是去掉短横线的 UUID */
    private String ticket;

    /** 生成的小程序码访问地址 */
    private String url;

    /**
     * 发起者（Web 端）的 IP 地址
     *
     * <h2>说明
     * <p>在 Web 端发起请求需要一个新的小程序码凭证时，会记录该 IP 地址，在获取登录状态时，也会带上 IP 地址，两者需一致。这样做可以从一定程度
     * 上降低登录凭证被冒领的风险。
     */
    private String ip;

    /** IP 地址所在区域，包含省和市，例如：浙江省杭州市 */
    private String region;

    /** 状态 */
    private int status = Status.CREATED;

    /** 创建时间（时间戳） */
    private Long createTime = System.currentTimeMillis();

    /** 扫码时间（时间戳） */
    private Long scanTime;

    /** 确认时间（时间戳） */
    private Long confirmTime;

    /** 使用时间（时间戳） */
    private Long consumeTime;

    /** 扫码操作者用户 ID */
    private int userId = 0;

    /**
     * 创建一个无效的凭证
     *
     * @since 1.1.0
     */
    public static QrcodeCredential invalid() {
        QrcodeCredential credential = new QrcodeCredential();
        credential.setStatus(Status.INVALID);
        return credential;
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
