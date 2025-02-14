package com.weutil.todo.exception;

/**
 * 待办项目不允许被删除异常
 *
 * <h2>以下情况抛出异常
 * <p>情况[1]: 该项目下未完成的任务数不为0
 *
 * <h2>前端异常处理
 * <p>直接展示对应的错误消息文本 {@code message}。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/13
 * @since 3.0.0
 **/
public class TodoProjectFailedToDeleteException extends RuntimeException {
    public TodoProjectFailedToDeleteException(String message) {
        super(message);
    }
}
