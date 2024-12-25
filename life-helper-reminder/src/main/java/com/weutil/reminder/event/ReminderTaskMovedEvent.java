package com.weutil.reminder.event;

import com.weutil.reminder.entity.ReminderTask;
import lombok.Getter;

/**
 * 待办任务移动项目事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Getter
public class ReminderTaskMovedEvent extends ReminderTaskEvent {
    /** 移动前的项目 ID */
    private final Long originProjectId;

    /** 移动后的项目 ID */
    private final Long targetProjectId;

    public ReminderTaskMovedEvent(ReminderTask task, long originProjectId, long targetProjectId) {
        super(task);

        this.originProjectId = originProjectId;
        this.targetProjectId = targetProjectId;
    }
}
