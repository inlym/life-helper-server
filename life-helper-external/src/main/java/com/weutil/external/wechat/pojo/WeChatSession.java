package com.weutil.external.wechat.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信小程序会话
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/15
 * @since 2.1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeChatSession {
    /** 用户在小程序的唯一标识 */
    private String openId;

    /** 用户在微信开放平台的唯一标识 */
    private String unionId;

    /** 会话密钥 */
    private String sessionKey;
}
