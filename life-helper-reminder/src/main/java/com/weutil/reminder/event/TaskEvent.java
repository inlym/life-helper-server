package com.weutil.reminder.event;

import com.weutil.reminder.entity.Task;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 任务系列事件
 *
 * <h2>说明
 * <p>所有“任务”相关事件均继承当前事件。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
@Getter
public class TaskEvent extends ApplicationEvent {
    private final Task task;
    private final Long taskId;
    private final Long projectId;
    private final Long userId;

    public TaskEvent(Task entity) {
        super(entity);

        this.task = entity;
        this.taskId = entity.getId();
        this.projectId = entity.getProjectId();
        this.userId = entity.getUserId();
    }
}
