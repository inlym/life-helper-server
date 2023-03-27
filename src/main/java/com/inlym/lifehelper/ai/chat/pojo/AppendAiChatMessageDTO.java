package com.inlym.lifehelper.ai.chat.pojo;

import lombok.Data;

/**
 * 添加新的智能会话消息的请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/27
 * @since 1.9.6
 **/
@Data
public class AppendAiChatMessageDTO {
    /** 用户输入的消息内容 */
    private String prompt;
}
