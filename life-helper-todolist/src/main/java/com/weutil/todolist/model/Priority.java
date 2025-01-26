package com.weutil.todolist.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;

/**
 * 优先级
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2025/1/21
 * @since 3.0.0
 **/
public enum Priority {
    /** 无优先级（默认值） */
    NONE(0),

    /** 低优先级 */
    LOW(1),

    /** 中优先级 */
    MEDIUM(2),

    /** 高优先级 */
    HIGH(3);

    @EnumValue
    private final Integer code;

    Priority(int code) {
        this.code = code;
    }

    @JsonCreator
    public static Priority fromCode(int code) {
        for (Priority e : Priority.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid Priority code: " + code);
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
