package com.weutil.account.model;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 性别
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/3
 * @since 3.0.0
 **/
@Getter
public enum Gender {
    /** 未知 */
    UNKNOWN(0),

    /** 男 */
    MALE(1),

    /** 女 */
    FEMALE(2);

    @EnumValue
    private final Integer code;

    Gender(int code) {
        this.code = code;
    }
}
