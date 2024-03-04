package com.inlym.lifehelper.user.info.service;

import com.inlym.lifehelper.common.base.aliyun.oss.constant.AliyunOssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.service.OssService;
import com.inlym.lifehelper.user.info.constant.GenderType;
import com.inlym.lifehelper.user.info.entity.UserInfo;
import com.inlym.lifehelper.user.info.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoMapper userInfoMapper;

    private final OssService ossService;

    /**
     * 获取用户信息
     *
     * @param userId 用户 ID
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    public UserInfo getUserInfo(long userId) {
        UserInfo info = userInfoMapper.selectOneById(userId);
        if (info == null) {
            // 资料为空时
            UserInfo inserted = UserInfo
                .builder()
                .id(userId)
                .nickName("小鸣AI用户")
                .genderType(GenderType.UNKNOWN)
                .build();
            userInfoMapper.insertSelective(inserted);
            return inserted;
        }

        return info;
    }

    /**
     * 更新用户信息
     *
     * @param info 用户信息
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    public UserInfo updateUserInfo(UserInfo info) {
        userInfoMapper.update(info);
        return info;
    }

    /**
     * 更新头像
     *
     * @param userId    用户 ID
     * @param avatarUrl 头像资源 URL 地址
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    public UserInfo updateAvatar(long userId, String avatarUrl) {
        String path = ossService.dump(AliyunOssDir.AVATAR, avatarUrl);
        UserInfo update = UserInfo
            .builder()
            .id(userId)
            .avatarPath(path)
            .build();
        userInfoMapper.update(update);
        return userInfoMapper.selectOneById(userId);
    }
}
