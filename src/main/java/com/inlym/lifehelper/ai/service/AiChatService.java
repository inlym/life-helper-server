package com.inlym.lifehelper.ai.service;

import com.inlym.lifehelper.ai.constant.AiRole;
import com.inlym.lifehelper.ai.entity.AiChat;
import com.inlym.lifehelper.common.exception.ResourceNotFoundException;
import com.inlym.lifehelper.extern.openai.model.ChatCompletion;
import com.inlym.lifehelper.extern.openai.model.ChatCompletionMessage;
import com.inlym.lifehelper.extern.openai.model.ChatCompletionRequest;
import com.inlym.lifehelper.extern.openai.service.OpenAiApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.inlym.lifehelper.ai.entity.table.AiChatTableDef.AI_CHAT;

/**
 * AI 聊天服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/25
 * @since 2.2.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AiChatService {
    private final OpenAiApiService openAiApiService;

    private final AiChatMapper aiChatMapper;

    private final AiChatMessageService aiChatMessageService;

    /**
     * 创建智能会话
     *
     * @param userId 用户 ID
     * @param prompt 用户发的提示词
     *
     * @since 2.2.0
     */
    public AiChat createChat(long userId, String prompt) {
        String model = "gpt-3.5-turbo-0125";
        return createChat(userId, prompt, model);
    }

    /**
     * 创建智能会话
     *
     * @param userId 用户 ID
     * @param prompt 用户发的提示词
     * @param model  模型
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    public AiChat createChat(long userId, String prompt, String model) {
        ChatCompletion response = createChatCompletion(prompt, model);

        AiChat chat = AiChat
            .builder()
            .model(model)
            .userId(userId)
            .lastMessageTime(LocalDateTime.now())
            .build();
        aiChatMapper.insertSelective(chat);
        log.info("创建智能会话 -> {}", chat);

        aiChatMessageService.createUserMessage(userId, chat.getId(), prompt);
        aiChatMessageService.createAssistantMessage(userId, chat.getId(), response);

        return aiChatMapper.selectOneWithRelationsById(chat.getId());
    }

    /**
     * 补充会话
     *
     * @param userId 用户 ID
     * @param chatId 会话 ID
     * @param prompt 用户发的提示词
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    public AiChat appendChat(long userId, long chatId, String prompt) {
        AiChat chat = aiChatMapper.selectOneById(chatId);
        if (chat.getUserId() != userId) {
            throw new ResourceNotFoundException();
        }

        ChatCompletion response = createChatCompletion(prompt, chat.getModel());
        aiChatMessageService.createUserMessage(userId, chatId, prompt);
        aiChatMessageService.createAssistantMessage(userId, chatId, response);

        AiChat updated = AiChat
            .builder()
            .id(chatId)
            .lastMessageTime(LocalDateTime.now())
            .build();
        aiChatMapper.update(updated);

        return aiChatMapper.selectOneWithRelationsById(chat.getId());
    }

    /**
     * 删除会话
     *
     * @param userId 用户 ID
     * @param chatId 会话 ID
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    public void deleteChat(long userId, long chatId) {
        aiChatMapper.deleteByCondition(AI_CHAT.USER_ID
                                           .eq(userId)
                                           .and(AI_CHAT.ID.eq(chatId)));
    }

    /**
     * 创建会话补全请求
     *
     * @param prompt 用户发的提示词
     * @param model  模型
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    private ChatCompletion createChatCompletion(String prompt, String model) {
        ChatCompletionRequest request = ChatCompletionRequest
            .builder()
            .model(model)
            .messages(List.of(ChatCompletionMessage
                                  .builder()
                                  .content(prompt)
                                  .role(AiRole.USER)
                                  .build()))
            .build();

        return openAiApiService.createChatCompletion(request);
    }
}
