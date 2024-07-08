package com.weutil.ai.chat.model;

import com.weutil.ai.constant.AiRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 智能会话消息展示对象
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
public class AiChatMessageVO {
    /** 消息 ID */
    private Long id;

    /** 文本内容 */
    private String content;

    /** 角色，可选值 `system`, `user`, `assistant` */
    private AiRole role;
}
