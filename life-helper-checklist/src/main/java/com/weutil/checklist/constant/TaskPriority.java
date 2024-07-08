package com.weutil.checklist.constant;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 任务优先级
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/11
 * @since 2.3.0
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
    private final int code;

    TaskPriority(int code) {
        this.code = code;
    }
}
