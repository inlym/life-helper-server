package com.weutil.reminder.event;

import com.weutil.reminder.entity.ReminderTask;

/**
 * 待办任务被标记为“已完成”事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/26
 * @since 3.0.0
 **/
public class ReminderTaskCompletedEvent extends ReminderTaskEvent {
    public ReminderTaskCompletedEvent(ReminderTask task) {
        super(task);
    }
}
