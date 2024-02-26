package com.inlym.lifehelper.login.qrcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于扫码登录的二维码信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginQrCode {
    /** 携带的 ID 参数 */
    private String id;

    /** 图片资源的完整 URL 地址 */
    private String url;
}
