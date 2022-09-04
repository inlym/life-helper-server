package com.inlym.lifehelper.photoalbum.media;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.photoalbum.media.pojo.AddImageDTO;
import com.inlym.lifehelper.photoalbum.media.pojo.MediaVO;
import org.springframework.web.bind.annotation.*;

/**
 * 媒体文件操作控制器
 *
 * <h2>主要用途
 * <p>管理“相册”模块的媒体文件（照片和视频）的增删改查等操作。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/30
 * @since 1.4.0
 **/
@RestController
public class MediaController {
    /**
     * 向相册新增图片
     *
     * @param albumId 相册 ID
     *
     * @since 1.4.0
     */
    @PostMapping(path = "/album/{album_id}/media", params = "type=image")
    @UserPermission
    public MediaVO addMedia(@PathVariable("album_id") String albumId, @UserId int userId, @RequestBody AddImageDTO dto) {
        return null;
    }

    /**
     * 获取指定相册的所有媒体文件
     *
     * @param albumId 相册 ID
     *
     * @since 1.4.0
     */
    @GetMapping("/albums/{album_id}/medias")
    public Object getMediaList(@PathVariable("album_id") String albumId) {
        return null;
    }

    /**
     * 获取指定媒体文件信息
     *
     * @param albumId 相册 ID
     * @param mediaId 媒体文件 ID
     *
     * @since 1.4.0
     */
    @GetMapping("/albums/{album_id}/medias/{media_id}")
    public Object getMediaDetails(@PathVariable("album_id") String albumId, @PathVariable("media_id") String mediaId) {
        return null;
    }

    /**
     * 删除指定媒体文件
     *
     * @param albumId 相册 ID
     * @param mediaId 媒体文件 ID
     *
     * @since 1.4.0
     */
    @DeleteMapping("/albums/{album_id}/medias/{media_id}")
    public Object deleteMedia(@PathVariable("album_id") String albumId, @PathVariable("media_id") String mediaId) {
        return null;
    }
}
