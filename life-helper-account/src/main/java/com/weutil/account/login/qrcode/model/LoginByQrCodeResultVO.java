package com.weutil.account.login.qrcode.model;

import com.weutil.common.auth.core.IdentityCertificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 被扫码端登录结果
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginByQrCodeResultVO {
    /**
     * 登录结果状态
     *
     * <h2>说明
     * <p>扫码登录凭据的各个状态的处理策略：
     * <li>[CREATED] - 1 -> 被扫码端处理：不变
     * <li>[SCANNED] - 2 -> 被扫码端处理：增加已扫码样式
     * <li>[CONFIRMED] - 3 -> 会包含 IdentityCertificate -> 被扫码端处理：登录成功，进行页面跳转，并且不再继续轮询查询登录结果
     * <li>[CONSUMED,EXCEPTION] - 4 -> 被扫码端处理：增加已失效样式，并且不再继续轮询查询登录结果
     */
    private Integer status;

    /**
     * 登录凭据
     *
     * <h2>说明
     * <p>只有在 status=3 时，该属性才有值，其余情况为空。
     */
    private IdentityCertificate certificate;
}
