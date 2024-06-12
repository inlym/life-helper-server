package com.inlym.lifehelper.account.user.service;

import com.inlym.lifehelper.account.user.entity.UserAccountPhone;
import com.inlym.lifehelper.account.user.event.LoginByPhoneEvent;
import com.inlym.lifehelper.account.user.mapper.UserAccountPhoneMapper;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.inlym.lifehelper.account.user.entity.table.UserAccountPhoneTableDef.USER_ACCOUNT_PHONE;

/**
 * 用户手机号账户服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/13
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountPhoneService {
    private final UserAccountPhoneMapper userAccountPhoneMapper;

    private final UserAccountService userAccountService;

    /**
     * 通过手机号获取手机号账户关联表数据
     *
     * @param phone 手机号
     *
     * @date 2024/6/13
     * @since 2.3.0
     */
    public UserAccountPhone getUserAccountPhone(String phone) {
        QueryCondition condition = USER_ACCOUNT_PHONE.PHONE.eq(phone);
        UserAccountPhone result = userAccountPhoneMapper.selectOneByCondition(condition);
        if (result != null) {
            return result;
        }

        // 未找到说明从未使用手机号注册过，需要先注册一个新用户，再返回
        long userId = userAccountService.createUser();
        UserAccountPhone inserted = UserAccountPhone.builder().userId(userId).phone(phone).build();
        userAccountPhoneMapper.insertSelective(inserted);
        return userAccountPhoneMapper.selectOneById(inserted.getId());
    }

    /**
     * 监听使用手机号账户登录事件
     *
     * @date 2024/6/13
     * @since 2.3.0
     */
    @Async
    @EventListener(LoginByPhoneEvent.class)
    public void listenToLoginByPhoneEvent(LoginByPhoneEvent event) {
        long id = event.getUserAccountPhoneId();

        // 更新统计数据
        UserAccountPhone updated = UpdateEntity.of(UserAccountPhone.class, id);
        updated.setLastTime(LocalDateTime.now());
        UpdateWrapper<UserAccountPhone> wrapper = UpdateWrapper.of(updated);
        wrapper.set(USER_ACCOUNT_PHONE.COUNTER, USER_ACCOUNT_PHONE.COUNTER.add(1));

        userAccountPhoneMapper.update(updated);
        userAccountService.refreshLoginStatistic(event.getUserId());
    }
}
