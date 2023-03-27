package com.inlym.lifehelper.ai.chat.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用于客户端使用的智能会话对象
 *
 * <h2>主要用途
 * <p>将 {@link com.inlym.lifehelper.ai.chat.entity.AiChat} 封装为客户端展示使用的数据结构。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/27
 * @since 1.9.6
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatVO {
    /** 会话 ID */
    private String id;

    /** 消息列表 */
    private List<AiChatMessageVO> messages;
}
