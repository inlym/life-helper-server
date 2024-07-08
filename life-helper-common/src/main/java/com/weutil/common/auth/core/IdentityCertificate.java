package com.weutil.common.auth.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 身份证书
 * <p>
 * <h2>主要用途
 * <p>在用户鉴权通过后（即完成登录），发放身份证书，用于证明其身份。证书中包含鉴权令牌以及使用方法。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/27
 * @since 1.7.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityCertificate {
    /** 鉴权令牌 */
    private String token;

    /** 发起请求时，携带令牌的请求头名称 */
    private String headerName;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 过期时间 */
    private LocalDateTime expireTime;
}
