package com.weutil.reminder.exception;

/**
 * 删除时项目不为空异常
 *
 * <h2>说明
 * <p>删除项目时，要求项目下的未完成任务数应为0，若不为空，则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
public class ProjectNotEmptyWhenDeletedException extends RuntimeException {}
