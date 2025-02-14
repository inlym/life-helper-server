package com.weutil.todo.event;

import com.weutil.todo.entity.TodolistTask;

/**
 * 待办任务删除事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public class TodolistTaskDeletedEvent extends TodolistTaskEvent {
    public TodolistTaskDeletedEvent(TodolistTask task) {
        super(task);
    }
}
