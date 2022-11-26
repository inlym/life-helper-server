package com.inlym.lifehelper.user.info.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 修改用户信息请求数据
 *
 * <h2>说明
 * <p>各处需要修改用户信息的 API 均可使用当前对象，只赋值需要修改的字段即可。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInfoDTO {
    /** 用户昵称 */
    private String nickName;

    /** 头像图片的 URL 地址 */
    private String avatarUrl;
}
