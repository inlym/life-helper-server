package com.inlym.lifehelper.user.account.service;

import com.inlym.lifehelper.user.account.entity.User;
import com.inlym.lifehelper.user.account.event.NewUserRegistrationEvent;
import com.inlym.lifehelper.user.account.exception.UserNotExistException;
import com.inlym.lifehelper.user.account.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import static com.inlym.lifehelper.user.account.entity.table.UserTableDef.USER;

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
     * 使用微信的 unionId 进行注册
     *
     * @param unionId 用户在微信开放平台的唯一标识符
     *
     * @date 2023/7/31
     * @since 2.0.2
     */
    public User register(String unionId) {
        User user = User
            .builder()
            .unionId(unionId)
            .build();

        // 将数据存入，框架将自动填充 `id` 字段
        userMapper.insertSelective(user);
        log.info("[新用户注册] userId={}, unionId={}", user.getId(), user.getUnionId());

        // 此处重新查一遍的原因是：
        // 后续新增字段可能存在默认值，直接使用上述 `user` 可能不包含这些值
        User newUser = userMapper.selectOneById(user.getId());

        // 发布新用户注册事件
        applicationContext.publishEvent(new NewUserRegistrationEvent(newUser));

        return newUser;
    }

    /**
     * 通过微信的 unionId 获取用户 ID
     *
     * @param unionId 用户在微信开放平台的唯一标识符
     *
     * @date 2024/02/15
     * @since 2.0.2
     */
    public long getUserIdByUnionId(String unionId) {
        User user = userMapper.selectOneByCondition(USER.UNION_ID.eq(unionId));

        if (user != null) {
            return user.getId();
        } else {
            // 未找到用户，则说明是新用户，进行注册操作
            return register(unionId).getId();
        }
    }

    /**
     * 获取用户账户实体（未找到则报错）
     *
     * @param userId 用户 ID
     *
     * @date 2023/7/31
     * @since 2.0.2
     */
    public User getById(long userId) {
        User user = userMapper.selectOneById(userId);

        if (user != null) {
            return user;
        } else {
            throw UserNotExistException.fromId(userId);
        }
    }
}
