package com.weutil.todo.event;

import com.weutil.todo.entity.TodoTask;

/**
 * 待办任务删除事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public class TodoTaskDeletedEvent extends TodoTaskEvent {
    public TodoTaskDeletedEvent(TodoTask task) {
        super(task);
    }
}
