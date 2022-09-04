package com.inlym.lifehelper.photoalbum.media;

import com.inlym.lifehelper.common.base.aliyun.ots.widecolumn.WideColumnExecutor;
import com.inlym.lifehelper.photoalbum.media.entity.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 媒体文件服务
 *
 * <h2>主要用途
 * <p>管理媒体文件的增删改查操作
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/28
 * @since 1.4.0
 **/
@Service
@RequiredArgsConstructor
public class MediaService {
    private final WideColumnExecutor wideColumnExecutor;

    public Media add(Media media){
        return null;
    }
}
