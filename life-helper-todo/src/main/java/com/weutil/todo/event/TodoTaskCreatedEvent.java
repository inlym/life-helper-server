package com.weutil.todo.event;

import com.weutil.todo.entity.TodoTask;

/**
 * 待办任务创建事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/24
 * @since 3.0.0
 **/
public class TodoTaskCreatedEvent extends TodoTaskEvent {
    public TodoTaskCreatedEvent(TodoTask task) {
        super(task);
    }
}
