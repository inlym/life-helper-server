package com.inlym.lifehelper.user.info.entity;

import com.inlym.lifehelper.user.info.constant.GenderType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户信息实体
 *
 * <h2>主要用途
 * <p>用于存储可由用户自主修改的信息。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Table("user_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    /** 主键 ID（用户 ID） */
    @Id(keyType = KeyType.None)
    private Long id;

    /** 用户昵称 */
    private String nickName;

    /**
     * 用户头像图片路径
     *
     * <h2>说明
     * <p>从微信侧获取的头像 URL，会将图片转储到我方 OSS 中，最终存储的是在 OSS 中的路径。
     */
    private String avatarPath;

    /**
     * 性别
     *
     * <h2>值范围
     * <li>[0] - 未知
     * <li>[1] - 男
     * <li>[2] - 女
     */
    private GenderType genderType;

    /** 创建时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime createTime;

    /** 更新时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime updateTime;
}
