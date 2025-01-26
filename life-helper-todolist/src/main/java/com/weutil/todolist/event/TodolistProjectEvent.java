package com.weutil.todolist.event;

import com.weutil.todolist.entity.TodolistProject;
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
public class TodolistProjectEvent extends ApplicationEvent {
    private final TodolistProject project;

    public TodolistProjectEvent(TodolistProject project) {
        super(project);
        this.project = project;
    }
}
