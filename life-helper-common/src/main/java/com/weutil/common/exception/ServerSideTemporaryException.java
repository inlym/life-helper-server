package com.weutil.common.exception;

/**
 * 服务器端临时性的异常
 *
 * <h3>异常类说明
 * <p>在一些“偏底层”处理（比如建立连接、使用SDK创建客户端等）时，一般很少出现异常，出现时重试几次往往也能成功，无需客户端做出额外处理。出现此类异常时，则抛出当前类。
 *
 * <h3>客户端处理策略
 * <p>展示服务器给出的“笼统”的提示文案，无需告知真实的错误原因。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/15
 * @since 3.0.0
 **/
public class ServerSideTemporaryException extends RuntimeException {}
