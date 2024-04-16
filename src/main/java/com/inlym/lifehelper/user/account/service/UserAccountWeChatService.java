package com.inlym.lifehelper.user.account.service;

import com.inlym.lifehelper.user.account.entity.UserAccountWeChat;
import com.inlym.lifehelper.user.account.mapper.UserAccountWeChatMapper;
import com.inlym.lifehelper.user.account.model.WeChatAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.inlym.lifehelper.user.account.entity.table.UserAccountWeChatTableDef.USER_ACCOUNT_WE_CHAT;

/**
 * 用户微信账户服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/4/16
 * @since 2.3.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountWeChatService {
    private final UserAccountWeChatMapper userAccountWeChatMapper;

    private final UserAccountService userAccountService;

    /**
     * 通过微信账户数据获取用户 ID
     *
     * @param weChatAccount 微信账户信息
     *
     * @return 用户 ID
     */

    public long getUserIdByWeChat(WeChatAccount weChatAccount) {
        UserAccountWeChat account = userAccountWeChatMapper.selectOneByCondition(USER_ACCOUNT_WE_CHAT.APP_ID
                                                                                         .eq(weChatAccount.getAppId())
                                                                                         .and(USER_ACCOUNT_WE_CHAT.UNION_ID.eq(weChatAccount.getUnionId())));
        if (account != null) {
            UserAccountWeChat updated = UserAccountWeChat
                    .builder()
                    .id(account.getId())
                    .counter(account.getCounter() + 1)
                    .lastTime(LocalDateTime.now())
                    .build();
            userAccountWeChatMapper.update(updated);
            return account.getUserId();
        }

        // 未找到说明是新用户，需要先注册，再返回
        long userId = userAccountService.createUser();
        UserAccountWeChat entity = UserAccountWeChat
                .builder()
                .appId(weChatAccount.getAppId())
                .openId(weChatAccount.getOpenId())
                .unionId(weChatAccount.getUnionId())
                .userId(userId)
                .build();
        userAccountWeChatMapper.insertSelective(entity);
        log.info("[新用户通过微信注册] {}", entity);

        return userId;
    }
}
