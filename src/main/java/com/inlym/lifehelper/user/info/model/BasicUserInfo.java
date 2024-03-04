package com.inlym.lifehelper.user.info.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基本用户信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/3/1
 * @since 2.3.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicUserInfo {
    /** 用户昵称 */
    private String nickName;

    /** 用户头像图片 URL 地址 */
    private String avatarUrl;
}
