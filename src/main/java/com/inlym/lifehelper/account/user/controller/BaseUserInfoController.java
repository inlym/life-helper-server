package com.inlym.lifehelper.account.user.controller;

import com.inlym.lifehelper.account.user.model.BaseUserInfo;
import com.inlym.lifehelper.account.user.service.BaseUserInfoService;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础用户信息管理控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/9
 * @since 2.3.0
 **/
@RestController
@RequiredArgsConstructor
public class BaseUserInfoController {
    private final BaseUserInfoService baseUserInfoService;

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
    public BaseUserInfo get(@UserId long userId) {
        return baseUserInfoService.get(userId);
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
    public BaseUserInfo update(@UserId long userId, @RequestBody BaseUserInfo dto) {
        baseUserInfoService.update(userId, dto);
        return baseUserInfoService.get(userId);
    }
}
