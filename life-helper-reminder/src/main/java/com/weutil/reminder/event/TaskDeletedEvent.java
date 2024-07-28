package com.weutil.reminder.event;

import com.weutil.reminder.entity.Task;

/**
 * 任务被删除事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
public class TaskDeletedEvent extends TaskEvent {
    public TaskDeletedEvent(Task entity) {
        super(entity);
    }
}
