package com.inlym.lifehelper.login;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author inlym
 * @since 2022-01-23 01:13
 **/
@RestController
@Validated
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {this.loginService = loginService;}

    @GetMapping("/login/weixin")
    public Object loginByCode(@NotEmpty @RequestParam("code") String code) {
        Map<String, String> map = new HashMap<>(16);
        map.put("token", loginService.loginByCode(code));

        return map;
    }
}
