package com.inlym.lifehelper.ai.chat.service;

import com.inlym.lifehelper.ai.chat.entity.AiChatMessage;
import com.inlym.lifehelper.ai.chat.pojo.AiChatMessageVO;
import com.inlym.lifehelper.extern.chatgpt.pojo.ChatCompletionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 智能会话消息处理服务
 *
 * <h2>主要用途
 * <p>专门处理智能会话对象中的消息，做数据格式转换。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/27
 * @since 1.9.6
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AiChatMessageService {
    /**
     * 转换成可被 API 调用时使用的格式
     *
     * @param messages 智能会话消息对象列表
     *
     * @since 1.9.6
     */
    public List<ChatCompletionMessage> convertToApiAccessibleFormat(List<AiChatMessage> messages) {
        return messages
            .stream()
            .filter(AiChatMessage::getApiValid)
            .map(p -> {
                return ChatCompletionMessage
                    .builder()
                    .role(p.getRole())
                    .content(p.getContent())
                    .build();
            })
            .toList();
    }

    /**
     * 转换成客户端可用的数据格式
     *
     * @param messages 智能会话消息对象列表
     *
     * @since 1.9.6
     */
    public List<AiChatMessageVO> convertToClientAccessibleFormat(List<AiChatMessage> messages) {
        return messages
            .stream()
            .filter(AiChatMessage::getClientVisible)
            .map(p -> {
                return AiChatMessageVO
                    .builder()
                    .id(p.getId())
                    .role(p.getRole())
                    .content(p.getContent())
                    .build();
            })
            .toList();
    }
}
