package com.inlym.lifehelper.ai.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 智能会话的聊天消息
 *
 * <h2>主要用途
 * <p>封装内部使用的聊天消息数据结构
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/27
 * @since 1.9.6
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatMessage {
    /** 随机生成的消息 ID */
    private String id;

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

    /** 创建时间 */
    private LocalDateTime createTime;

    /**
     * 客户端可见
     *
     * <h2>说明
     * <p>该值为 `true` 时，才输出到客户端展示。
     */
    private Boolean clientVisible;

    /**
     * API 调用时可用的
     *
     * <h2>说明
     * <p>该值为 `true` 时，才放到 API 中的会话列表中。
     */
    private Boolean apiValid;
}
