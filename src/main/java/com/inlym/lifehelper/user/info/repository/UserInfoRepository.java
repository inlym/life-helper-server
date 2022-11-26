package com.inlym.lifehelper.user.info.repository;

import com.inlym.lifehelper.common.base.aliyun.ots.widecolumn.WideColumnExecutor;
import com.inlym.lifehelper.user.info.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 用户信息存储库
 *
 * <h2>主要用途
 * <p>管理用户信息的增删改查操作。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserInfoRepository {
    private final WideColumnExecutor wideColumnExecutor;

    /**
     * 保存用户信息
     *
     * @param info 用户信息
     *
     * @since 1.7.0
     */
    public void save(UserInfo info) {
        wideColumnExecutor.update(info);
    }

    /**
     * 通过用户 ID 获取用户信息
     *
     * @param userId 用户 ID
     *
     * @since 1.7.0
     */
    public UserInfo findByUserId(int userId) {
        UserInfo info = UserInfo
            .builder()
            .userId(userId)
            .build();

        return wideColumnExecutor.findOne(info, UserInfo.class);
    }
}
