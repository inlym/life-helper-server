package com.inlym.lifehelper.ai.chat;

import com.inlym.lifehelper.ai.chat.entity.AiChat;
import com.inlym.lifehelper.ai.chat.pojo.AiChatVO;
import com.inlym.lifehelper.ai.chat.pojo.AppendAiChatMessageDTO;
import com.inlym.lifehelper.ai.chat.service.AiChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 智能会话控制器
 *
 * <h2>主要用途
 * <p>管理智能会话相关服务。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/27
 * @since 1.9.6
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
public class AiChatController {
    private final AiChatService aiChatService;

    private final AiChatMessageService aiChatMessageService;

    /**
     * 创建一个新的智能会话对象
     *
     * @since 1.9.6
     */
    @PostMapping("/ai/chat")
    public AiChatVO create() {
        AiChat chat = aiChatService.create();
        return AiChatVO
            .builder()
            .id(chat.getId())
            .messages(aiChatMessageService.convertToClientAccessibleFormat(chat.getMessages()))
            .build();
    }

    /**
     * 添加会话消息
     *
     * @param id  会话 ID
     * @param dto 请求数据
     *
     * @since 1.9.6
     */
    @PutMapping("/ai/chat/{id}")
    public AiChatVO appendMessage(@PathVariable("id") String id, @RequestBody AppendAiChatMessageDTO dto) {
        AiChat chat = aiChatService.append(id, dto.getPrompt());
        return AiChatVO
            .builder()
            .id(chat.getId())
            .messages(aiChatMessageService.convertToClientAccessibleFormat(chat.getMessages()))
            .build();
    }
}
