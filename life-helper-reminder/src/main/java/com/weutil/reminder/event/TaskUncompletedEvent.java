package com.weutil.reminder.event;

import com.weutil.reminder.entity.Task;

/**
 * 任务被取消完成事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
public class TaskUncompletedEvent extends TaskEvent {
    public TaskUncompletedEvent(Task entity) {
        super(entity);
    }
}
