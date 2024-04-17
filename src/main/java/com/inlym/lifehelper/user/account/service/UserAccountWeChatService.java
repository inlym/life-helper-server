package com.inlym.lifehelper.user.account.service;

import com.inlym.lifehelper.user.account.entity.UserAccountWeChat;
import com.inlym.lifehelper.user.account.mapper.UserAccountWeChatMapper;
import com.inlym.lifehelper.user.account.model.WeChatAccountInfo;
import com.mybatisflex.core.query.QueryCondition;
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
     * @param info 微信账户信息
     *
     * @return 用户 ID
     * @date 2024/4/17
     * @since 2.3.0
     */
    public long getUserIdByWeChat(WeChatAccountInfo info) {
        log.info("[微信登录] 微信账户信息: appId={}, openId={}, unionId={}", info.getAppId(), info.getOpenId(),
                 info.getUnionId());

        // 流程1：以“小程序账户级”查找优先级最高，若查到则直接返回对应的用户 ID
        QueryCondition condition1 = USER_ACCOUNT_WE_CHAT.APP_ID
                .eq(info.getAppId())
                .and(USER_ACCOUNT_WE_CHAT.OPEN_ID.eq(info.getOpenId()));
        UserAccountWeChat result1 = userAccountWeChatMapper.selectOneByCondition(condition1);

        if (result1 != null) {
            UserAccountWeChat updated = UserAccountWeChat
                    .builder()
                    .id(result1.getId())
                    .counter(result1.getCounter() + 1)
                    .lastTime(LocalDateTime.now())
                    .build();
            userAccountWeChatMapper.update(updated);
            log.debug("[用户描述] 老用户, userId={}, 当前小程序登录次数={}", result1.getUserId(), updated.getCounter());
            return result1.getUserId();
        }

        // 流程2：未找到，则说明这是首次使用这个小程序登录，继续检查“平台级”账号
        QueryCondition condition2 = USER_ACCOUNT_WE_CHAT.UNION_ID.eq(info.getUnionId());
        UserAccountWeChat result2 = userAccountWeChatMapper.selectOneByCondition(condition2);

        if (result2 != null) {
            // 继承对应的用户 ID，并将小程序账号级数据存入，下次登录时就直接在流程1返回了
            createAccount(result2.getUserId(), info);
            log.info("[用户描述] 小程序级新用户（访问过其他小程序）, userId={}", result2.getUserId());
            return result2.getUserId();
        }

        // 流程3：上述2步未找到说明是新用户，需要先注册一个新用户，再返回
        long userId = userAccountService.createUser();
        createAccount(userId, info);
        log.info("[用户描述] 平台级新用户, userId={}", userId);

        return userId;
    }

    /**
     * 创建微信账号关联账户
     *
     * @param userId 用户 ID
     * @param info   微信账户信息
     *
     * @date 2024/4/17
     * @since 2.3.0
     */
    private void createAccount(long userId, WeChatAccountInfo info) {
        UserAccountWeChat entity = UserAccountWeChat
                .builder()
                .appId(info.getAppId())
                .openId(info.getOpenId())
                .unionId(info.getUnionId())
                .userId(userId)
                .build();
        userAccountWeChatMapper.insertSelective(entity);
    }
}
