package com.weutil.todo.event;

import com.weutil.todo.entity.TodoProject;

/**
 * 待办项目删除事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public class TodoProjectDeltedEvent extends TodoProjectEvent {
    public TodoProjectDeltedEvent(TodoProject project) {
        super(project);
    }
}
