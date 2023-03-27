package com.inlym.lifehelper.ai.chat;

import cn.hutool.core.util.IdUtil;
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
import java.util.ArrayList;
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
        AiChatMessage message1 = aiChatMessageService.createForSystem("请你记住你叫“小鸣助手”，我们现在开始！");
        message1.setApiValid(true);

        AiChatMessage message2 = aiChatMessageService.createForSystem("你可以问我问题，我会尽量回答你！");
        message2.setClientVisible(true);

        List<AiChatMessage> messages = new ArrayList<>(16);
        messages.add(message1);
        messages.add(message2);

        LocalDateTime now = LocalDateTime.now();
        AiChat chat = AiChat
            .builder()
            .id(IdUtil.simpleUUID())
            .createTime(now)
            .updateTime(now)
            .messages(messages)
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

        AiChatMessage message = aiChatMessageService.createForUser(userPrompt);

        chat.setUpdateTime(LocalDateTime.now());
        chat
            .getMessages()
            .add(message);

        List<ChatCompletionMessage> completionMessages = aiChatMessageService.convertToApiAccessibleFormat(chat.getMessages());

        AiChatMessage replyMessage;

        try {
            ChatCompletionMessage reply = chatGPTService.createChatCompletion(completionMessages);
            replyMessage = aiChatMessageService.createForAssistant(reply.getContent());
        } catch (Exception e) {
            replyMessage = aiChatMessageService.createForAssistant("我不知道你在说什么！");
            replyMessage.setApiValid(false);
        }

        chat
            .getMessages()
            .add(replyMessage);

        repository.save(chat);
        return chat;
    }
}
