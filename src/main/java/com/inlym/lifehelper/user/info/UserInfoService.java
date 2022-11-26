package com.inlym.lifehelper.user.info;

import com.inlym.lifehelper.common.base.aliyun.oss.OssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.common.constant.Endpoint;
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
    private final UserInfoRepository userInfoRepository;

    private final OssService ossService;

    /**
     * 处理头像，返回在 OSS 中存储的路径
     *
     * @param avatarUrl 头像图片的 URL 地址
     *
     * @since 1.7.0
     */
    private String resolveAvatar(String avatarUrl) {
        return ossService.dump(OssDir.AVATAR, avatarUrl);
    }

    /**
     * 处理待修改的用户信息（主要做数据转换）
     *
     * @param dto 请求数据
     *
     * @since 1.7.0
     */
    public void resolveModifiedUserInfo(int userId, UpdateUserInfoDTO dto) {
        UserInfo info = UserInfo
            .builder()
            .userId(userId)
            .nickName(dto.getNickName())
            .avatarPath(resolveAvatar(dto.getAvatarUrl()))
            .build();

        userInfoRepository.save(info);
    }

    /**
     * 将实体对象转换为视图对象
     *
     * @param info 实体对象
     *
     * @since 1.7.0
     */
    public UserInfoVO convertToViewObject(UserInfo info) {
        return UserInfoVO
            .builder()
            .nickName(info.getNickName())
            .avatarUrl(ossService.concatUrl(info.getAvatarPath()))
            .build();
    }

    /**
     * 获取用户信息试图对象
     *
     * @param userId 用户 ID
     *
     * @since 1.7.0
     */
    public UserInfoVO getViewObject(int userId) {
        UserInfo info = userInfoRepository.findByUserId(userId);
        if (info != null) {
            return convertToViewObject(info);
        } else {
            return UserInfoVO
                .builder()
                .nickName("小鸣助手用户")
                .avatarUrl(Endpoint.LOGO_URL)
                .build();
        }
    }
}
