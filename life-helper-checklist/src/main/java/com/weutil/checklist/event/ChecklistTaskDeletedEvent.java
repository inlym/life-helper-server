package com.weutil.checklist.event;

import com.weutil.checklist.entity.ChecklistTask;

/**
 * 待办任务删除事件
 *
 * <h2>说明
 * <p>说明文本内容
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/3
 * @since 2.3.0
 **/
public class ChecklistTaskDeletedEvent extends ChecklistTaskEvent {
    public ChecklistTaskDeletedEvent(ChecklistTask entity) {
        super(entity);
    }
}
