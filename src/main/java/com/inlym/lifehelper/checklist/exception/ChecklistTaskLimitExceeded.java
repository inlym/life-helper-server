package com.inlym.lifehelper.checklist.exception;

/**
 * 当前拥有的（不包含已删除的）待办任务数已达到上限异常（即无法继续创建）
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/3
 * @since 2.3.0
 **/
public class ChecklistTaskLimitExceeded extends RuntimeException {}
