package com.weutil.external.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用量统计
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/20
 * @since 2.1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usage {
    /** 补全内容的 token 数 */
    @JsonProperty("completion_tokens")
    private Integer completionTokens;

    /** 提示词的 token 数 */
    @JsonProperty("prompt_tokens")
    private Integer promptTokens;

    /** 累计 token 数 */
    @JsonProperty("total_tokens")
    private Integer totalTokens;
}
