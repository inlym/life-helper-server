package com.inlym.lifehelper.user;

import com.inlym.lifehelper.user.entity.User;
import com.inlym.lifehelper.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {this.userMapper = userMapper;}

    /**
     * 通过 openid 获取用户 ID（用户不存在时将自动创建用户）
     *
     * @param openid 微信小程序用户唯一标识
     */
    public int getUserIdByOpenid(String openid) {
        User user = userMapper.findUserByOpenid(openid);
        if (user != null) {
            return user.getId();
        } else {
            User newUser = new User();
            newUser.setOpenid(openid);
            userMapper.insertWithOpenid(newUser);
            return newUser.getId();
        }
    }
}
