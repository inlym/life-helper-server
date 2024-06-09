package com.inlym.lifehelper.login.wechat2.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 微信登录记录日志
 *
 * <h2>主要用途
 * <p>日志表，记录每一次微信登录行为。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/15
 * @since 2.1.0
 **/
@Table("login_wechat_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeChatLoginLog {
    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 微信小程序端通过 `wx.login` 获取的 code */
    private String code;

    /** 小程序的 appId */
    private String appId;

    /** 用户在小程序的唯一标识 */
    private String openId;

    /** 用户在微信开放平台的唯一标识 */
    private String unionId;

    /** 会话密钥 */
    private String sessionKey;

    /** 用户 ID */
    private Long userId;

    /** 鉴权令牌 */
    private String token;

    /** 客户端 IP 地址 */
    private String ip;

    /** 登录时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime loginTime;

    /** 创建时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime createTime;

    /** 更新时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime updateTime;
}
