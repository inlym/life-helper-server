package com.inlym.lifehelper.ai.openai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发生错误时的响应内容
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/22
 * @since 2.2.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private ResponseError error;
}
