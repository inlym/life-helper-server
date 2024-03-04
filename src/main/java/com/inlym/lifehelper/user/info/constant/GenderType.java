package com.inlym.lifehelper.user.info.constant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 性别类型
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/16
 * @since 2.1.0
 **/
@Getter
public enum GenderType {
    /** 未知（默认值） */
    @JsonProperty("0") UNKNOWN(0),

    /** 男 */
    @JsonProperty("1") MALE(1),

    /** 女 */
    @JsonProperty("2") FEMALE(2);

    @EnumValue
    private final int code;

    GenderType(int code) {
        this.code = code;
    }
}
