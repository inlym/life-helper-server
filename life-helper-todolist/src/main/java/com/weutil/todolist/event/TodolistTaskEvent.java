package com.weutil.todolist.event;

import com.weutil.todolist.entity.TodolistTask;
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
public class TodolistTaskEvent extends ApplicationEvent {
    private final TodolistTask task;

    public TodolistTaskEvent(TodolistTask task) {
        super(task);
        this.task = task;
    }
}
