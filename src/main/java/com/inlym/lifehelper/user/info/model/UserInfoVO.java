package com.inlym.lifehelper.user.info.model;

import com.inlym.lifehelper.user.info.constant.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息视图对象
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {
    /** 用户昵称 */
    private String nickName;

    /** 头像图片的 URL 地址 */
    private String avatarUrl;

    /** 性别：男、女、未知 */
    private GenderType gender;
}
