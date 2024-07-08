package com.weutil.common.base.aliyun.oss.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 阿里云 OSS 资源注解
 *
 * <h2>主要用途
 * <p>标记该注解的字段，返回响应时自动将 OSS 中的路径转化为完整可访问的 URL 地址。
 *
 * <h2>使用效果示例
 * <p>原字段值为 {@code temp/4rqE00X6qXg0.jpg}，使用该注解标记后，响应中的值为 {@code https://res.weutil.com/temp/4rqE00X6qXg0
 * .jpg?Expires=1720599451&OSSAccessKeyId=LTAI5tKLP9qGYxi3AUwFNaZV&Signature=G4dgAW6tpUZv%2Bmub%2FRJ%2B3nQ9hSo%3D}。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/10
 * @since 2.3.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = OssResourceJsonSerializer.class)
public @interface OssResource {
    String value() default "";
}
