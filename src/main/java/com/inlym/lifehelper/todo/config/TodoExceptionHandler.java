package com.inlym.lifehelper.todo.config;

import com.inlym.lifehelper.common.model.ErrorResponse;
import com.inlym.lifehelper.todo.exception.TodoProjectNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 待办清单模块异常处理器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/11/22
 * @since 2.0.3
 **/
@RestController
@Order(500)
public class TodoExceptionHandler {
    @ResponseStatus(HttpStatus.FOUND)
    @ExceptionHandler(TodoProjectNotFoundException.class)
    public ErrorResponse handleTodoProjectNotFoundException() {
        return new ErrorResponse(50001);
    }
}
