package com.inlym.lifehelper.extern.chatgpt;

import com.inlym.lifehelper.extern.chatgpt.pojo.CreateCompletionResponse;
import com.inlym.lifehelper.extern.chatgpt.service.ChatGPTHttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
