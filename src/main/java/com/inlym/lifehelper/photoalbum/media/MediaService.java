package com.inlym.lifehelper.photoalbum.media;

import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.common.base.aliyun.ots.widecolumn.WideColumnExecutor;
import com.inlym.lifehelper.photoalbum.album.AlbumService;
import com.inlym.lifehelper.photoalbum.album.entity.Album;
import com.inlym.lifehelper.photoalbum.album.pojo.AlbumVO;
import com.inlym.lifehelper.photoalbum.media.entity.Media;
import com.inlym.lifehelper.photoalbum.media.pojo.MediaVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
@Slf4j
@RequiredArgsConstructor
public class MediaService {
    private final WideColumnExecutor wideColumnExecutor;

    private final AlbumService albumService;

    private final OssService ossService;

    /**
     * 将实体转化为客户端使用的视图对象
     *
     * @param media 媒体文件实体
     *
     * @since 1.4.0
     */
    public MediaVO convert(Media media) {
        MediaVO vo = new MediaVO();
        vo.setId(media.getMediaId());
        vo.setType(media.getType());
        vo.setUploadTime(media.getUploadTime());
        vo.setDuration(media.getDuration());

        vo.setUrl(ossService.concatUrl(media.getPath()));
        if (StringUtils.hasText(media.getThumbPath())) {
            vo.setThumbUrl(ossService.concatUrl(media.getThumbPath()));
        }

        return vo;
    }

    /**
     * 向相册中添加媒体文件
     *
     * @param userId 用户 ID
     * @param media  媒体文件实体
     *
     * @since 1.4.0
     */
    public Media add(int userId, Media media) {
        // 首先判断要操作的相册是否存在，如果不存在就不需要继续了
        albumService.findOneOrElseThrow(userId, media.getAlbumId());

        // 给一些字段赋初始值
        media.setCreateTime(System.currentTimeMillis());

        Media media2 = wideColumnExecutor.create(media);

        albumService.refresh(userId, media.getAlbumId());

        return media2;
    }

    /**
     * 删除媒体文件
     *
     * @param userId 用户 ID
     * @param media  媒体文件实体
     *
     * @since 1.4.0
     */
    public void delete(int userId, Media media) {
        // 首先判断要操作的相册是否存在，如果不存在就不需要继续了
        albumService.findOneOrElseThrow(userId, media.getAlbumId());

        wideColumnExecutor.delete(media);
        albumService.refresh(userId, media.getAlbumId());
    }

    /**
     * 获取带媒体文件列表的相册详情
     *
     * @param userId  用户 ID
     * @param albumId 相册 ID
     *
     * @since 1.4.0
     */
    public AlbumVO getAlbumWithMedias(int userId, String albumId) {
        Album album = albumService.findOneOrElseThrow(userId, albumId);
        Media mediaSearch = Media
            .builder()
            .albumId(albumId)
            .build();

        List<Media> mediaList = wideColumnExecutor.findAll(mediaSearch, Media.class);
        mediaList.sort(Comparator.comparing(Media::getUploadTime));

        List<MediaVO> list = new ArrayList<>();

        for (Media media : mediaList) {
            list.add(convert(media));
        }

        AlbumVO vo = albumService.convert(album);
        vo.setMedias(list);

        return vo;
    }
}
