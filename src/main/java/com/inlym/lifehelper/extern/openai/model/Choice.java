package com.inlym.lifehelper.extern.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类名称
 * <p>
 * <h2>主要用途
 * <p>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/20
 * @since 2.1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    /** 结束原因 */
    @JsonProperty("finish_reason")
    private ChatCompletionFinishReason finishReason;

    /** 索引，从 0 开始 */
    private Integer index;

    /** 会话补全的回复消息 */
    private ChatCompletionMessage message;

    private LogProbs logprobs;
}
