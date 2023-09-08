package com.inlym.lifehelper.membership.point.service;

import com.inlym.lifehelper.membership.point.entity.PointTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 积分服务
 * <p>
 * <h2>主要用途
 * <p>汇总各个方法。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/8
 * @since 2.0.2
 **/
@Service
@RequiredArgsConstructor
public class PointService {
    private final PointProfileService pointProfileService;

    private final PointTransactionService pointTransactionService;

    /**
     * 创建积分交易
     *
     * @param pt 积分交易记录实体
     *
     * @return 插入了主键ID、订单编号等数据的实体
     *
     * @date 2023/9/8
     * @since 2.0.3
     */
    public PointTransaction createTransaction(PointTransaction pt) {
        pointProfileService.changeWithTransaction(pt);
        pointTransactionService.create(pt);

        return pt;
    }
}
