package com.inlym.lifehelper.user.info.service;

import com.inlym.lifehelper.user.info.entity.UserInfo;
import com.inlym.lifehelper.user.info.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.inlym.lifehelper.user.info.entity.table.UserInfoTableDef.USER_INFO;

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
    private final UserInfoMapper userInfoMapper;

    /**
     * 获取用户资料
     *
     * @param userId 用户 ID
     *
     * @date 2023/5/6
     * @since 2.0.0
     */
    public UserInfo get(long userId) {
        return userInfoMapper.selectOneByCondition(USER_INFO.USER_ID.eq(userId));
    }

    /**
     * 保存用户资料
     *
     * @param info 用户资料实体对象
     *
     * @date 2023/5/6, 2024/2/16
     * @since 2.0.0
     */
    public void save(UserInfo info) {
        // 先检测数据表中是否已有记录，若无则创建
        UserInfo userInfo = userInfoMapper.selectOneByCondition(USER_INFO.USER_ID.eq(info.getUserId()));
        if (userInfo != null) {
            info.setId(userInfo.getId());
            userInfoMapper.update(info);
        } else {
            userInfoMapper.insertSelective(info);
        }
    }
}
