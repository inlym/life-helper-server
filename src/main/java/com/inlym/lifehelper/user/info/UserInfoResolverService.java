package com.inlym.lifehelper.user.info;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.common.constant.Endpoint;
import com.inlym.lifehelper.common.exception.UnpredictableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户信息辅助类
 *
 * <h2>主要用途
 * <p>将一些繁琐的字段处理方法从主类中剥离出来，放在这里处理，让主类看起来更简洁一点。
 * <p>当前类只做数据格式转换，不处理数据的增删改查。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/3
 * @since 1.7.2
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoResolverService {
    private final OssService ossService;

    /**
     * 计算注册天数
     *
     * @param registerTime 注册时间
     *
     * @since 1.7.2
     */
    public int calcRegisteredDays(LocalDateTime registerTime) {
        LocalDateTime now = LocalDateTime.now();
        return (int) LocalDateTimeUtil
            .between(registerTime, now)
            .toDays();
    }

    /**
     * 获取昵称（若无，则返回默认值）
     *
     * @param nickName 昵称
     *
     * @since 1.7.2
     */
    public String getNickName(String nickName) {
        return nickName != null ? nickName : "小鸣助手用户";
    }

    /**
     * 获取头像的 URL 地址
     *
     * @param avatarPath 头像存储在 OSS 中的路径
     *
     * @since 1.7.2
     */
    public String getAvatarUrl(String avatarPath) {
        return avatarPath != null ? ossService.concatUrl(avatarPath) : Endpoint.LOGO_URL;
    }

    /**
     * 获取性别
     *
     * @param genderType 性别类型
     *
     * @since 1.7.2
     */
    public String getGender(Integer genderType) {
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
