package com.inlym.lifehelper.user;

import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
import com.inlym.lifehelper.user.pojo.UserInfoBO;
import com.inlym.lifehelper.user.pojo.UserInfoDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息控制器
 *
 * @author inlym
 * @date 2022-01-26 20:59
 **/
@RestController
public class UserController {
    private final UserService userService;

    private final HttpServletRequest request;

    public UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @ApiOperation("获取用户个人信息")
    @GetMapping("/userinfo")
    @UserPermission
    public UserInfoBO getUserInfo() {
        int userId = (int) request.getAttribute(CustomRequestAttribute.USER_ID);
        return userService.getUserInfo(userId);
    }

    @ApiOperation("修改用户个人信息")
    @PostMapping("/userinfo")
    @UserPermission
    public UserInfoBO updateUserInfo(@Validated @RequestBody UserInfoDTO dto) {
        int userId = (int) request.getAttribute(CustomRequestAttribute.USER_ID);
        return userService.updateUserInfo(userId, dto);
    }
}
