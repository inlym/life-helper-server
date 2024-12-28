package com.weutil.reminder.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 待办清单模块异常捕获器
 *
 * <h3>错误码范围
 * <p>总体范围: {@code 13xxx}
 * <p>待办项目部分（project）: {@code 131xx}
 * <p>待办任务部分（task）: {@code 132xx}
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/26
 * @since 3.0.0
 **/
@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 1050)
public class ReminderExceptionHandler {}
