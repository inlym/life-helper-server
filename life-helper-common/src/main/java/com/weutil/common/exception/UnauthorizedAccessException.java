package com.weutil.common.exception;

/**
 * 未授权访问异常
 *
 * <h2>说明
 * <p>需要提供鉴权信息的 API，未提供或提供了无效的鉴权信息，则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
public class UnauthorizedAccessException extends RuntimeException {}
