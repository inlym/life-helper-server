package com.weutil.todolist.event;

import com.weutil.todolist.entity.TodolistTask;

/**
 * 待办任务被标记为“已完成”事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/26
 * @since 3.0.0
 **/
public class TodolistTaskCompletedEvent extends TodolistTaskEvent {
    public TodolistTaskCompletedEvent(TodolistTask task) {
        super(task);
    }
}
