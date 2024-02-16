package com.inlym.lifehelper.common.exception;

/**
 * 未授权资源访问异常
 *
 * <h2>主要用途
 * <p>用户访问了不属于自己的资源，则抛出此异常。
 *
 * <h2>使用说明
 * <p>在用户进行查看、修改、删除操作时，代码层面会先通过主键 ID 获取资源，然后再判断该条记录中的所属用户是否是当前用户，若不是，则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/16
 * @since 2.1.0
 **/
public class UnauthorizedResourceAccessException extends RuntimeException {}
