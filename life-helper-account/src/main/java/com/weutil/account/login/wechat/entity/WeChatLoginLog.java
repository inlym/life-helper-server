package com.weutil.account.login.wechat.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.weutil.account.login.common.model.LoginLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 微信账户登录日志表
 *
 * <h2>主要用途
 * <p>记录使用微信账户的方式进行登录的行为。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/10
 * @since 2.3.0
 **/
@Table("login_wechat_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeChatLoginLog implements LoginLog {
    // ============================ 通用字段 ============================

    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    // 字段说明：因为是“日志表”，因此此处无“逻辑删除标志”字段

    // ============================ 业务字段 ============================

    /** 微信小程序端通过 `wx.login` 获取的 code */
    private String code;

    /** 小程序开发者 ID */
    private String appId;

    /** 小程序的用户唯一标识 */
    private String openId;

    /** 用户在微信开放平台的唯一标识符 */
    private String unionId;

    /** 会话密钥 */
    private String sessionKey;

    /** 关联的用户微信账户表 ID */
    private Long userAccountWeChatId;

    /** 对应的用户 ID */
    private Long userId;

    /** 鉴权令牌 */
    private String token;

    /** 客户端 IP 地址 */
    private String ip;

    /** 登录时间 */
    private LocalDateTime loginTime;
}
