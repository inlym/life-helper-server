package com.weutil.reminder.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 待办清单模块异常捕获器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/30
 * @since 3.0.0
 **/
@RestControllerAdvice
@Slf4j
@Order(21)
public class ReminderExceptionHandler {
    // ============================ [项目] 部分异常 ============================

    // ============================ [任务] 部分异常 ============================

    // ============================ [标签] 部分异常 ============================
}
