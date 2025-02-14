package com.weutil.todo.event;

import com.weutil.todo.entity.TodoTask;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 待办任务相关事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Getter
public class TodoTaskEvent extends ApplicationEvent {
    private final TodoTask task;

    public TodoTaskEvent(TodoTask task) {
        super(task);
        this.task = task;
    }
}
