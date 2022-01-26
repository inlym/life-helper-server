package com.inlym.lifehelper.user;

import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制器
 *
 * @author inlym
 * @date 2022-01-26 20:59
 **/
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}
}
