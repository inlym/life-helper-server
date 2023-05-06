package com.inlym.lifehelper.user.info.service;

import com.inlym.lifehelper.user.info.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    /**
     * 获取用户资料
     *
     * @param userId 用户 ID
     *
     * @date 2023/5/6
     * @since 2.0.0
     */
    public UserInfo get(int userId) {
        return userInfoRepository.findByUserId(userId);
    }

    /**
     * 保存用户资料
     *
     * @param info 用户资料实体对象
     *
     * @date 2023/5/6
     * @since 2.0.0
     */
    public void save(UserInfo info) {
        userInfoRepository.save(info);
    }
}
