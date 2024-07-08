package com.weutil.external.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 会话补全接口停止原因
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/19
 * @since 2.1.0
 **/
public enum ChatCompletionFinishReason {

    @JsonProperty("stop") STOP,

    /** 达到最大令牌数 */
    @JsonProperty("length") LENGTH,

    @JsonProperty("content_filter") CONTENT_FILTER,

    @JsonProperty("tool_calls") TOOL_CALLS,

    @JsonProperty("function_call") FUNCTION_CALL
}
