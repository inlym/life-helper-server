package com.weutil.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访问凭证详情
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDetail {
    /** 用户 ID */
    private Long userId;
}
