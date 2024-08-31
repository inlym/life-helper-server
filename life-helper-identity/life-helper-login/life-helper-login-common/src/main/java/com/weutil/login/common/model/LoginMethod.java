package com.weutil.login.common.model;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 登录方式
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/8/28
 * @since 3.0.0
 **/
@Getter
public enum LoginMethod {
    /** 手机号 + 短信验证码 */
    PHONE_SMS(1),

    /** 手机号 + 密码 */
    PHONE_PASSWORD(2);

    @EnumValue
    private final Integer code;

    LoginMethod(int code) {
        this.code = code;
    }
}
