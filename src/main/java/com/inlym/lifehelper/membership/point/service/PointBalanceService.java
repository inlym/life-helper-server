package com.inlym.lifehelper.membership.point.service;

import com.inlym.lifehelper.membership.point.entity.PointBalance;
import com.inlym.lifehelper.membership.point.entity.table.PointBalanceTableDef;
import com.inlym.lifehelper.membership.point.mapper.PointBalanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 积分余额服务
 * <p>
 * <h2>主要用途
 * <p>处理「积分余额」相关事项。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/8/15
 * @since 2.0.2
 **/
@Service
@RequiredArgsConstructor
public class PointBalanceService {
    private final PointBalanceMapper pointBalanceMapper;

    /**
     * 获取积分余额
     *
     * @param userId 用户 ID
     *
     * @return 积分余额的数值
     *
     * @date 2023/8/15
     * @since 2.0.2
     */
    public long getBalance(int userId) {
        PointBalance entity = getEntity(userId);
        return entity.getBalance();
    }

    /**
     * 通过用户 ID 获取实体对象
     *
     * @param userId 用户 ID
     *
     * @return 实体对象
     *
     * @date 2023/8/15
     * @since 2.0.2
     */
    private PointBalance getEntity(int userId) {
        PointBalance result = pointBalanceMapper.selectOneByCondition(PointBalanceTableDef.POINT_BALANCE.USER_ID.eq(userId));
        if (result != null) {
            return result;
        }

        // 每个用户的数据默认是空的，查询不存在时增增加对应记录
        PointBalance entity = PointBalance
            .builder()
            .userId(userId)
            .build();

        // 插入数据，注意，这里会自动赋值主键 ID，后续可直接通过主键 ID 查询
        pointBalanceMapper.insertSelective(entity);
        return pointBalanceMapper.selectOneById(entity.getId());
    }
}
