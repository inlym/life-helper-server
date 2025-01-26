package com.weutil.todolist.exception;

import com.weutil.common.exception.ResourceNotFoundException;

/**
 * 待办任务未找到异常
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/23
 * @since 3.0.0
 **/
public class TodolistTaskNotFoundException extends ResourceNotFoundException {
    public TodolistTaskNotFoundException(long pkId, long userId) {
        super(pkId, userId);
    }
}
