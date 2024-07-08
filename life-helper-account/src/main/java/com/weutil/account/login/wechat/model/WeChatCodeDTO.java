package com.weutil.account.login.wechat.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 微信登录使用的请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/10
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html">小程序登录</a>
 * @since 2.3.0
 **/
@Data
public class WeChatCodeDTO {
    /** 微信登录凭证 */
    @NotEmpty
    private String code;

    /**
     * 小程序的 appId
     *
     * <h3>说明
     * <p>后期会接入多个小程序，为方便区分，直接由小程序传入该值。
     */
    @NotEmpty
    private String appId;
}
