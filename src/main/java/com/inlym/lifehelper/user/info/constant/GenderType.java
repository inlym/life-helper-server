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
    @JsonProperty("未知") UNKNOWN(0),

    /** 男 */
    @JsonProperty("男") MALE(1),

    /** 女 */
    @JsonProperty("女") FEMALE(2);

    @EnumValue
    private final int code;

    GenderType(int code) {
        this.code = code;
    }
}
