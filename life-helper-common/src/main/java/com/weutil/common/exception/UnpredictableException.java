package com.weutil.common.exception;

/**
 * 意料之外的异常
 *
 * <h2>使用说明
 * <p>用于分支判断中，在认为已包含所有情况处理后，若不匹配已有情况则抛出此异常。
 *
 * <h2>主要用途
 * <p>出现了这个异常表示出现了之前未考虑到的情况，利于在开发阶段提早发现错误。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/28
 * @since 1.7.0
 **/
public class UnpredictableException extends RuntimeException {
    public UnpredictableException(String message) {
        super(message);
    }
}
