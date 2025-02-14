package com.weutil.todo.event;

import com.weutil.todo.entity.TodolistProject;

/**
 * 待办项目创建事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public class TodolistProjectCreatedEvent extends TodolistProjectEvent {
    public TodolistProjectCreatedEvent(TodolistProject project) {
        super(project);
    }
}
