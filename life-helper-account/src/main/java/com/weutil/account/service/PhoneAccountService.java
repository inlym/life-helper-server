package com.weutil.account.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.weutil.account.entity.PhoneAccount;
import com.weutil.account.event.LoginByPhoneCodeEvent;
import com.weutil.account.mapper.PhoneAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.weutil.account.entity.table.PhoneAccountTableDef.PHONE_ACCOUNT;

/**
 * 手机号账户服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneAccountService {
    private final PhoneAccountMapper phoneAccountMapper;
    private final UserService userService;

    /**
     * 通过手机号获取关联表账户
     *
     * @param phone 手机号
     *
     * @date 2024/7/24
     * @since 3.0.0
     */
    public PhoneAccount getOrCreatePhoneAccount(String phone) {
        QueryCondition condition = PHONE_ACCOUNT.PHONE.eq(phone);
        PhoneAccount result = phoneAccountMapper.selectOneByCondition(condition);

        // 能找到则直接返回结果
        if (result != null) {
            return result;
        }

        // 未找到则说明：该手机号从未注册过，需要先注册一个新用户，再返回
        long userId = userService.createUser();
        PhoneAccount inserted = PhoneAccount.builder().userId(userId).phone(phone).build();
        phoneAccountMapper.insertSelective(inserted);
        return phoneAccountMapper.selectOneById(inserted.getId());
    }

    /**
     * （异步）监听使用手机号（短信验证码）账户登录事件
     *
     * @date 2024/6/13
     * @since 2.3.0
     */
    @Async
    @EventListener(LoginByPhoneCodeEvent.class)
    public void listenToLoginByPhoneSmsEvent(LoginByPhoneCodeEvent event) {
        log.trace("[EventListener=LoginByPhoneCodeEvent] event={}", event);

        // 更新登录统计数据
        PhoneAccount updated = UpdateEntity.of(PhoneAccount.class, event.getPhoneAccountId());
        updated.setLastLoginTime(event.getLoginTime());
        updated.setLastLoginIp(event.getIp());
        UpdateWrapper<PhoneAccount> wrapper = UpdateWrapper.of(updated);
        wrapper.set(PHONE_ACCOUNT.LOGIN_COUNTER, PHONE_ACCOUNT.LOGIN_COUNTER.add(1));

        phoneAccountMapper.update(updated);
    }
}
