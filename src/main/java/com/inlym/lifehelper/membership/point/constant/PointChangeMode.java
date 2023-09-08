package com.inlym.lifehelper.membership.point.constant;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 积分变化模式
 * <p>
 * <h2>说明
 * <p>收入和支出，两者数据均可能为 0。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/8
 * @since 2.0.2
 **/
@Getter
public enum PointChangeMode {
    /**
     * 收入
     */
    INCOME(1),

    /**
     * 支出
     */
    EXPENSE(2);

    @EnumValue
    private final int code;

    PointChangeMode(int code) {
        this.code = code;
    }
}
