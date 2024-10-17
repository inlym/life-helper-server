package com.weutil.account.model;

import lombok.Data;

/**
 * 基础用户信息 请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/24
 * @since 3.0.0
 **/
@Data
public class BaseUserInfoDTO {
    /** 昵称 */
    private String nickName;

    /** 头像资源在 OSS 的存储路径 */
    private String avatarKey;
}
