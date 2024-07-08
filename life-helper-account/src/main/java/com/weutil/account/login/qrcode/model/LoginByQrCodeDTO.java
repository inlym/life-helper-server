package com.weutil.account.login.qrcode.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 扫码登录请求数据
 *
 * <h2>主要用途
 * <p>被扫码端（Web）检查登录状态，发起登录请求时的请求数据。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@Data
public class LoginByQrCodeDTO {
    /** 扫码登录票据 ID */
    @NotNull
    private String id;
}
