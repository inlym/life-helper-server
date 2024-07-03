package com.inlym.lifehelper.checklist.event;

import com.inlym.lifehelper.checklist.entity.ChecklistTask;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 待办任务事件
 *
 * <h2>说明
 * <p>其他的待办任务事件均继承自当前事件。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/4
 * @since 2.3.0
 **/
@Getter
public class ChecklistTaskEvent extends ApplicationEvent {
    private final ChecklistTask checklistTask;
    private final Long userId;
    private final Long projectId;
    private final Long taskId;

    public ChecklistTaskEvent(ChecklistTask entity) {
        super(entity);

        this.checklistTask = entity;
        this.userId = entity.getUserId();
        this.projectId = entity.getProjectId();
        this.taskId = entity.getId();
    }
}
