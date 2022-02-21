package com.inlym.lifehelper.user.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户个人信息
 *
 * @author inlym
 * @apiNote 数据来源与微信小程序接口获取
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
