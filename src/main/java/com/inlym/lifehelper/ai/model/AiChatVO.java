package com.inlym.lifehelper.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 智能会话展示对象
 *
 * <h2>主要用途
 * <p>用于客户端展示专用的数据模型。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/3/1
 * @since 2.3.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatVO {
    /** 会话 ID */
    private Long id;

    /** 使用的模型 */
    private String model;

    /** 最后一条消息时间 */
    private LocalDateTime lastMessageTime;

    /** 消息列表 */
    private List<AiChatMessageVO> messages;
}
