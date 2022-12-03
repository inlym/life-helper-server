package com.inlym.lifehelper.user.account;

import com.inlym.lifehelper.user.account.entity.User;
import com.inlym.lifehelper.user.account.event.NewUserRegistrationEvent;
import com.inlym.lifehelper.user.account.exception.UserNotExistException;
import com.inlym.lifehelper.user.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/**
 * 用户账户服务
 *
 * <h2>主要用途
 * <p>管理用户账户权限验证、授权等事项。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountService {
    private final ApplicationContext applicationContext;

    private final UserRepository userRepository;

    /**
     * 计算用户的账户 ID
     *
     * <h2>说明
     * <p>仅在账户创建时计算1次并存入。
     *
     * @param userId 用户 ID
     *
     * @since 1.7.0
     */
    private static int calcAccountId(int userId) {
        return (1428571 + userId * 17 + new Random().nextInt(10));
    }

    /**
     * 注册新用户
     *
     * @param openid 微信小程序的用户唯一标识
     *
     * @since 1.7.0
     */
    public User register(String openid) {
        User user = User
            .builder()
            .openid(openid)
            .registerTime(LocalDateTime.now())
            .build();

        userRepository.save(user);

        // 数据插入后，获取自增 ID，根据此再计算账户 ID，之后再存入
        user.setAccountId(calcAccountId(user.getId()));
        userRepository.save(user);

        log.info("[新用户注册] userId={}, openid={}, accountId={}", user.getId(), user.getOpenid(), user.getAccountId());

        // 发布新用户注册事件
        applicationContext.publishEvent(new NewUserRegistrationEvent(user));

        return user;
    }

    /**
     * 通过 openID 获取用户 ID
     *
     * @param openid 微信小程序的用户唯一标识
     *
     * @since 1.7.0
     */
    public int getUserIdByOpenid(String openid) {
        User user = userRepository.findByOpenid(openid);
        if (user != null) {
            return user.getId();
        } else {
            return register(openid).getId();
        }
    }

    /**
     * 查找用户
     *
     * @param userId 用户 ID
     *
     * @since 1.7.0
     */
    public User findById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw UserNotExistException.fromId(userId);
        }

        return user.get();
    }
}
