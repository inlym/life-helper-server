package com.inlym.lifehelper.user.info.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    /** 生日 */
    private LocalDate birthday;

    /**
     * 性别
     *
     * <h2>值范围
     * <li>[1] - 男
     * <li>[2] - 女
     */
    private Integer genderType;

    /** 所在城市的 adcode */
    private Integer cityId;
}
