package com.inlym.lifehelper.checklist.event;

import com.inlym.lifehelper.checklist.entity.ChecklistTask;

/**
 * 待办任务创建事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/3
 * @since 2.3.0
 **/
public class ChecklistTaskCreatedEvent extends ChecklistTaskEvent {
    public ChecklistTaskCreatedEvent(ChecklistTask entity) {
        super(entity);
    }
}
