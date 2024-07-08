package com.weutil.checklist.event;

import com.weutil.checklist.entity.ChecklistTask;

/**
 * 待办任务被取消完成事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/3
 * @since 2.3.0
 **/
public class ChecklistTaskUncompletedEvent extends ChecklistTaskEvent {
    public ChecklistTaskUncompletedEvent(ChecklistTask entity) {
        super(entity);
    }
}
