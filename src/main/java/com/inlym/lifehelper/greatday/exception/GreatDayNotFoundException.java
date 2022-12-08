package com.inlym.lifehelper.greatday.exception;

/**
 * 未找到纪念日异常
 *
 * <h2>使用说明
 * <p>当发现要查找的 ID 不存在或 ID 存在但不归属于当前用户，则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/9
 * @since 1.8.0
 **/
public class GreatDayNotFoundException extends RuntimeException {
    public GreatDayNotFoundException() {
        super();
    }
}
