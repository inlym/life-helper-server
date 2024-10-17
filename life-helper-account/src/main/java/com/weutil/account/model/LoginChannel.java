package com.weutil.account.model;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 登录渠道
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/8/28
 * @since 3.0.0
 **/
@Getter
public enum LoginChannel {
    /** Web 网页端（www 主站） */
    WEB(1),

    /** 微信小程序 */
    MINI_PROGRAM(2);

    @EnumValue
    private final Integer code;

    LoginChannel(int code) {
        this.code = code;
    }
}
