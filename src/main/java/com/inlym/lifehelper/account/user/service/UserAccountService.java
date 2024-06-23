package com.inlym.lifehelper.account.user.service;

import com.inlym.lifehelper.account.login.common.event.LoginEvent;
import com.inlym.lifehelper.account.user.entity.User;
import com.inlym.lifehelper.account.user.event.UserCreatedEvent;
import com.inlym.lifehelper.account.user.mapper.UserMapper;
import com.inlym.lifehelper.common.util.RandomStringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 用户账户服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/4/16
 * @since 2.3.0
 **/
@Service
@Slf4j
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
        User user = User.builder().nickName("用户_" + RandomStringUtil.generate(6)).avatarPath(DEFAULT_AVATAR_PATH).build();

        userMapper.insertSelective(user);
        // 发布用户创建事件
        applicationEventPublisher.publishEvent(new UserCreatedEvent(userMapper.selectOneById(user.getId())));

        return user.getId();
    }

    /**
     * 监听登录事件
     *
     * @date 2024/6/22
     * @since 2.3.0
     */
    @Async
    @EventListener(LoginEvent.class)
    public void listenToLoginEvent(LoginEvent event) {
        log.trace("[EventListener=LoginEvent] event={}", event);

        // TODO
    }
}
