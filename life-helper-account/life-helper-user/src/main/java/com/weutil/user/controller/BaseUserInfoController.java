package com.weutil.user.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.user.model.BaseUserInfoDTO;
import com.weutil.user.model.BaseUserInfoVO;
import com.weutil.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础用户信息管理控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@RestController
@RequiredArgsConstructor
public class BaseUserInfoController {
    private final UserService userService;

    /**
     * 获取用户基础信息
     *
     * @param userId 用户 ID
     *
     * @date 2024/6/9
     * @since 2.3.0
     */
    @GetMapping("/user-info/base")
    @UserPermission
    public BaseUserInfoVO get(@UserId long userId) {
        return userService.getBaseUserInfo(userId);
    }

    /**
     * 修改用户基础信息
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/6/9
     * @since 2.3.0
     */
    @PutMapping("/user-info/base")
    @UserPermission
    public BaseUserInfoVO update(@UserId long userId, @RequestBody BaseUserInfoDTO dto) {
        userService.updateBaseUserInfo(userId, dto);
        return userService.getBaseUserInfo(userId);
    }
}
