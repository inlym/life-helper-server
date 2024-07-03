package com.inlym.lifehelper.checklist.event;

import com.inlym.lifehelper.checklist.entity.ChecklistTask;

/**
 * 待办任务被完成事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/3
 * @since 2.3.0
 **/
public class ChecklistTaskCompletedEvent extends ChecklistTaskEvent {
    public ChecklistTaskCompletedEvent(ChecklistTask entity) {
        super(entity);
    }
}
