package com.weutil.external.openai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 创建图片响应
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/22
 * @see <a href="https://platform.openai.com/docs/api-reference/images/create">创建图片</a>
 * @since 2.2.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {
    private Long created;

    private List<ImageData> data;
}
