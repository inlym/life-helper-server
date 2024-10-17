package com.weutil.account.service;

import com.weutil.account.entity.User;
import com.weutil.account.event.UserCreatedEvent;
import com.weutil.account.exception.UserNotExistException;
import com.weutil.account.mapper.UserMapper;
import com.weutil.account.model.BaseUserInfoDTO;
import com.weutil.account.model.BaseUserInfoVO;
import com.weutil.common.util.RandomStringUtil;
import com.weutil.oss.model.OssDir;
import com.weutil.oss.service.OssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

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
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserMapper userMapper;
    private final OssService ossService;

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
     * 获取用户基础信息
     *
     * @param userId 用户 ID
     *
     * @date 2024/6/9
     * @since 2.3.0
     */
    public BaseUserInfoVO getBaseUserInfo(long userId) {
        User user = getOrThrowByUserId(userId);
        return BaseUserInfoVO.builder().nickName(user.getNickName()).avatarUrl(user.getAvatarPath()).build();
    }

    /**
     * 通过用户 ID 获取用户信息
     *
     * @param userId 用户 ID
     *
     * @date 2024/09/01
     * @since 3.0.0
     */
    private User getOrThrowByUserId(long userId) {
        User user = userMapper.selectOneById(userId);
        if (user != null) {
            return user;
        }
        throw new UserNotExistException();
    }

    /**
     * 更新用户基础信息
     *
     * @param userId 用户 ID
     * @param dto    用户基础信息请求数据
     *
     * @date 2024/6/9
     * @since 2.3.0
     */
    public void updateBaseUserInfo(long userId, BaseUserInfoDTO dto) {
        User updated = User.builder().id(userId).build();

        // 处理昵称
        if (dto.getNickName() != null) {
            updated.setNickName(dto.getNickName());
        }

        // 处理头像
        if (dto.getAvatarKey() != null) {
            // 将图片资源复制到“头像”专用目录下
            String newAvatarKey = ossService.copyInternal(dto.getAvatarKey(), OssDir.AVATAR);
            updated.setAvatarPath(newAvatarKey);
        }

        userMapper.update(updated);
    }
}
