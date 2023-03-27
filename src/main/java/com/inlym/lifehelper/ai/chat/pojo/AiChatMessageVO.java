package com.inlym.lifehelper.ai.chat.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于客户端使用的单条智能会话的聊天消息
 *
 * <h2>主要用途
 * <p>将 {@link com.inlym.lifehelper.ai.chat.entity.AiChatMessage} 对象封装为客户端使用的数据结构。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/27
 * @since 1.9.6
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatMessageVO {
    /** 消息 ID */
    private String id;

    /** 角色 */
    private String role;

    /** 文本内容 */
    private String content;
}
