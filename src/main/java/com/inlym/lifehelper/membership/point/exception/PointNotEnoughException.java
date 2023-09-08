package com.inlym.lifehelper.membership.point.exception;

/**
 * 积分不足异常
 * <p>
 * <h2>主要用途
 * <p>要扣除的积分数低于余额时，抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/8
 * @since 2.0.3
 **/
public class PointNotEnoughException extends RuntimeException {
    public PointNotEnoughException() {
        super("积分不足");
    }
}
