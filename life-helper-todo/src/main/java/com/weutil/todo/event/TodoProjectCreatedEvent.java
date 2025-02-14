package com.weutil.todo.event;

import com.weutil.todo.entity.TodoProject;

/**
 * 待办项目创建事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public class TodoProjectCreatedEvent extends TodoProjectEvent {
    public TodoProjectCreatedEvent(TodoProject project) {
        super(project);
    }
}
