package com.inlym.lifehelper.membership.point.service;

import com.inlym.lifehelper.membership.point.constant.PointChangeMode;
import com.inlym.lifehelper.membership.point.entity.PointProfile;
import com.inlym.lifehelper.membership.point.entity.PointTransaction;
import com.inlym.lifehelper.membership.point.entity.table.PointBalanceTableDef;
import com.inlym.lifehelper.membership.point.exception.PointAccountBlockedException;
import com.inlym.lifehelper.membership.point.exception.PointNotEnoughException;
import com.inlym.lifehelper.membership.point.mapper.PointProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 积分余额服务
 * <p>
 * <h2>功能范围
 * <p>仅对 {@link PointProfile} 实体数据表做业务含义的二次封装，涉及到其他数据表数据的业务逻辑不要写在当前服务类中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/8/15
 * @since 2.0.2
 **/
@Service
@RequiredArgsConstructor
public class PointProfileService {
    private final PointProfileMapper pointProfileMapper;

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
        PointProfile entity = getEntity(userId);
        return entity.getBalance();
    }

    /**
     * 跟随交易变动积分信息
     *
     * @param pt 积分交易记录
     *
     * @date 2023/9/8
     * @since 2.0.3
     */
    public void changeWithTransaction(PointTransaction pt) {
        PointProfile profile = getEntity(pt.getUserId());

        // 判断账户是否被封禁
        if (profile.getBlocked()) {
            throw new PointAccountBlockedException();
        }
        // 判断积分是否足够支出
        if (pt.getChangeMode() == PointChangeMode.EXPENSE && pt.getAmount() > profile.getBalance()) {
            throw new PointNotEnoughException();
        }

        // 新建一个对象，除主键外，只赋值变更的值
        PointProfile changed = new PointProfile();
        changed.setId(profile.getId());
        changed.setUpdateTime(LocalDateTime.now());

        // 收支类型计数
        if (pt.getChangeMode() == PointChangeMode.INCOME) {
            changed.setIncomeCounter(profile.getIncomeCounter() + 1);
        } else if (pt.getChangeMode() == PointChangeMode.EXPENSE) {
            changed.setExpenseCounter(profile.getExpenseCounter() + 1);
        }

        if (pt.getAmount() > 0) {
            if (pt.getChangeMode() == PointChangeMode.INCOME) {
                changed.setBalance(profile.getBalance() + pt.getAmount());
                changed.setIncome(profile.getIncome() + pt.getAmount());
            } else if (pt.getChangeMode() == PointChangeMode.EXPENSE) {
                changed.setBalance(profile.getBalance() - pt.getAmount());
                changed.setExpense(profile.getExpense() + pt.getAmount());
            }
        }

        pointProfileMapper.update(changed);
    }

    /**
     * 通过用户 ID 获取实体对象
     * <p>
     * <h2>说明
     * <p>包含了为空情况自动创建逻辑。
     *
     * @param userId 用户 ID
     *
     * @return 实体对象
     *
     * @date 2023/8/15
     * @since 2.0.2
     */
    private PointProfile getEntity(int userId) {
        PointProfile result = pointProfileMapper.selectOneByCondition(PointBalanceTableDef.POINT_BALANCE.USER_ID.eq(userId));
        if (result != null) {
            return result;
        }

        // 创建初始对象并插入数据，注意，这里会自动赋值主键 ID，后续可直接通过主键 ID 查询
        PointProfile entity = getInitEntity(userId);
        pointProfileMapper.insertSelective(entity);
        return pointProfileMapper.selectOneById(entity.getId());
    }

    /**
     * 获取初始化的实体对象
     *
     * @param userId 用户 ID
     *
     * @date 2023/9/8
     * @since 2.0.3
     */
    private PointProfile getInitEntity(int userId) {
        return PointProfile
            .builder()
            .userId(userId)
            .balance(0L)
            .expense(0L)
            .income(0L)
            .expenseCounter(0L)
            .incomeCounter(0L)
            .blocked(false)
            .createTime(LocalDateTime.now())
            .updateTime(LocalDateTime.now())
            .build();
    }
}
