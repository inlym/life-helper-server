package com.inlym.lifehelper.user;

import com.inlym.lifehelper.external.oss.OssService;
import com.inlym.lifehelper.user.entity.User;
import com.inlym.lifehelper.user.mapper.UserMapper;
import com.inlym.lifehelper.user.pojo.UserInfoBO;
import com.inlym.lifehelper.user.pojo.UserInfoDTO;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户账户服务类
 *
 * @author inlym
 * @date 2022-01-23
 */
@Service
@Slf4j
public class UserService {
    private final UserMapper userMapper;

    private final OssService ossService;

    public UserService(UserMapper userMapper, OssService ossService) {
        this.userMapper = userMapper;
        this.ossService = ossService;
    }

    /**
     * 通过 openid 获取用户 ID（用户不存在时将自动创建用户）
     *
     * @param openid 微信小程序用户唯一标识
     */
    public int getUserIdByOpenid(@NonNull String openid) {
        User user = userMapper.findByOpenid(openid);
        if (user != null) {
            return user.getId();
        } else {
            User newUser = User
                .builder()
                .openid(openid)
                .build();

            userMapper.insert(newUser);
            log.info("新注册用户：{}", newUser);

            return newUser.getId();
        }
    }

    /**
     * 获取用户信息
     *
     * @param id 用户 ID
     */
    @SneakyThrows
    public UserInfoBO getUserInfo(int id) {
        User user = userMapper.findById(id);
        if (user != null) {
            return UserInfoBO
                .builder()
                .nickName(user.getNickName())
                .avatarUrl(ossService.concatUrl(user.getAvatar()))
                .build();
        }

        throw new Exception("查找的用户不存在");
    }

    /**
     * 更新用户信息（昵称和头像）
     *
     * @param userId 用户 ID
     * @param dto    前端传输的用户信息
     */
    public UserInfoBO updateUserInfo(int userId, UserInfoDTO dto) {
        User user = User
            .builder()
            .id(userId)
            .nickName(dto.getNickName())
            .avatar(ossService.dump(OssService.AVATAR_DIR, dto.getAvatarUrl()))
            .build();

        userMapper.update(user);

        return UserInfoBO
            .builder()
            .nickName(user.getNickName())
            .avatarUrl(ossService.concatUrl(user.getAvatar()))
            .build();
    }
}
