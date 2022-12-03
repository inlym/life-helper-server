package com.inlym.lifehelper.user.info;

import com.inlym.lifehelper.common.base.aliyun.oss.OssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.location.region.RegionService;
import com.inlym.lifehelper.location.region.entity.Region;
import com.inlym.lifehelper.location.region.exception.RegionNotFoundException;
import com.inlym.lifehelper.user.account.UserAccountService;
import com.inlym.lifehelper.user.account.entity.User;
import com.inlym.lifehelper.user.info.entity.UserInfo;
import com.inlym.lifehelper.user.info.pojo.UpdateUserInfoDTO;
import com.inlym.lifehelper.user.info.pojo.UserInfoVO;
import com.inlym.lifehelper.user.info.repository.UserInfoRepository;
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
    private final UserAccountService userAccountService;

    private final UserInfoRepository userInfoRepository;

    private final UserInfoResolverService userInfoResolverService;

    private final OssService ossService;

    private final RegionService regionService;

    /**
     * 合成用户客户端展示使用的用户信息（混合了用户账户信息）
     *
     * @param userId 用户 ID
     *
     * @since 1.7.2
     */
    public UserInfoVO getDisplayUserInfo(int userId) {
        User user = userAccountService.findById(userId);

        UserInfoVO vo = UserInfoVO
            .builder()
            .accountId(user.getAccountId())
            .registerTime(user.getRegisterTime())
            .registeredDays(userInfoResolverService.calcRegisteredDays(user.getRegisterTime()))
            .build();

        UserInfo info = userInfoRepository.findByUserId(userId);
        if (info == null) {
            info = new UserInfo();
        }

        vo.setNickName(userInfoResolverService.getNickName(info.getNickName()));
        vo.setAvatarUrl(userInfoResolverService.getAvatarUrl(info.getAvatarPath()));
        vo.setGender(userInfoResolverService.getGender(info.getGenderType()));

        if (info.getCityId() != null) {
            // 此处查找地区发生错误，不应阻塞主流程，因此使用 `try` 捕获，而不让全局错误捕获器接管
            try {
                Region admin2 = regionService.getById(info.getCityId());
                Region admin1 = regionService.getById(admin2.getParentId());
                vo.setRegion(userInfoResolverService.getUserRegion(admin1, admin2));
            } catch (RegionNotFoundException e) {
                log.trace("地区未找到，未返回地区信息（cityId={}）", info.getCityId());
            }
        }

        return vo;
    }

    /**
     * 修改用户信息
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @since 1.7.2
     */
    public void updateUserInfo(int userId, UpdateUserInfoDTO dto) {
        UserInfo info = UserInfo
            .builder()
            .userId(userId)
            .nickName(dto.getNickName())
            .birthday(dto.getBirthday())
            .genderType(dto.getGenderType())
            .cityId(dto.getCityId())
            .build();

        if (dto.getAvatarUrl() != null) {
            info.setAvatarPath(ossService.dump(OssDir.AVATAR, dto.getAvatarUrl()));
        }

        userInfoRepository.save(info);
    }
}
