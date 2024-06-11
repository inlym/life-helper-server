package com.inlym.lifehelper.checklist.constant;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 内容类型
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/11
 * @since 2.3.0
 **/
@Getter
public enum ContentType {
    /** 纯文本（默认） */
    TEXT(0),

    /** markdown 格式的文本 */
    MARKDOWN(1);

    @EnumValue
    private final Integer code;

    ContentType(int code) {
        this.code = code;
    }
}
