package com.inlym.lifehelper.ai.chat.service;

import com.inlym.lifehelper.ai.chat.entity.AiChatMessage;
import com.inlym.lifehelper.ai.constant.AiRole;
import com.inlym.lifehelper.extern.openai.model.ChatCompletion;
import com.inlym.lifehelper.extern.openai.model.ChatCompletionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * AI 聊天会话消息服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/3/1
 * @since 2.3.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AiChatMessageService {
    private final AiChatMessageMapper aiChatMessageMapper;

    /**
     * 创建用户消息
     *
     * @param userId  用户 ID
     * @param chatId  会话 ID
     * @param content 消息文本
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    public AiChatMessage createUserMessage(long userId, long chatId, String content) {
        AiChatMessage message = AiChatMessage
            .builder()
            .userId(userId)
            .chatId(chatId)
            .content(content)
            .role(AiRole.USER)
            .build();

        aiChatMessageMapper.insertSelective(message);
        log.info("创建会话消息（USER） -> {}", message);
        return message;
    }

    /**
     * 从响应数据创建智能消息
     *
     * @param userId         用户 ID
     * @param chatId         会话 ID
     * @param chatCompletion 调用 API 的响应数据
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    public AiChatMessage createAssistantMessage(long userId, long chatId, ChatCompletion chatCompletion) {
        ChatCompletionMessage chatCompletionMessage = chatCompletion
            .getChoices()
            .get(0)
            .getMessage();

        AiChatMessage message = AiChatMessage
            .builder()
            .model(chatCompletion.getModel())
            .userId(userId)
            .chatId(chatId)
            .nativeId(chatCompletion.getId())
            .content(chatCompletionMessage.getContent())
            .role(AiRole.ASSISTANT)
            .build();

        aiChatMessageMapper.insertSelective(message);
        log.info("创建会话消息（ASSISTANT） -> {}", message);
        return message;
    }
}
