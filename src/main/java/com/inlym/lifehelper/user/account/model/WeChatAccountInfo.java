package com.inlym.lifehelper.user.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信账户信息集合
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/4/16
 * @since 2.3.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeChatAccountInfo {
    /** 小程序开发者 ID */
    private String appId;

    /** 小程序的用户唯一标识 */
    private String openId;

    /** 用户在微信开放平台的唯一标识符 */
    private String unionId;
}
