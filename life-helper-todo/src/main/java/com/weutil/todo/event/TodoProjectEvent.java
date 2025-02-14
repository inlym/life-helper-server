package com.weutil.todo.event;

import com.weutil.todo.entity.TodoProject;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 待办项目相关事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Getter
public class TodoProjectEvent extends ApplicationEvent {
    private final TodoProject project;

    public TodoProjectEvent(TodoProject project) {
        super(project);
        this.project = project;
    }
}
