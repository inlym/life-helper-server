package com.inlym.lifehelper.user.account.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户账户实体
 * <p>
 * <h2>主要用途
 * <p>仅包含用户账户权限相关字段。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Table("user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /** 用户 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 微信小程序用户唯一标识 */
    private String openid;

    /**
     * 账户 ID
     * <p>
     * <h2>主要用途
     * <p>给用户展示的 ID。
     */
    private Long accountId;

    /** 注册时间 */
    private LocalDateTime registerTime;
}
