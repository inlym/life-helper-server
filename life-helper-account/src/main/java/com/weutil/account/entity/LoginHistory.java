package com.weutil.account.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.weutil.account.constant.LoginChannel;
import com.weutil.account.constant.LoginMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 登录历史记录
 *
 * <h2>说明
 * <p>原本每种登录方式分别一张表，每张表有80%左右的字段重合，为方便统计和使用，合并到一张表上。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/8/28
 * @since 3.0.0
 **/
@Table("login_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistory {
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

    // ---------- 各种登录方式通用项 ----------

    /** 登录方式 */
    private LoginMethod loginMethod;

    /** 登录渠道 */
    private LoginChannel loginChannel;

    /** 对应的用户 ID */
    private Long userId;

    /** 发放的鉴权令牌 */
    private String token;

    /** 客户端 IP 地址 */
    private String ip;

    /** 登录时间 */
    private LocalDateTime loginTime;

    // ---------- 各种登录方式差异项 ----------

    // --- 通过手机号系列方式登录 ---

    /** 关联的用户手机号账户表 ID */
    private Long phoneAccountId;
}
