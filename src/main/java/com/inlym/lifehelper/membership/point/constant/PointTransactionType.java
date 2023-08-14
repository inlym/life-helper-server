package com.inlym.lifehelper.membership.point.constant;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 积分变动类型
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/8/15
 * @since 2.0.2
 **/
@Getter
public enum PointTransactionType {
    // =========================== 收入类型 ===========================

    /**
     * 每日签到获取积分
     */
    SIGN_IN(101),

    /**
     * 退还积分
     * <p>
     * <h2>事件说明
     * <p>用于取消已支出的某笔交易，需要关联支出积分交易编号。
     */
    REFUND(900);

    // =========================== 支出类型 ===========================

    /**
     * 类型的数字编码
     * <p>
     * <h2>字段说明
     * <p>1. 存入数据库的是 `code` 对应的数字。
     * <p>2. `code` 一经使用，不允许更改。
     */
    @EnumValue
    private final int code;

    PointTransactionType(int code) {
        this.code = code;
    }
}
