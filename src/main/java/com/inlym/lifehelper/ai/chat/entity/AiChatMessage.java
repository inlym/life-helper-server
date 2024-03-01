package com.inlym.lifehelper.ai.chat.entity;

import com.inlym.lifehelper.ai.constant.AiRole;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * AI 会话的聊天消息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/25
 * @since 2.2.0
 **/
@Table("ai_chat_message")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatMessage {
    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 使用的模型 */
    private String model;

    /** 所属用户 ID */
    private Long userId;

    /** 会话表主键 ID */
    private Long chatId;

    /** OpenAI 响应数据原生 ID */
    private String nativeId;

    /** 文本内容 */
    private String content;

    /** 角色，可选值 `system`, `user`, `assistant` */
    private AiRole role;

    /** 创建时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime createTime;

    /** 更新时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime updateTime;

    /** 删除时间（逻辑删除标志） */
    @Column(isLogicDelete = true)
    private LocalDateTime deleteTime;
}
