package com.inlym.lifehelper.photoalbum.media;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.photoalbum.album.pojo.AlbumVO;
import com.inlym.lifehelper.photoalbum.media.constant.MediaType;
import com.inlym.lifehelper.photoalbum.media.entity.Media;
import com.inlym.lifehelper.photoalbum.media.pojo.AddImageDTO;
import com.inlym.lifehelper.photoalbum.media.pojo.AddVideoDTO;
import com.inlym.lifehelper.photoalbum.media.pojo.MediaVO;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    /**
     * 向相册新增图片
     *
     * @param albumId 相册 ID
     * @param userId  用户 ID
     * @param dto     请求数据
     *
     * @since 1.4.0
     */
    @PostMapping(path = "/album/{album_id}/media", params = "type=image")
    @UserPermission
    public MediaVO addImage(@PathVariable("album_id") String albumId, @UserId int userId, @RequestBody AddImageDTO dto) {
        Media media = Media
            .builder()
            .albumId(albumId)
            .type(MediaType.IMAGE)
            .path(dto.getPath())
            .size(dto.getSize())
            .uploadTime(dto.getUploadTime())
            .build();

        return mediaService.convert(mediaService.add(userId, media));
    }

    /**
     * 向相册新增视频
     *
     * @param albumId 相册 ID
     * @param userId  用户 ID
     * @param dto     请求数据
     *
     * @since 1.4.0
     */
    @PostMapping(path = "/album/{album_id}/media", params = "type=video")
    @UserPermission
    public MediaVO addVideo(@PathVariable("album_id") String albumId, @UserId int userId, @RequestBody AddVideoDTO dto) {
        Media media = Media
            .builder()
            .albumId(albumId)
            .type(MediaType.VIDEO)
            .path(dto.getPath())
            .width(dto.getWidth())
            .height(dto.getHeight())
            .size(dto.getSize())
            .uploadTime(dto.getUploadTime())
            .thumbPath(dto.getThumbPath())
            .duration(dto.getDuration())
            .build();

        return mediaService.convert(mediaService.add(userId, media));
    }

    /**
     * 删除媒体文件
     *
     * @param userId  用户 ID
     * @param albumId 相册 ID
     * @param mediaId 媒体文件 ID
     *
     * @since 1.4.0
     */
    @DeleteMapping("/album/{album_id}/media/{media_id}")
    @UserPermission
    public MediaVO delete(@UserId int userId, @PathVariable("album_id") String albumId, @PathVariable("media_id") String mediaId) {
        Media media = Media
            .builder()
            .albumId(albumId)
            .mediaId(mediaId)
            .build();

        mediaService.delete(userId, media);

        return mediaService.convert(media);
    }

    /**
     * 获取带媒体文件列表的相册详情
     *
     * @param userId  用户 ID
     * @param albumId 相册 ID
     *
     * @since 1.4.0
     */
    @GetMapping("/album/{album_id}")
    @UserPermission
    public AlbumVO getAlbumDetail(@UserId int userId, @PathVariable("album_id") String albumId) {
        return mediaService.getAlbumWithMedias(userId, albumId);
    }
}
