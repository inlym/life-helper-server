package com.weutil.account.service;

import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.weutil.account.entity.User;
import com.weutil.account.event.LoginEvent;
import com.weutil.account.event.UserCreatedEvent;
import com.weutil.account.mapper.UserMapper;
import com.weutil.common.util.RandomStringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.weutil.account.entity.table.UserTableDef.USER;

/**
 * 用户账户服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    /** 默认头像存储路径 */
    private static final String DEFAULT_AVATAR_PATH = "avatar/default.jpg";
    private final UserMapper userMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 创建新用户
     *
     * @return 新创建用户的 ID
     * @since 2.3.0
     */
    public long createUser() {
        User user = User.builder().nickName("用户_" + RandomStringUtil.generate(6)).avatarPath(DEFAULT_AVATAR_PATH).build();
        userMapper.insertSelective(user);

        // 发布用户创建事件
        applicationEventPublisher.publishEvent(new UserCreatedEvent(userMapper.selectOneById(user.getId())));

        return user.getId();
    }

    /**
     * （异步）监听登录事件
     *
     * @date 2024/6/22
     * @since 2.3.0
     */
    @Async
    @EventListener(LoginEvent.class)
    public void listenToLoginEvent(LoginEvent event) {
        log.trace("[EventListener=LoginEvent] event={}", event);

        // 更新登录统计数据
        User updated = UpdateEntity.of(User.class, event.getUserId());
        updated.setLastLoginTime(event.getLoginTime());
        updated.setLastLoginIp(event.getIp());
        UpdateWrapper<User> wrapper = UpdateWrapper.of(updated);
        wrapper.set(USER.LOGIN_COUNTER, USER.LOGIN_COUNTER.add(1));

        userMapper.update(updated);
    }
}
