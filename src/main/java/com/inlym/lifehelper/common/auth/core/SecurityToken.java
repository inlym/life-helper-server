package com.inlym.lifehelper.common.auth.core;

import com.inlym.lifehelper.common.constant.SecurityTokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 安全令牌
 *
 * <h2>主要用途
 * <p>用于发放登录鉴权凭证，并告知其如何使用及使用限制。用户登录成功后，就发放这个安全令牌。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/27
 * @since 1.7.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityToken {
    /** 鉴权令牌 */
    private String token;

    /** 鉴权令牌类型 */
    private SecurityTokenType type;

    /** 发起请求时，携带令牌的请求头名称 */
    private String headerName;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 过期时间 */
    private LocalDateTime expireTime;
}
