package com.weutil.external.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建图片请求参数
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/22
 * @see <a href="https://platform.openai.com/docs/api-reference/images/create">Create image</a>
 * @since 2.2.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest {
    private String prompt;

    private String model;

    private Integer n;

    private String quality;

    @JsonProperty("response_format")
    private String responseFormat;

    private String size;

    private String style;

    private String user;
}
