package com.inlym.lifehelper.user.info.entity;

import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息实体
 *
 * <h2>主要用途
 * <p>用于存储可由用户自主修改的信息。
 *
 * <h2>存储说明
 * <p>该实体对象存储在表格存储的宽列模型中。。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    // ================================= 主键列 =================================

    /** 所属用户 ID - 分区键 */
    @PrimaryKeyField(name = "uid", order = 1, hashed = true)
    private Integer userId;

    // ================================= 属性列 =================================

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
     * <li>[1] - 男
     * <li>[2] - 女
     *
     * <h2>说明
     * <p>为空表示“未知”。
     */
    private Integer genderType;

    /** 所在城市的 adcode */
    private Integer cityId;
}
