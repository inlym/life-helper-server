package com.weutil.todo.event;

import com.weutil.todo.entity.TodoTask;
import lombok.Getter;

/**
 * 待办任务移动项目事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Getter
public class TodoTaskMovedEvent extends TodoTaskEvent {
    /** 移动前的项目 ID */
    private final Long originProjectId;

    /** 移动后的项目 ID */
    private final Long targetProjectId;

    public TodoTaskMovedEvent(TodoTask task, long originProjectId, long targetProjectId) {
        super(task);

        this.originProjectId = originProjectId;
        this.targetProjectId = targetProjectId;
    }
}
