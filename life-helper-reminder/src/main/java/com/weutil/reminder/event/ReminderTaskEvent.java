package com.weutil.reminder.event;

import com.weutil.reminder.entity.ReminderTask;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 待办任务相关事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Getter
public class ReminderTaskEvent extends ApplicationEvent {
    private final ReminderTask task;

    public ReminderTaskEvent(ReminderTask task) {
        super(task);
        this.task = task;
    }
}
