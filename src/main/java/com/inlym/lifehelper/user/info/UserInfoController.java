package com.inlym.lifehelper.user.info;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.user.info.pojo.UpdateUserInfoDTO;
import com.inlym.lifehelper.user.info.pojo.UserInfoVO;
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

    /**
     * 获取用户信息
     *
     * @param userId 用户 ID
     *
     * @since 1.7.0
     */
    @GetMapping("/userinfo")
    @UserPermission
    public UserInfoVO get(@UserId int userId) {
        return userInfoService.getViewObject(userId);
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
    public UserInfoVO update(@UserId int userId, @RequestBody UpdateUserInfoDTO dto) {
        userInfoService.resolveModifiedUserInfo(userId, dto);
        return userInfoService.getViewObject(userId);
    }
}
