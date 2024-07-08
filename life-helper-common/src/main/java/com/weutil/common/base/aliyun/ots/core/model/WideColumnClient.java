package com.weutil.common.base.aliyun.ots.core.model;

import com.alicloud.openservices.tablestore.SyncClient;

/**
 * 宽表模型客户端
 *
 * <h2>说明
 * <p>这个类的主要用途是在使用时给官方 SDK 的客户端 {@link SyncClient} 改个名，原类名看不出和表格存储的关系，在使用时可能引起混淆。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @see <a href="https://help.aliyun.com/document_detail/43009.html">文档</a>
 * @since 1.3.0
 **/
public class WideColumnClient extends SyncClient {
    public WideColumnClient(String endpoint, String accessKeyId, String accessKeySecret, String instanceName) {
        super(endpoint, accessKeyId, accessKeySecret, instanceName);
    }
}
