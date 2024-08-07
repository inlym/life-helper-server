package com.weutil.reminder.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 任务优先级
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
@Getter
public enum TaskPriority {
    /** 无优先级（默认） */
    NONE(0),

    /** 低优先级 */
    LOW(1),

    /** 中优先级 */
    MEDIUM(2),

    /** 高优先级 */
    HIGH(3);

    @EnumValue
    @JsonValue
    private final int code;

    TaskPriority(int code) {
        this.code = code;
    }

    public static TaskPriority fromCode(int code) {
        for (TaskPriority item : TaskPriority.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("无效的 code 值：" + code);
    }
}
