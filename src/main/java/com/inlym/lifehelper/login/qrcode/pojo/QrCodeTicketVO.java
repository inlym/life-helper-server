package com.inlym.lifehelper.login.qrcode.pojo;

import com.inlym.lifehelper.common.auth.core.AuthenticationCredential;
import lombok.Builder;
import lombok.Data;

/**
 * 扫码登录凭据
 *
 * <h2>主要用途
 * <p>用于客户端使用的扫码登录凭据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/6
 * @since 1.3.0
 **/
@Data
@Builder
public class QrCodeTicketVO {
    /** 凭据 ID */
    private String id;

    /** 生成的小程序码存放在 OSS 的完整 URL 地址 */
    private String url;

    /** 被扫码端的 IP 地址 */
    private String ip;

    /** IP 地址所在区域，包含省和市，例如：浙江杭州 */
    private String region;

    /** 凭证状态 */
    private Integer status;

    /** 鉴权凭证（仅在“已确认”状态时返回） */
    private AuthenticationCredential credential;
}
