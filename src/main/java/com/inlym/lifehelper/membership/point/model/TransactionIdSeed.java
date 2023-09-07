package com.inlym.lifehelper.membership.point.model;

import com.inlym.lifehelper.membership.point.constant.PointTransactionChannel;
import com.inlym.lifehelper.membership.point.constant.PointTransactionType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用于创建订单号的种子
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/8
 * @since 2.0.2
 **/
@Data
public class TransactionIdSeed {
    /** 订单创建时间 */
    private LocalDateTime createTime;

    /** 交易类型 */
    private PointTransactionType type;

    /** 交易渠道 */
    private PointTransactionChannel channel;

    /** 用户 ID */
    private Integer userId;
}
