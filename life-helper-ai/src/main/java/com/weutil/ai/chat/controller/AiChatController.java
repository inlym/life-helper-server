package com.weutil.ai.chat.controller;

import com.weutil.ai.chat.entity.AiChat;
import com.weutil.ai.chat.model.AiChatMessageVO;
import com.weutil.ai.chat.model.AiChatVO;
import com.weutil.ai.chat.model.CreateChatDTO;
import com.weutil.ai.chat.service.AiChatService;
import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.CommonListResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 智能会话模块控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/3/1
 * @since 2.3.0
 **/
@RestController
@RequiredArgsConstructor
@Validated
public class AiChatController {
    private final AiChatService aiChatService;

    /**
     * 创建会话
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    @PostMapping("/ai/chat")
    @UserPermission
    public AiChatVO createChat(@UserId long userId, @Valid @RequestBody CreateChatDTO dto) {
        String prompt = dto.getPrompt();
        return convertEntity(aiChatService.createChat(userId, prompt));
    }

    /**
     * 将实体对象转化为展示对象
     *
     * @param chat 智能会话实体对象
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    private AiChatVO convertEntity(AiChat chat) {
        AiChatVO vo = AiChatVO
                .builder()
                .id(chat.getId())
                .model(chat.getModel())
                .description(chat.getDescription())
                .lastMessageTime(chat.getLastMessageTime())
                .build();

        if (chat.getMessages() != null && !chat
                .getMessages()
                .isEmpty()) {
            List<AiChatMessageVO> messages = chat
                    .getMessages()
                    .stream()
                    .map(msg -> AiChatMessageVO
                            .builder()
                            .id(msg.getId())
                            .content(msg.getContent())
                            .role(msg.getRole())
                            .build())
                    .toList();

            vo.setMessages(messages);
        }

        return vo;
    }

    /**
     * 获取单个会话
     *
     * @param userId 用户 ID
     * @param chatId 会话 ID
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    @GetMapping("/ai/chat/{id}")
    @UserPermission
    public AiChatVO getChat(@UserId long userId, @NotNull @PathVariable("id") long chatId) {
        return convertEntity(aiChatService.getChat(userId, chatId));
    }

    /**
     * 获取会话列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    @GetMapping("/ai/chats")
    @UserPermission
    public CommonListResponse<AiChatVO> getSimpleList(@UserId long userId) {
        List<AiChatVO> list = aiChatService
                .getChatList(userId)
                .stream()
                .map(this::convertEntity)
                .toList();

        return new CommonListResponse<>(list);
    }

    /**
     * 补充会话
     *
     * @param userId 用户 ID
     * @param chatId 会话 ID
     * @param dto    请求数据
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    @PutMapping("/ai/chat/{id}")
    @UserPermission
    public AiChatVO appendChat(@UserId long userId, @NotNull @PathVariable("id") long chatId, @Valid @RequestBody CreateChatDTO dto) {
        String prompt = dto.getPrompt();
        return convertEntity(aiChatService.appendChat(userId, chatId, prompt));
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
    @DeleteMapping("/ai/chat/{id}")
    @UserPermission
    public AiChatVO deleteChat(@UserId long userId, @NotNull @PathVariable("id") long chatId) {
        aiChatService.deleteChat(userId, chatId);
        return AiChatVO
                .builder()
                .id(chatId)
                .build();
    }
}
