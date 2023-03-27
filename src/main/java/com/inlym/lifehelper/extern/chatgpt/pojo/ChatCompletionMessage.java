package com.inlym.lifehelper.extern.chatgpt.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天消息中的单条消息
 *
 * <h2>说明
 * <p>发起 HTTP 请求时，请求数据和响应数据都用到这个数据结构。
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
public class ChatCompletionMessage {
    /**
     * 角色
     *
     * <h2>可选值
     * <p>1. `system`
     * <p>2. `user`
     * <p>3. `assistant`
     */
    private String role;

    /** 文本内容 */
    private String content;
}
