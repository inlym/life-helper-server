package com.inlym.lifehelper.user.info.controller;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.user.info.pojo.UpdateUserInfoDTO;
import com.inlym.lifehelper.user.info.pojo.UserInfoVO;
import com.inlym.lifehelper.user.info.service.UserInfoAdapter;
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
    private final UserInfoAdapter userInfoAdapter;

    /**
     * 获取用户信息
     *
     * @param userId 用户 ID
     *
     * @since 1.7.0
     */
    @GetMapping("/userinfo")
    @UserPermission
    public UserInfoVO get(@UserId long userId) {
        return userInfoAdapter.getMixedUserInfo(userId);
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
        return userInfoAdapter.update(userId, dto);
    }
}
