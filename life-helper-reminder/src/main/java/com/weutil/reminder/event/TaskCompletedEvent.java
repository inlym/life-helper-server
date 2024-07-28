package com.weutil.reminder.event;

import com.weutil.reminder.entity.Task;

/**
 * 任务被完成事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
public class TaskCompletedEvent extends TaskEvent {
    public TaskCompletedEvent(Task entity) {
        super(entity);
    }
}
