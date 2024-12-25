package com.weutil.reminder.event;

import com.weutil.reminder.entity.ReminderProject;

/**
 * 待办项目创建事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public class ReminderProjectCreatedEvent extends ReminderProjectEvent {
    public ReminderProjectCreatedEvent(ReminderProject project) {
        super(project);
    }
}
