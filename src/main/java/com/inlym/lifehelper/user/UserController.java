package com.inlym.lifehelper.user;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.user.pojo.UserInfoBO;
import com.inlym.lifehelper.user.pojo.UserInfoDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制器
 *
 * @author inlym
 * @date 2022-01-26
 **/
@RestController
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("获取用户个人信息")
    @GetMapping("/userinfo")
    @UserPermission
    public UserInfoBO getUserInfo(@UserId int userId) {
        return userService.getUserInfo(userId);
    }

    @ApiOperation("修改用户个人信息")
    @PutMapping("/userinfo")
    @UserPermission
    public UserInfoBO updateUserInfo(@Validated @RequestBody UserInfoDTO dto, @UserId int userId) {
        return userService.updateUserInfo(userId, dto);
    }
}
