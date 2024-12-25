package com.weutil.reminder.event;

import com.weutil.reminder.entity.ReminderTask;

/**
 * 待办任务删除事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public class ReminderTaskDeletedEvent extends ReminderTaskEvent {
    public ReminderTaskDeletedEvent(ReminderTask task) {
        super(task);
    }
}
