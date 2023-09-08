package com.inlym.lifehelper.extern.chatgptold.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会话补全接口请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/25
 * @see <a href="https://platform.openai.com/docs/api-reference/completions/create">Create completion</a>
 * @since 1.9.5
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompletionRequestData {
    /** 使用的模型 */
    private String model;

    /** 提示 */
    private String prompt;

    /** 最大令牌数 */
    @JsonProperty("max_tokens")
    private Integer maxTokens;

    private String suffix;
}
