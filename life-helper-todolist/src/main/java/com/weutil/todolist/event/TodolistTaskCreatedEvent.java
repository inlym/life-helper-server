package com.weutil.todolist.event;

import com.weutil.todolist.entity.TodolistTask;

/**
 * 待办任务创建事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/24
 * @since 3.0.0
 **/
public class TodolistTaskCreatedEvent extends TodolistTaskEvent {
    public TodolistTaskCreatedEvent(TodolistTask task) {
        super(task);
    }
}
