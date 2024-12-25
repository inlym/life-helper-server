package com.weutil.reminder.event;

import com.weutil.reminder.entity.ReminderTask;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 待办任务创建事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/24
 * @since 3.0.0
 **/
@Getter
public class ReminderTaskCreatedEvent extends ApplicationEvent {
    private final ReminderTask task;

    public ReminderTaskCreatedEvent(ReminderTask task) {
        super(task);

        this.task = task;
    }
}
