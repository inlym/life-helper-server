package com.inlym.lifehelper.extern.chatgptold;

import com.inlym.lifehelper.extern.chatgptold.pojo.ChatCompletionMessage;
import com.inlym.lifehelper.extern.chatgptold.pojo.CreateChatCompletionOptions;
import com.inlym.lifehelper.extern.chatgptold.pojo.CreateChatCompletionResponse;
import com.inlym.lifehelper.extern.chatgptold.pojo.CreateCompletionResponse;
import com.inlym.lifehelper.extern.chatgptold.service.ChatGPTHttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ChatGPT 服务
 *
 * <h2>主要用途
 * <p>封装用于模块外部调用的服务。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/25
 * @since 1.9.5
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ChatGPTService {
    private final ChatGPTHttpService chatGPTHttpService;

    /**
     * 会话补全
     *
     * @param prompt 提示语
     *
     * @since 1.9.5
     */
    public String createCompletion(String prompt) {
        CreateCompletionResponse response = chatGPTHttpService.createCompletion(prompt);
        return response.getChoices()[0].getText();
    }

    /**
     * 创建会话消息补全
     *
     * @param messages 已发生的消息列表
     *
     * @see <a href="https://platform.openai.com/docs/api-reference/chat/create">Create chat completion</a>
     * @since 1.9.6
     */
    public ChatCompletionMessage createChatCompletion(List<ChatCompletionMessage> messages) {
        CreateChatCompletionOptions options = CreateChatCompletionOptions
            .builder()
            .model("gpt-3.5-turbo")
            .messages(messages)
            .build();

        CreateChatCompletionResponse response = chatGPTHttpService.createChatCompletion(options);
        return response
            .getChoices()
            .get(0)
            .getMessage();
    }
}
