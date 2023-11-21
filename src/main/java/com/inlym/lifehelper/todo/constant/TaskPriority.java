package com.inlym.lifehelper.todo.constant;

import com.mybatisflex.annotation.EnumValue;

/**
 * 任务优先级
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/11/21
 * @since 2.0.3
 **/
public enum TaskPriority {
    /** 无优先级（默认值） */
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
