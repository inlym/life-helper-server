package com.weutil.todo.event;

import com.weutil.todo.entity.TodolistProject;

/**
 * 待办项目删除事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public class TodolistProjectDeltedEvent extends TodolistProjectEvent {
    public TodolistProjectDeltedEvent(TodolistProject project) {
        super(project);
    }
}
