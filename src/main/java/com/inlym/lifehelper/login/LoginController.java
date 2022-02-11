package com.inlym.lifehelper.login;

import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
import com.inlym.lifehelper.login.dto.LoginByCodeDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author inlym
 * @date 2022-01-23 01:13
 **/
@RestController
@Validated
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {this.loginService = loginService;}

    @ApiOperation("通过微信获取的 code 登录")
    @PostMapping("/login/weixin")
    public Map<String, Object> loginByCode(@Validated @RequestBody LoginByCodeDTO data) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("token", loginService.loginByCode(data.getCode()));
        map.put("expiration", System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000);

        return map;
    }

    /**
     * 开发者登录，用于获取开发者角色
     * <p>
     * [使用说明]
     * 正常登录后，再访问以下这个接口就可以了。
     */
    @PostMapping("/login/developer")
    @UserPermission
    public Map<String, String> loginForDeveloper(HttpServletRequest request) {
        int userId = (int) request.getAttribute(CustomRequestAttribute.USER_ID);
        Map<String, String> map = new HashMap<>(16);
        map.put("token", loginService.loginForDeveloper(userId));

        return map;
    }
}
