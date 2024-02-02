package com.inlym.lifehelper.user.info.service;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.inlym.lifehelper.common.base.aliyun.oss.OssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.common.exception.UnpredictableException;
import com.inlym.lifehelper.location.region.entity.Region;
import com.inlym.lifehelper.location.region.exception.RegionNotFoundException;
import com.inlym.lifehelper.location.region.service.RegionService;
import com.inlym.lifehelper.user.account.entity.User;
import com.inlym.lifehelper.user.account.service.UserAccountService;
import com.inlym.lifehelper.user.info.entity.UserInfo;
import com.inlym.lifehelper.user.info.pojo.UpdateUserInfoDTO;
import com.inlym.lifehelper.user.info.pojo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息适配器
 *
 * <h2>主要用途
 * <p>对接控制器和服务层，主要做数据格式转换。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/6
 * @since 2.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoAdapter {
    private final OssService ossService;

    private final DefaultUserInfoProvider defaultUserInfoProvider;

    private final UserAccountService userAccountService;

    private final RegionService regionService;

    private final UserInfoService userInfoService;

    /**
     * 更新用户资料
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2023/5/6
     * @since 2.0.0
     */
    public UserInfoVO update(long userId, UpdateUserInfoDTO dto) {
        UserInfo info = UserInfo
            .builder()
            .userId(userId)
            .nickName(dto.getNickName())
            .genderType(dto.getGenderType())
            .cityId(dto.getCityId())
            .build();

        if (dto.getAvatarUrl() != null) {
            info.setAvatarPath(ossService.dump(OssDir.AVATAR, dto.getAvatarUrl()));
        }

        userInfoService.save(info);

        return getMixedUserInfo(userId);
    }

    public UserInfoVO getMixedUserInfo(long userId) {
        User user = userAccountService.getById(userId);
        UserInfo info = userInfoService.get(userId);
        UserInfoVO vo = convert(info);
        vo.setAccountId(user.getAccountId());
        vo.setRegisterTime(user.getRegisterTime());
        vo.setRegisteredDays(calcRegisteredDays(user.getRegisterTime()));

        if (info.getCityId() != null) {
            // 此处查找地区发生错误，不应阻塞主流程，因此使用 `try` 捕获，而不让全局错误捕获器接管
            try {
                List<String> region = new ArrayList<>();

                Region admin2 = regionService.getById(info.getCityId());

                if (admin2.getParentId() != null) {
                    Region admin1 = regionService.getById(admin2.getParentId());

                    vo.setRegionDisplayName(admin1.getShortName() + " " + admin2.getShortName());
                    region.add(admin1.getFullName());
                    region.add(admin2.getFullName());
                } else {
                    vo.setRegionDisplayName(admin2.getShortName());
                    region.add(admin2.getFullName());
                }

                vo.setRegion(region);
            } catch (RegionNotFoundException e) {
                log.trace("地区未找到，未返回地区信息（cityId={}）", info.getCityId());
            }
        }

        return vo;
    }

    /**
     * 将实体对象转化为客户端展示使用的数据格式
     *
     * @param info 实体对象
     *
     * @date 2023/5/6
     * @since 2.0.0
     */
    private UserInfoVO convert(UserInfo info) {
        UserInfoVO vo = new UserInfoVO();

        // 昵称
        vo.setNickName(info.getNickName() != null ? info.getNickName() : defaultUserInfoProvider.getNickName());

        // 头像
        if (info.getAvatarPath() != null) {
            vo.setAvatarUrl(ossService.concatUrl(info.getAvatarPath()));
        } else {
            vo.setAvatarUrl(defaultUserInfoProvider.getAvatarUrl());
        }

        // 性别
        vo.setGender(getGender(info.getGenderType()));

        return vo;
    }

    /**
     * 计算注册天数
     *
     * @param registerTime 注册时间
     *
     * @since 1.7.2
     */
    private int calcRegisteredDays(LocalDateTime registerTime) {
        LocalDateTime now = LocalDateTime.now();
        return (int) LocalDateTimeUtil
            .between(registerTime, now)
            .toDays();
    }

    /**
     * 获取性别
     *
     * @param genderType 性别类型
     *
     * @since 1.7.2
     */
    private String getGender(Integer genderType) {
        final int male = 1;
        final int female = 2;

        if (genderType == null) {
            return "保密";
        } else if (genderType == male) {
            return "男";
        } else if (genderType == female) {
            return "女";
        } else {
            throw new UnpredictableException("未支持的性别类型！");
        }
    }
}
