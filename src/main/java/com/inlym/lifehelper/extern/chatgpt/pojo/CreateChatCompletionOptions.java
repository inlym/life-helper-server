package com.inlym.lifehelper.extern.chatgpt.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 聊天消息补全请求参数
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
public class CreateChatCompletionOptions {
    /** 使用的模型 */
    private String model;

    /** 消息列表 */
    private List<ChatCompletionMessage> messages;
}
