package com.inlym.lifehelper.login.scanlogin2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 返回给客户端使用的扫码登录相关操作结果业务对象
 *
 * <h2>说明
 * <li>不同的状态使用的字段不同，客户端使用时需先判断状态。
 * <li>用于 Web 端和小程序端两个端的返回对象实际上应不同，这里省事直接放在一个对象里面了。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/13
 * @since 1.1.0
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScanLoginResult {
    /** 状态 */
    private Integer status;

    /** 凭证编号，一般是去掉短横线的 UUID */
    private String ticket;

    /** 生成的小程序码访问地址 */
    private String url;

    /** 登录凭证，鉴权成功后返回 */
    private String token;

    /** IP 地址 */
    private String ip;

    /** IP 地址所在区域，包含省和市，例如：浙江省杭州市 */
    private String region;

    /**
     * 创建一个无效的凭证
     *
     * @since 1.1.0
     */
    public static ScanLoginResult invalid() {
        ScanLoginResult credential = new ScanLoginResult();
        credential.setStatus(QrcodeCredential.Status.INVALID);
        return credential;
    }

    /**
     * 根据状态创建一个业务对象
     *
     * @param status 凭证状态
     *
     * @since 1.1.0
     */
    public static ScanLoginResult fromStatus(int status) {
        ScanLoginResult bo = new ScanLoginResult();
        bo.setStatus(status);
        return bo;
    }

    /**
     * 返给客户端显示的状态，与 {@link QrcodeCredential.Status} 并不完全对应。
     */
    public static class Status {
        /** 已失效 */
        public static final int INVALID = -1;

        /** 已创建 */
        public static final int CREATED = 0;

        /** 已扫码但未确认 */
        public static final int SCANNED = 1;

        /** 已扫码确认 */
        public static final int CONFIRMED = 2;
    }
}
