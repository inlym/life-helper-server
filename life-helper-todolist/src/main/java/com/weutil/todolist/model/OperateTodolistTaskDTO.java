package com.weutil.todolist.model;

import lombok.Data;

/**
 * 操作待办任务的请求数据
 *
 * <h2>说明
 * <p>处理特殊操作，包含以下：
 * <p>（1）完成
 * <p>（2）取消完成
 * <p>（3）清空截止时间
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Data
public class OperateTodolistTaskDTO {
    private TodolistTaskOperation operation;
}
