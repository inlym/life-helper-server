package com.inlym.lifehelper.account.user.model;

import com.inlym.lifehelper.common.base.aliyun.oss.annotation.OssResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础用户信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/4/16
 * @since 2.3.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserInfo {
    /** 昵称 */
    private String nickName;

    /** 头像地址 */
    @OssResource
    private String avatarUrl;
}
