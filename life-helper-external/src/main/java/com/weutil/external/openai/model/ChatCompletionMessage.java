package com.weutil.external.openai.model;

import com.weutil.ai.constant.AiRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会话补全单条消息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/19
 * @since 2.1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionMessage {
    private String name;

    /** 文本内容 */
    private String content;

    /** 角色，可选值 `system`, `user`, `assistant` */
    private AiRole role;
}
