package com.weutil.common.model;

import lombok.Data;

import java.util.List;

/**
 * 单个列表响应数据
 *
 * <h2>说明
 * <p>当响应数据只包含 {@code list} 一个属性时，直接使用当前模型进行封装，避免建立重复数据模型。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@Data
public class SingleListResponse<T> {
    /** 列表数据 */
    private List<T> list;

    public SingleListResponse(List<T> list) {
        this.list = list;
    }
}
