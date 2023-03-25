package com.inlym.lifehelper.ai.text;

import com.inlym.lifehelper.ai.text.pojo.TextCompletionDTO;
import com.inlym.lifehelper.ai.text.pojo.TextCompletionVO;
import com.inlym.lifehelper.extern.chatgpt.ChatGPTService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文本补全类功能控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/25
 * @since 1.9.5
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
public class TextCompletionController {
    private final ChatGPTService chatGPTService;

    /**
     * 文本会话补全
     *
     * @param dto 请求数据
     *
     * @since 1.9.5
     */
    @PostMapping("/ai/text")
    public TextCompletionVO createTextCompletion(@NotEmpty @RequestBody TextCompletionDTO dto) {
        String prompt = dto.getPrompt();
        String reply = chatGPTService.createCompletion(prompt);

        return TextCompletionVO
            .builder()
            .text(reply)
            .build();
    }
}
