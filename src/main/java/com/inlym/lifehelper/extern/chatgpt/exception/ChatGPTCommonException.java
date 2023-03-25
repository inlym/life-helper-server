package com.inlym.lifehelper.extern.chatgpt.exception;

/**
 * ChatGPT 通用异常
 *
 * <h2>主要用途
 * <p>在 ChatGPT 模块发生异常时，若无特殊指向性，则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/25
 * @since 1.9.5
 **/
public class ChatGPTCommonException extends RuntimeException {
    public ChatGPTCommonException(String message) {
        super(message);
    }
}
