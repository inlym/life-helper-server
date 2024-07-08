package com.weutil.ai.chat.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 创建会话请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/3/1
 * @since 2.3.0
 **/
@Data
public class CreateChatDTO {
    /** 用户发的提示词 */
    @NotEmpty
    private String prompt;
}
