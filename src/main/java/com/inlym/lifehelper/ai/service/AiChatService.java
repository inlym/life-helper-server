package com.inlym.lifehelper.ai.service;

import com.inlym.lifehelper.ai.constant.AiRole;
import com.inlym.lifehelper.ai.entity.AiChat;
import com.inlym.lifehelper.ai.entity.AiChatMessage;
import com.inlym.lifehelper.extern.openai.model.ChatCompletion;
import com.inlym.lifehelper.extern.openai.model.ChatCompletionMessage;
import com.inlym.lifehelper.extern.openai.model.ChatCompletionRequest;
import com.inlym.lifehelper.extern.openai.service.OpenAiApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI 聊天服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/25
 * @since 2.2.0
 **/
@Service
@RequiredArgsConstructor
public class AiChatService {
    private final OpenAiApiService openAiApiService;

    private final AiChatMapper aiChatMapper;

    private final AiChatMessageMapper aiChatMessageMapper;

    /**
     * 创建智能会话
     *
     * @param userId 用户 ID
     * @param prompt 用户发的提示词
     *
     * @since 2.2.0
     */
    public AiChat createChat(long userId, String prompt) {
        ChatCompletionMessage chatCompletionMessage1 = ChatCompletionMessage
            .builder()
            .content(prompt)
            .role(AiRole.USER)
            .build();

        ChatCompletionRequest request = ChatCompletionRequest
            .builder()
            .model("gpt-3.5-turbo-0125")
            .messages(List.of(chatCompletionMessage1))
            .build();

        ChatCompletion response = openAiApiService.createChatCompletion(request);
        ChatCompletionMessage chatCompletionMessage2 = response
            .getChoices()
            .get(0)
            .getMessage();

        AiChat chat = AiChat
            .builder()
            .model("gpt-3.5-turbo-0125")
            .userId(userId)
            .lastMessageTime(LocalDateTime.now())
            .build();
        aiChatMapper.insertSelective(chat);

        AiChatMessage message1 = AiChatMessage
            .builder()
            .model("")
            .userId(userId)
            .chatId(chat.getId())
            .nativeId("")
            .content(chatCompletionMessage1.getContent())
            .role(AiRole.USER)
            .build();

        AiChatMessage message2 = AiChatMessage
            .builder()
            .model(response.getModel())
            .userId(userId)
            .chatId(chat.getId())
            .nativeId(response.getId())
            .content(chatCompletionMessage2.getContent())
            .role(chatCompletionMessage2.getRole())
            .build();
        aiChatMessageMapper.insertSelective(message1);
        aiChatMessageMapper.insertSelective(message2);

        return aiChatMapper.selectOneWithRelationsById(chat.getId());
    }
}
