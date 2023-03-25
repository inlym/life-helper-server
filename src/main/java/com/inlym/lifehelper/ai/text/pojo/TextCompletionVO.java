package com.inlym.lifehelper.ai.text.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文本补全响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/25
 * @since 1.9.5
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextCompletionVO {
    /** 回复文本 */
    private String text;
}
