package com.weutil.account.model;

import com.weutil.oss.annotation.OssResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础用户信息视图对象
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserInfoVO {
    /** 昵称 */
    private String nickName;

    /** 头像的完整 URL 地址 */
    @OssResource
    private String avatarUrl;
}
