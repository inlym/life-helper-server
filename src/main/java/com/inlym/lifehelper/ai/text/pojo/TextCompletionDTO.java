package com.inlym.lifehelper.ai.text.pojo;

import lombok.Data;

/**
 * 发起会话补全接口的请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/25
 * @since 1.9.5
 **/
@Data
public class TextCompletionDTO {
    /** 提示 */
    private String prompt;
}
