package com.inlym.lifehelper.account.login.qrcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 扫码端进行“扫码”操作后的响应结果
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScanLoginQrCodeResultVO {
    /** 被扫码端 IP 地址 */
    private String ip;

    /** 被扫码端 IP 位置名称 */
    private String location;
}
