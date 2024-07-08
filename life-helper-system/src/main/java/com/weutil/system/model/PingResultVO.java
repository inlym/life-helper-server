package com.weutil.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 连通性测试结果视图对象
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/30
 * @since 2.3.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PingResultVO {
    /** 延迟时长，单位：毫秒 */
    private Long delay;
}
