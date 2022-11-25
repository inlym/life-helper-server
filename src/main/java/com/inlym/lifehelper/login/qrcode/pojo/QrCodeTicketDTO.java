package com.inlym.lifehelper.login.qrcode.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 扫码登录凭据传输实体
 *
 * <h2>主要用途
 * <p>用于客户端（包含扫码端和被扫码端）向服务器传输扫码登录凭据信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/7
 * @since 1.3.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrCodeTicketDTO {
    /** 凭据 ID */
    @NotEmpty
    private String id;
}
