package com.inlym.lifehelper.user.info.service;

import com.inlym.lifehelper.common.base.aliyun.oss.constant.AliyunOssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.service.AliyunOssService;
import com.inlym.lifehelper.user.info.constant.GenderType;
import com.inlym.lifehelper.user.info.entity.UserInfo;
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

    private final AliyunOssService aliyunOssService;

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
        return getUserInfo(info.getId());
    }

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
                    .avatarPath(getInitialAvatar())
                    .genderType(GenderType.UNKNOWN)
                    .build();
            userInfoMapper.insertSelective(inserted);
            return inserted;
        }

        return info;
    }

    /**
     * 获取初始头像
     *
     * @return 头像在 OSS 的资源路径
     * @date 2024/3/5
     * @since 2.3.0
     */
    private String getInitialAvatar() {
        // 备注：临时用一个指定路径，后续改为从列表中随机一个
        return "avatar/6ea11e4ffb8146d6b511a8a8f920c4c1.png";
    }

    /**
     * 转储头像
     *
     * @param avatarUrl 头像资源的 URL 地址
     *
     * @return 转储至 OSS 后的资源路径
     * @date 2024/3/4
     * @since 2.3.0
     */
    public String dumpAvatar(String avatarUrl) {
        return aliyunOssService.dump(AliyunOssDir.AVATAR, avatarUrl);
    }
}
