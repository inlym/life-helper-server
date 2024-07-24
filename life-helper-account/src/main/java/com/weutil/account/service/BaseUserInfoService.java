package com.weutil.account.service;

import com.weutil.account.entity.User;
import com.weutil.account.mapper.UserMapper;
import com.weutil.account.model.BaseUserInfoDTO;
import com.weutil.account.model.BaseUserInfoVO;
import com.weutil.oss.model.OssDir;
import com.weutil.oss.service.OssService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 基础用户信息服务
 *
 * <h2>说明
 * <p>将昵称和头像这两个用户信息单独封装处理。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Service
@RequiredArgsConstructor
public class BaseUserInfoService {
    private final UserMapper userMapper;
    private final OssService ossService;

    /**
     * 更新用户基础信息
     *
     * @param userId 用户 ID
     * @param dto    用户基础信息请求数据
     *
     * @date 2024/6/9
     * @since 2.3.0
     */
    public void update(long userId, BaseUserInfoDTO dto) {
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

    /**
     * 获取用户基础信息
     *
     * @param userId 用户 ID
     *
     * @date 2024/6/9
     * @since 2.3.0
     */
    public BaseUserInfoVO get(long userId) {
        User user = userMapper.selectOneById(userId);
        return BaseUserInfoVO.builder().nickName(user.getNickName()).avatarUrl(user.getAvatarPath()).build();
    }
}
