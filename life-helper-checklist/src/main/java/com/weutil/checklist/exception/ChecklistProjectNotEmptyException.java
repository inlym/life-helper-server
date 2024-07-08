package com.weutil.checklist.exception;

/**
 * 待办项目非空异常
 *
 * <h2>触发条件
 * <p>删除项目时，要求项目为空才能删除（即项目中不能包含“未完成”的任务），若有，则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/3
 * @since 2.3.0
 **/
public class ChecklistProjectNotEmptyException extends RuntimeException {}
