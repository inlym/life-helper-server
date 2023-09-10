package com.inlym.lifehelper.extern.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 会话补全响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/9
 * @see <a href="https://platform.openai.com/docs/api-reference/chat/object">会话补全响应数据</a>
 * @since 2.0.3
 **/
@Data
public class ChatCompletionResponse {
    /** 唯一 ID */
    private String id;

    /** 对象类型，固定为 `chat.completion` */
    private String object;

    /** 创建时间的时间戳（秒） */
    private Integer created;

    /** 使用的模型 */
    private String model;

    /** 会话补全选项列表 */
    private Choice[] choices;

    /** 接口使用量 */
    private Usage usage;

    @Data
    public static class Message {
        /** 角色，可选值 `system`, `user`, `assistant` */
        private String role;

        /** 文本内容 */
        private String content;
    }

    @Data
    public static class Choice {
        /** 索引，从 0 开始 */
        private Integer index;

        /** 会话补全的回复消息 */
        private ChatCompletionMessage message;

        /** 结束原因 */
        @JsonProperty("finish_reason")
        private String finishReason;
    }

    /** 接口使用量 */
    @Data
    public static class Usage {
        /** 提示词的 token 数 */
        @JsonProperty("prompt_tokens")
        private Integer promptTokens;

        /** 补全内容的 token 数 */
        @JsonProperty("completion_tokens")
        private Integer completionTokens;

        /** 累计 token 数 */
        @JsonProperty("total_tokens")
        private Integer totalTokens;
    }
}
