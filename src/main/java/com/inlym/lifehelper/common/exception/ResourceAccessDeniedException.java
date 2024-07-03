package com.inlym.lifehelper.common.exception;

/**
 * 资源无权访问异常
 *
 * <h2>触发条件
 * <p>使用主键 ID 查找资源时，资源存在，但是并不归属于当前用户（即无权访问）
 *
 * <h2>使用说明
 * <p>不要直接使用这个类，而是在各个模块内继承这个类，抛出子类。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/3
 * @since 2.3.0
 **/
public class ResourceAccessDeniedException extends RuntimeException {}
