package com.inlym.lifehelper.user;

import com.inlym.lifehelper.user.entity.User;
import com.inlym.lifehelper.user.mapper.UserMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户账户服务类
 *
 * @author inlym
 * @since 2022-01-23 00:33
 */
@Service
@Slf4j
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {this.userMapper = userMapper;}

    /**
     * 通过 openid 获取用户 ID（用户不存在时将自动创建用户）
     *
     * @param openid 微信小程序用户唯一标识
     */
    public int getUserIdByOpenid(@NonNull String openid) {
        User user = userMapper.findUserByOpenid(openid);
        if (user != null) {
            return user.getId();
        } else {
            User newUser = new User();
            newUser.setOpenid(openid);
            userMapper.insertWithOpenid(newUser);
            log.info("新注册用户：" + newUser);

            return newUser.getId();
        }
    }

    /**
     * 更新用户信息（昵称和头像）
     *
     * @param user 用户实体
     */
    public void updateUserInfo(User user) {
        User user1 = new User();
        user1.setId(user.getId());
        user1.setNickName(user.getNickName());
        user1.setAvatar(user.getAvatar());

        userMapper.update(user1);
    }
}
