package com.weutil.checklist.event;

import com.weutil.checklist.entity.ChecklistProject;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 待办项目事件
 *
 * <h2>说明
 * <p>其他的待办项目事件均继承自当前事件。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/4
 * @since 2.3.0
 **/
@Getter
public class ChecklistProjectEvent extends ApplicationEvent {
    private final ChecklistProject checklistProject;
    private final Long userId;
    private final Long projectId;

    public ChecklistProjectEvent(ChecklistProject entity) {
        super(entity);

        this.checklistProject = entity;
        this.userId = entity.getUserId();
        this.projectId = entity.getId();
    }
}
