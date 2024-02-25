package com.inlym.lifehelper.extern.openai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应异常数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/22
 * @since 2.2.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
    private String message;

    private String type;

    private String param;

    private String code;
}
