package com.inlym.lifehelper.account.user.service;

import com.inlym.lifehelper.account.user.entity.User;
import com.inlym.lifehelper.account.user.entity.table.UserTableDef;
import com.inlym.lifehelper.account.user.event.UserCreatedEvent;
import com.inlym.lifehelper.account.user.mapper.UserMapper;
import com.inlym.lifehelper.common.util.StringUtil;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户账户服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/4/16
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
public class UserAccountService {
    /** 默认头像存储路径 */
    private static final String DEFAULT_AVATAR_PATH = "avatar/default.jpg";

    private final UserMapper userMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 创建新用户
     *
     * @since 2.3.0
     */
    public long createUser() {
        User user = User.builder().nickName("用户_" + StringUtil.generateRandomString(6)).avatarPath(DEFAULT_AVATAR_PATH).build();

        userMapper.insertSelective(user);
        // 发布用户创建事件
        applicationEventPublisher.publishEvent(new UserCreatedEvent(userMapper.selectOneById(user.getId())));

        return user.getId();
    }

    /**
     * 刷新登录统计数据
     *
     * @param userId 用户 ID
     *
     * @since 2.3.0
     */
    public void refreshLoginStatistic(long userId) {
        User updated = UpdateEntity.of(User.class, userId);
        updated.setLastLoginTime(LocalDateTime.now());

        UpdateWrapper<User> wrapper = UpdateWrapper.of(updated);
        wrapper.set(UserTableDef.USER.LOGIN_COUNTER, UserTableDef.USER.LOGIN_COUNTER.add(1));

        userMapper.update(updated);
    }
}
