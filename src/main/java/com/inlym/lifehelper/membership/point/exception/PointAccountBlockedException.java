package com.inlym.lifehelper.membership.point.exception;

/**
 * 积分账户被封禁异常
 * <p>
 * <h2>主要用途
 * <p>判断积分账户被封禁后，禁止用户进行任意积分相关操作，检测到操作时，抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/8
 * @since 2.0.3
 **/
public class PointAccountBlockedException extends RuntimeException {
    public PointAccountBlockedException() {
        super("积分账户被封禁");
    }
}
