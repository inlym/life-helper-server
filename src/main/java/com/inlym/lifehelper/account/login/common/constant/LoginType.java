package com.inlym.lifehelper.account.login.common.constant;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 登录方式
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/15
 * @since 2.3.0
 **/
@Getter
public enum LoginType {
    /** 微信应用（小程序、网站等）账户 */
    WECHAT_CODE(1),

    /** 手机号 + 短信验证码 */
    PHONE_SMS(2),

    /** 手机号 + 密码 */
    PHONE_PASSWORD(3);

    @EnumValue
    private final int code;

    LoginType(int code) {
        this.code = code;
    }
}
