package com.inlym.lifehelper.login.scan.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 扫码登录凭据视图对象
 *
 * <h2>主要用途
 * <p>将实体 {@link com.inlym.lifehelper.login.scan.entity.ScanLoginTicket} 转化为客户端展示的对象。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/5
 * @since 1.9.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScanLoginTicketVO {
    /** 票据 ID */
    private String id;

    /** 小程序码图片的 URL 地址 */
    private String url;

    /** IP 地址 */
    private String ip;

    /** 地区信息 */
    private String region;
}
