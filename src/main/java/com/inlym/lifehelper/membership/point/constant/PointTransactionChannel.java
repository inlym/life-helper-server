package com.inlym.lifehelper.membership.point.constant;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 积分交易渠道
 * <p>
 * <h2>说明
 * <p>目前限定取值范围 1~9
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/8
 * @since 2.0.2
 **/
@Getter
public enum PointTransactionChannel {
    /**
     * 微信小程序
     */
    MINI_PROGRAM(1),

    /**
     * 网页
     */
    WEB(2);

    @EnumValue
    private final int code;

    PointTransactionChannel(int code) {
        this.code = code;
    }
}
