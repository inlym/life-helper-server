package com.inlym.lifehelper.ai.chat.entity;

import com.mybatisflex.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI 会话实体
 *
 * <h2>说明
 * <p>一条会话下包含多条消息。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/25
 * @since 2.2.0
 **/
@Table("ai_chat")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChat {
    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 使用的模型 */
    private String model;

    /** 所属用户 ID */
    private Long userId;

    /** 描述，目前直接使用第一句用户消息 */
    private String description;

    /** 最后一条消息时间 */
    private LocalDateTime lastMessageTime;

    @RelationOneToMany(selfField = "id", targetField = "chatId")
    private List<AiChatMessage> messages;

    /** 创建时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime createTime;

    /** 更新时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime updateTime;

    /** 删除时间（逻辑删除标志） */
    @Column(isLogicDelete = true)
    private LocalDateTime deleteTime;
}
