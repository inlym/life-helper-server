package com.weutil.checklist.constant;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 任务状态
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/2
 * @since 2.3.0
 **/
@Getter
public enum TaskStatus {
    /** 未开始（默认） */
    NOT_STARTED(0),

    /** 进行中 */
    IN_PROGRESS(1),

    /** 已完成 */
    COMPLETED(2);

    @EnumValue
    private final int code;

    TaskStatus(int code) {
        this.code = code;
    }
}
