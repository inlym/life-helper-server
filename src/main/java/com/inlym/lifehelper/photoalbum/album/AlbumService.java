package com.inlym.lifehelper.photoalbum.album;

import cn.hutool.core.util.IdUtil;
import com.inlym.lifehelper.photoalbum.album.entity.Album;
import com.inlym.lifehelper.photoalbum.album.pojo.AlbumVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    /**
     * 将相册实体转化为可用于客户端展示使用的对象结构
     *
     * @param album 相册实体
     *
     * @since 1.4.0
     */
    public AlbumVO convert(Album album) {
        return AlbumVO
            .builder()
            .id(album.getAlbumId())
            .name(album.getName())
            .description(album.getDescription())
            .createTime(album.getCreateTime())
            .updateTime(album.getUpdateTime())
            .build();
    }

    /**
     * 创建新相册
     *
     * @param album 包含部分属性的相册实体
     *
     * @since 1.4.0
     */
    public Album createAlbum(Album album) {
        album.setAlbumId(IdUtil.simpleUUID());
        album.setLastUploadTime(System.currentTimeMillis());

        return null;
    }

    /**
     * 获取用户的所有相册
     *
     * @param userId 用户 ID
     *
     * @since 1.4.0
     */
    public List<Album> listAlbums(int userId) {
        return null;
    }

    /**
     * 更新相册信息
     *
     * @param album 相册实体
     *
     * @since 1.4.0
     */
    public Album updateAlbum(Album album) {
        return null;
    }

    /**
     * 删除相册
     *
     * @param userId  用户 ID
     * @param albumId 相册 ID
     *
     * @since 1.4.0
     */
    public void deleteAlbum(int userId, String albumId) {

    }
}
