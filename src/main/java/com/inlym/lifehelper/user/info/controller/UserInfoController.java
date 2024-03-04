package com.inlym.lifehelper.user.info.controller;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.base.aliyun.oss.service.OssService;
import com.inlym.lifehelper.user.info.entity.UserInfo;
import com.inlym.lifehelper.user.info.model.BasicUserInfo;
import com.inlym.lifehelper.user.info.model.UpdateUserInfoDTO;
import com.inlym.lifehelper.user.info.model.UserInfoVO;
import com.inlym.lifehelper.user.info.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制器
 *
 * <h2>主要用途
 * <p>管理用户信息修改等接口。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@RestController
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    private final OssService ossService;

    /**
     * 获取基础个人信息
     *
     * @param userId 用户 ID
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    @GetMapping("/userinfo/basic")
    @UserPermission
    public BasicUserInfo getBasicUserInfo(@UserId long userId) {
        UserInfo info = userInfoService.getUserInfo(userId);
        return BasicUserInfo
            .builder()
            .nickName(info.getNickName())
            .avatarUrl(ossService.concatUrl(info.getAvatarPath()))
            .build();
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户 ID
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    @GetMapping("/userinfo")
    @UserPermission
    public UserInfoVO getCompleteUserInfo(@UserId long userId) {
        UserInfo info = userInfoService.getUserInfo(userId);

        return UserInfoVO
            .builder()
            .nickName(info.getNickName())
            .avatarUrl(ossService.concatUrl(info.getAvatarPath()))
            .gender(info.getGenderType())
            .build();
    }

    /**
     * 修改头像
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    @PutMapping("/userinfo/avatar")
    @UserPermission
    public UserInfoVO updateAvatar(@UserId long userId, @RequestBody UpdateUserInfoDTO dto) {
        String avatarUrl = dto.getAvatarUrl();
        return convertEntity(userInfoService.updateAvatar(userId, avatarUrl));
    }

    /**
     * 修改用户信息
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @since 1.7.0
     */
    @PutMapping("/userinfo")
    @UserPermission
    public UserInfoVO update(@UserId long userId, @RequestBody UpdateUserInfoDTO dto) {
        return null;
    }

    /**
     * 将实体对象转换为视图对象
     *
     * @param info 实体对象
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    private UserInfoVO convertEntity(UserInfo info) {
        return UserInfoVO
            .builder()
            .nickName(info.getNickName())
            .avatarUrl(ossService.concatUrl(info.getAvatarPath()))
            .gender(info.getGenderType())
            .build();
    }
}
