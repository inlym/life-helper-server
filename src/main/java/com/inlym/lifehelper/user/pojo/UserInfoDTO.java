package com.inlym.lifehelper.user.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户个人信息请求数据
 *
 * <h2>数据来源
 *
 * <p>微信小程序端通过 {@code wx.getUserProfile} 方法获取用户个人信息，并直接将该结果返回。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-26
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/user-info/wx.getUserProfile.html">获取用户信息</a>
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/user-info/UserInfo.html">用户信息</a>
 **/
@Data
public class UserInfoDTO {
    /** 用户昵称 */
    @NotEmpty
    private String nickName;

    /** 用户头像图片的 URL */
    @NotEmpty
    private String avatarUrl;
}
