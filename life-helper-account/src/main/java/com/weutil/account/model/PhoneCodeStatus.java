package com.weutil.account.model;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 短信验证码状态
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/26
 * @since 3.0.0
 **/
@Getter
public enum PhoneCodeStatus {
    /** 未发送 */
    UNSENT(0),

    /** 已发送 */
    SENT(1),

    /**
     * 已失效
     *
     * <h3>状态说明
     * <p>目前重试次数超过5次会让有效的验证码变更为“已失效”状态。
     */
    INVALID(2);

    @EnumValue
    private final int code;

    PhoneCodeStatus(int code) {
        this.code = code;
    }
}
