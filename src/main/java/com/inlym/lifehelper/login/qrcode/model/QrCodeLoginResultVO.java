package com.inlym.lifehelper.login.qrcode.model;

import com.inlym.lifehelper.common.auth.core.IdentityCertificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 扫码登录结果
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/6
 * @since 2.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrCodeLoginResultVO {
    /** 是否已失效 */
    private Boolean invalid;

    /** 是否已扫码，该状态仅用于页面展示 */
    private Boolean scanned;

    /**
     * 是否已登录
     *
     * <h2>字段说明
     * <p>该字段为 {@code true} 时，在 {@code identityCertificate} 字段中将包含登录凭证。
     */
    private Boolean logined;

    /** 登录凭证 */
    private IdentityCertificate identityCertificate;
}
