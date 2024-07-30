package com.weutil.reminder.event;

import com.weutil.reminder.entity.Task;
import lombok.Getter;

/**
 * 任务归属的项目 ID 改变事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/30
 * @since 3.0.0
 **/
@Getter
public class TaskBelongingChangedEvent extends TaskEvent {
    /** 修改前的项目 ID */
    private final Long sourceProjectId;
    /** 修改后的项目 ID */
    private final Long targetProjectId;

    public TaskBelongingChangedEvent(Task entity, long sourceProjectId, long targetProjectId) {
        super(entity);

        this.sourceProjectId = sourceProjectId;
        this.targetProjectId = targetProjectId;
    }
}
