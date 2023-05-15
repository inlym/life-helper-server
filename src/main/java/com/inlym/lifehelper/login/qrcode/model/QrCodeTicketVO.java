package com.inlym.lifehelper.login.qrcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二维码凭据视图
 *
 * <h2>主要用途
 * <p>用于客户端展示使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/16
 * @since 2.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrCodeTicketVO {
    /** 票据 ID */
    private String id;

    /** 小程序码图片的 URL 地址 */
    private String url;
}
