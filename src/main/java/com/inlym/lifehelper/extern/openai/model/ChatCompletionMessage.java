package com.inlym.lifehelper.extern.openai.model;

import lombok.Data;

/**
 * 会话补全接口请求和响应数据共同用到的数据模型
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/10
 * @see <a href="https://platform.openai.com/docs/api-reference/chat/create">会话补全</a>
 * @since 2.0.3
 **/
@Data
public class ChatCompletionMessage {
    /**
     * 角色
     * <p>
     * <h2>字段说明
     * <p>可选值：`system`, `user`, `assistant`
     */
    private String role;

    /** 消息内容 */
    private String content;
}
