package com.weutil.reminder.event;

import com.weutil.reminder.entity.ReminderProject;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 待办项目相关事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Getter
public class ReminderProjectEvent extends ApplicationEvent {
    private final ReminderProject project;

    public ReminderProjectEvent(ReminderProject project) {
        super(project);
        this.project = project;
    }
}
