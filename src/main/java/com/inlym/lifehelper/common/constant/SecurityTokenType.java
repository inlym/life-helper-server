package com.inlym.lifehelper.common.constant;

/**
 * 安全令牌类型
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/27
 * @since 1.7.0
 **/
public enum SecurityTokenType {
    /**
     * JSON Web Token
     *
     * @see <a href="https://jwt.io/">JWT</a>
     */
    JSON_WEB_TOKEN,

    /**
     * 简易令牌，即传统意义上的 `session_id`
     */
    SIMPLE_TOKEN;

    SecurityTokenType() {}
}
