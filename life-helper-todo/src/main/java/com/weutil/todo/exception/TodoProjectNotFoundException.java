package com.weutil.todo.exception;

import com.weutil.common.exception.ResourceNotFoundException;

/**
 * 待办项目未找到异常
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/13
 * @since 3.0.0
 **/
public class TodoProjectNotFoundException extends ResourceNotFoundException {
    public TodoProjectNotFoundException(long pkId, long userId) {
        super(pkId, userId);
    }
}
