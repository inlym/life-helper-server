package com.inlym.lifehelper.todo.constant;

import com.mybatisflex.annotation.EnumValue;

/**
 * 任务状态
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/11/21
 * @since 2.0.3
 **/
public enum TaskStatus {
    /** 未完成（默认值） */
    UNCOMPLETED(0),

    /** 已完成 */
    COMPLETED(1);

    @EnumValue
    private final int code;

    TaskStatus(int code) {
        this.code = code;
    }
}
