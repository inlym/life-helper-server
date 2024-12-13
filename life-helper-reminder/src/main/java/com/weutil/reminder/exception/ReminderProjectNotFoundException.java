package com.weutil.reminder.exception;

import com.weutil.common.exception.ResourceNotFoundException;
import lombok.Getter;

/**
 * 待办项目未找到异常
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/13
 * @since 3.0.0
 **/
@Getter
public class ReminderProjectNotFoundException extends ResourceNotFoundException {
    /** 主键 ID */
    private final Long pkId;

    /** 用户 ID */
    private final Long userId;

    public ReminderProjectNotFoundException(long userId, long pkId) {
        this.pkId = pkId;
        this.userId = userId;
    }
}
