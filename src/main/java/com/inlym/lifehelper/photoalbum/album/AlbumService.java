package com.inlym.lifehelper.photoalbum.album;

import com.inlym.lifehelper.photoalbum.album.entity.Album;
import com.inlym.lifehelper.photoalbum.album.entity.AlbumRepository;
import com.inlym.lifehelper.photoalbum.album.pojo.AlbumVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 相册服务
 *
 * <h2>主要用途
 * <p>用于对接相册模块控制器 {@link AlbumController}
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/12
 * @since 1.4.0
 **/
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;

    /**
     * 将相册实体转化为可用于客户端展示使用的对象结构
     *
     * @param album 相册实体
     *
     * @since 1.4.0
     */
    private AlbumVO convert(Album album) {
        return AlbumVO
            .builder()
            .id(album.getAlbumId())
            .name(album.getName())
            .description(album.getDescription())
            .createTime(album.getCreateTime())
            .updateTime(album.getUpdateTime())
            .build();
    }
}
