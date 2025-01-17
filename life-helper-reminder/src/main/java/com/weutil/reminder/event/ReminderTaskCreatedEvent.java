package com.weutil.reminder.event;

import com.weutil.reminder.entity.ReminderTask;

/**
 * 待办任务创建事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/24
 * @since 3.0.0
 **/
public class ReminderTaskCreatedEvent extends ReminderTaskEvent {
    public ReminderTaskCreatedEvent(ReminderTask task) {
        super(task);
    }
}
