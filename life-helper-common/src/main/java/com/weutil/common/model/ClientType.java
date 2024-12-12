package com.weutil.common.model;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 客户端类型
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/12
 * @since 3.0.0
 **/
@Getter
public enum ClientType {
    /** 未知 */
    UNKNOWN(0),

    /** Web 网页端 */
    WEB(1),

    /** 微信小程序 */
    MINI_PROGRAM(2);

    @EnumValue
    private final Integer code;

    ClientType(int code) {
        this.code = code;
    }
}
