package com.inlym.lifehelper.user.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * 用户个人信息
 * <p>
 * [说明]
 * 业务层从数据库获取并完成数据处理后的信息
 *
 * @author inlym
 * @date 2022-02-13 15:32
 **/
@Data
@Builder
public class UserInfoBO {
    /** 用户昵称 */
    private String nickName;

    /** 用户头像图片的 URL */
    private String avatarUrl;
}
