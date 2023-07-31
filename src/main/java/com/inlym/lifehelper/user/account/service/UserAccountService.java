package com.inlym.lifehelper.user.account.service;

import com.inlym.lifehelper.user.account.entity.User;
import com.inlym.lifehelper.user.account.entity.table.UserTableDef;
import com.inlym.lifehelper.user.account.event.NewUserRegistrationEvent;
import com.inlym.lifehelper.user.account.exception.UserNotExistException;
import com.inlym.lifehelper.user.account.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * 用户账户服务
 * <p>
 * <h2>主要用途
 * <p>管理用户账户权限验证、授权等事项。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/7/31
 * @since 2.0.2
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountService {
    private final ApplicationContext applicationContext;

    private final UserMapper userMapper;

    /**
     * 根据用户 ID 生成一个随机的账户 ID
     *
     * @param userId 用户 ID
     *
     * @date 2023/7/31
     * @since 2.0.2
     */
    private static int generateAccountId(int userId) {
        return (1428571 + userId * 17 + new Random().nextInt(10));
    }

    /**
     * 使用小程序的 openid 进行注册
     *
     * @param openid 微信小程序的用户唯一标识
     *
     * @date 2023/7/31
     * @since 2.0.2
     */
    public User register(String openid) {
        User user = User
            .builder()
            .openid(openid)
            .registerTime(LocalDateTime.now())
            .build();

        // 将数据存入，框架将自动填充 `id` 字段
        userMapper.insertSelective(user);
        log.info("[新用户注册] userId={}, openid={}", user.getId(), user.getOpenid());

        // 数据插入后，获取自增 ID，根据此再计算账户 ID，之后再存入（更新操作）
        int accountId = generateAccountId(user.getId());
        userMapper.update(User
                              .builder()
                              .id(user.getId())
                              .accountId(accountId)
                              .build());

        user.setAccountId(accountId);

        // 发布新用户注册事件
        applicationContext.publishEvent(new NewUserRegistrationEvent(user));

        return user;
    }

    /**
     * 通过微信的 openid 获取用户 ID
     *
     * @param openid 微信小程序的用户唯一标识
     *
     * @date 2023/7/31
     * @since 2.0.2
     */
    public int getUserIdByOpenid(String openid) {
        User user = userMapper.selectOneByCondition(UserTableDef.USER.OPENID.eq(openid));

        if (user != null) {
            return user.getId();
        } else {
            // 未找到用户，则说明是新用户，进行注册操作
            return register(openid).getId();
        }
    }

    /**
     * 获取用户账户实体（为找到则报错）
     *
     * @param userId 用户 ID
     *
     * @date 2023/7/31
     * @since 2.0.2
     */
    public User getById(int userId) {
        User user = userMapper.selectOneById(userId);

        if (user != null) {
            return user;
        } else {
            throw UserNotExistException.fromId(userId);
        }
    }
}
