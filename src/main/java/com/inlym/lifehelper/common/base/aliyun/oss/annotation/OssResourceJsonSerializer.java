package com.inlym.lifehelper.common.base.aliyun.oss.annotation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.inlym.lifehelper.common.base.aliyun.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 阿里云 OSS 资源注解序列化器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/10
 * @since 2.3.0
 **/
// 备注（2024.06.12）
// 临时移除以下注解，让其不要注入，因为目前一旦加上，所有的字符串都会被处理，目前还不知道如何只处理被注解的字符串，这一部分还需要去研究一下。
//@JsonComponent
public class OssResourceJsonSerializer extends JsonSerializer<String> {
    private OssService ossService;

    // 备注（2024.06.10）
    // [当前类不使用构造器注入的原因]
    // 使用构造器注入，则无默认构造器，会有警告提示。
    @Autowired
    public void setOssService(OssService ossService) {
        this.ossService = ossService;
    }

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(ossService.getPresignedUrl(s));
    }
}
