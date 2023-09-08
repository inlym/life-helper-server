package com.inlym.lifehelper.extern.chatgptold.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 聊天消息补全请求的响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/27
 * @see <a href="https://platform.openai.com/docs/api-reference/chat/create">Create chat completion</a>
 * @since 1.9.6
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatCompletionResponse {
    private String id;

    private String object;

    private String created;

    private List<Choice> choices;

    private ApiError error;

    @Data
    public static class Choice {
        /** 索引 */
        private Integer index;

        private ChatCompletionMessage message;

        /** 结束原因 */
        @JsonProperty("finish_reason")
        private String finishReason;

        /** 当前 API 调用的令牌使用情况 */
        private ApiUsage usage;
    }
}
