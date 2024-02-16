package com.inlym.lifehelper.common.exception;

/**
 * 未找到资源异常
 *
 * <h2>主要用途
 * <p>用户访问了不存在或者不属于自己的资源，则抛出此异常。
 *
 * <h2>使用说明
 * <p>发生以下情况时，抛出此异常：
 * <li>1. 通过主键 ID 查找资源，未找到资源。
 * <li>2. 通过主键 ID 查找资源，能够找到资源，但是不属于当前用户。
 * <li>3. 同时使用主键 ID 和所属用户 ID 查找资源，未找到资源。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/16
 * @since 2.1.0
 **/
public class ResourceNotFoundException extends RuntimeException {}
