package com.weutil.reminder.event;

import com.weutil.reminder.entity.ReminderProject;

/**
 * 待办项目删除事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public class ReminderProjectDeltedEvent extends ReminderProjectEvent {
    public ReminderProjectDeltedEvent(ReminderProject project) {
        super(project);
    }
}
