package com.inlym.lifehelper.checklist.exception;

/**
 * 待办清单模块资源未找到异常
 *
 * <h2>说明
 * <p>通过资源 ID 查找时，未找到或找到了但并不归属该用户，则报出当前异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/11
 * @since 2.3.0
 **/
public class ChecklistResourceNotFoundException extends RuntimeException {
    public ChecklistResourceNotFoundException(String message) {
        super(message);
    }
}
