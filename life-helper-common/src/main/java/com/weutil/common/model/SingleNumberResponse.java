package com.weutil.common.model;

import lombok.Data;

/**
 * 单个数据项响应数据
 *
 * <h2>说明
 * <p>当响应数据只有一个数据值时，使用当前模型。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2025/1/8
 * @since 3.0.0
 **/
@Data
public class SingleNumberResponse {
    /** 数据值 */
    private Long num;

    public SingleNumberResponse(long num) {
        this.num = num;
    }
}
