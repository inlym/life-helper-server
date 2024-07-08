package com.weutil.common.model;

import lombok.Data;

import java.util.List;

/**
 * 通用列表数据响应
 *
 * <h2>主要用途
 * <p>当响应数据只有一个列表时，为避免建立多个 VO 类，使用这个通用类。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/12
 * @since 1.8.0
 **/
@Data
public class CommonListResponse<T> {
    /** 列表数据 */
    private List<T> list;

    public CommonListResponse(List<T> list) {
        this.list = list;
    }
}
