package com.inlym.lifehelper.common.base.aliyun.sms.constant;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 发送状态
 *
 * <h2>说明
 * <p>说明文本内容
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/18
 * @since 2.3.0
 **/
@Getter
public enum SendingStatus {
    /** 未发送 */
    UNSENT(0),

    /** 已发送 */
    SENT(1);

    @EnumValue
    private final int code;

    SendingStatus(int code) {
        this.code = code;
    }
}
