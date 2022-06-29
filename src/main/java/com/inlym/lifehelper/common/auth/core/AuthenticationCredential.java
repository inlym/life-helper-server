package com.inlym.lifehelper.common.auth.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 鉴权凭证
 *
 * <h2>主要用途
 * <p>当用户登录鉴权通过后，发送该鉴权凭证用于客户端后续接口访问。其中最主要的内容是“鉴权令牌”，其余字段用于描述鉴权令牌的属性。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/30
 * @since 1.3.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationCredential {
    /** 鉴权令牌 */
    private String token;

    /** 鉴权令牌类型 */
    private String type;

    /** 创建时间（时间戳） */
    private Long createTime;

    /** 到期时间（时间戳） */
    private Long expireTime;

    /** 鉴权令牌类型 */
    public static abstract class Types {
        /** JSON Web Token */
        public static final String JWT = "jwt";

        /**
         * 简单令牌
         *
         * <h2>说明
         * <p>即在 Redis 中存储对应的用户 ID 等内容。
         */
        public static final String SIMPLE_TOKEN = "simple-token";
    }
}
