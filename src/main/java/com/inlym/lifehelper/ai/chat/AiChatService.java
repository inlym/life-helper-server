package com.inlym.lifehelper.ai.chat;

import cn.hutool.core.util.IdUtil;
import com.inlym.lifehelper.ai.chat.constant.AiChatRole;
import com.inlym.lifehelper.ai.chat.entity.AiChat;
import com.inlym.lifehelper.ai.chat.entity.AiChatMessage;
import com.inlym.lifehelper.ai.chat.repository.AiChatRepository;
import com.inlym.lifehelper.ai.chat.service.AiChatMessageService;
import com.inlym.lifehelper.extern.chatgpt.ChatGPTService;
import com.inlym.lifehelper.extern.chatgpt.pojo.ChatCompletionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * AI 聊天服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/27
 * @since 1.9.6
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AiChatService {
    private final ChatGPTService chatGPTService;

    private final AiChatMessageService aiChatMessageService;

    private final AiChatRepository repository;

    /**
     * 创建一个新的智能会话对象
     *
     * @since 1.9.6
     */
    public AiChat create() {
        LocalDateTime now = LocalDateTime.now();

        AiChatMessage reservedMessage = AiChatMessage
            .builder()
            .id(IdUtil.simpleUUID())
            .role(AiChatRole.SYSTEM)
            .content("请你记住你叫“小鸣助手”，我们现在开始！")
            .createTime(now)
            .clientVisible(false)
            .apiValid(true)
            .build();

        AiChat chat = AiChat
            .builder()
            .id(IdUtil.simpleUUID())
            .createTime(now)
            .updateTime(now)
            .messages(List.of(reservedMessage))
            .build();

        log.trace("创建智能会话对象 -> {}", chat);
        repository.save(chat);

        return chat;
    }

    /**
     * 增加会话语句
     *
     * @param chatId     会话 ID
     * @param userPrompt 用户输入的话
     *
     * @since 1.9.6
     */
    public AiChat append(String chatId, String userPrompt) {
        Optional<AiChat> result = repository.findById(chatId);
        // 如果不存在则直接创建一个新的，不报错
        AiChat chat = result.orElseGet(this::create);

        LocalDateTime now = LocalDateTime.now();

        AiChatMessage message = AiChatMessage
            .builder()
            .id(IdUtil.simpleUUID())
            .role(AiChatRole.USER)
            .content(userPrompt)
            .createTime(now)
            .clientVisible(true)
            .apiValid(true)
            .build();

        chat.setUpdateTime(now);
        chat
            .getMessages()
            .add(message);

        List<ChatCompletionMessage> completionMessages = aiChatMessageService.convertToApiAccessibleFormat(chat.getMessages());

        AiChatMessage replyMessage;

        try {
            ChatCompletionMessage reply = chatGPTService.createChatCompletion(completionMessages);
            replyMessage = AiChatMessage
                .builder()
                .id(IdUtil.simpleUUID())
                .role(AiChatRole.ASSISTANT)
                .content(reply.getContent())
                .createTime(LocalDateTime.now())
                .clientVisible(true)
                .apiValid(true)
                .build();
        } catch (Exception e) {
            replyMessage = AiChatMessage
                .builder()
                .id(IdUtil.simpleUUID())
                .role(AiChatRole.ASSISTANT)
                .content("我不知道你在说什么！")
                .createTime(LocalDateTime.now())
                .clientVisible(true)
                .apiValid(false)
                .build();
        }

        chat
            .getMessages()
            .add(replyMessage);

        repository.save(chat);
        return chat;
    }
}
