package com.weutil.account.service;

import com.mybatisflex.core.query.QueryCondition;
import com.weutil.account.entity.PhoneAccount;
import com.weutil.account.entity.table.PhoneAccountTableDef;
import com.weutil.account.mapper.PhoneAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
     * @param phone 手机号，示例值：{@code 13111111111}
     *
     * @date 2024/7/24
     * @since 3.0.0
     */
    public PhoneAccount getOrCreatePhoneAccount(String phone) {
        QueryCondition condition = PhoneAccountTableDef.PHONE_ACCOUNT.PHONE.eq(phone);
        PhoneAccount result = phoneAccountMapper.selectOneByCondition(condition);

        // 能找到则直接返回结果
        if (result != null) {
            return result;
        }

        // 未找到则说明：该手机号从未注册过，需要先注册一个新用户，再返回
        long userId = userService.createUser().getId();
        PhoneAccount inserted = PhoneAccount.builder().userId(userId).phone(phone).build();
        phoneAccountMapper.insertSelective(inserted);
        return phoneAccountMapper.selectOneById(inserted.getId());
    }
}
