package com.inlym.lifehelper.membership.point.service;

import com.inlym.lifehelper.membership.point.entity.PointTransaction;
import com.inlym.lifehelper.membership.point.mapper.PointTransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 积分交易记录服务
 * <p>
 * <h2>功能范围
 * <p>仅对 {@link PointTransaction} 实体数据表做业务含义的二次封装，涉及到其他数据表数据的业务逻辑不要写在此类中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/8
 * @since 2.0.2
 **/
@Service
@RequiredArgsConstructor
public class PointTransactionService {
    private final PointTransactionMapper pointTransactionMapper;

    private final TransactionIdGenerator transactionIdGenerator;

    /**
     * 创建交易记录
     *
     * @param pt 交易记录实体
     *
     * @date 2023/9/8
     * @since 2.0.2
     */
    public PointTransaction create(PointTransaction pt) {
        // 考虑到以后可能使用队列处理交易，提前预定义创建时间的情况，此处做兼容
        if (pt.getCreateTime() == null) {
            pt.setCreateTime(LocalDateTime.now());
        }

        pt.setTransactionId(transactionIdGenerator.generate(pt));
        pointTransactionMapper.insertSelective(pt);

        return pt;
    }
}
