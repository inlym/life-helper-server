package com.weutil.reminder.exception;

/**
 * 待办项目重名异常
 *
 * <h2>说明
 * <p>创建待办项目时，检测待创建的项目名称已经存在了（非全局，仅针对于同一用户），则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public class ReminderProjectDuplicateNameException extends RuntimeException {}
