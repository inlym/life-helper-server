package com.inlym.lifehelper.photoalbum.album;

import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.common.base.aliyun.ots.core.WideColumnExecutor;
import com.inlym.lifehelper.photoalbum.album.entity.Album;
import com.inlym.lifehelper.photoalbum.album.exception.AlbumNotExistException;
import com.inlym.lifehelper.photoalbum.album.pojo.AlbumVO;
import com.inlym.lifehelper.photoalbum.media.constant.MediaType;
import com.inlym.lifehelper.photoalbum.media.entity.Media;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;

/**
 * 相册服务
 *
 * <h2>主要用途
 * <p>管理相册数据的增删改查操作。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/12
 * @since 1.4.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumService {
    private final WideColumnExecutor wideColumnExecutor;

    private final OssService ossService;

    /**
     * 将相册实体转化为可用于客户端展示使用的对象结构
     *
     * @param album 相册实体
     * @since 1.4.0
     */
    public AlbumVO convert(Album album) {
        AlbumVO vo = AlbumVO
                .builder()
                .id(album.getAlbumId())
                .name(album.getName())
                .createTime(album.getCreateTime())
                .updateTime(album.getUpdateTime())
                .size(album.getSize())
                .imageCount(album.getImageCount())
                .videoCount(album.getVideoCount())
                .build();

        if (StringUtils.hasText(album.getCoverImagePath())) {
            vo.setCoverImageUrl(ossService.concatUrl(album.getCoverImagePath()));
        }

        if (album.getImageCount() != null && album.getVideoCount() != null) {
            vo.setCount(album.getImageCount() + album.getVideoCount());
        }

        return vo;
    }

    /**
     * 创建新相册
     *
     * @param album 包含部分属性的相册实体，该实体对象应包含以下属性：`userId`, `name`, `description`
     * @since 1.4.0
     */
    public Album create(Album album) {
        long now = System.currentTimeMillis();

        // 给一些字段赋默认初始值
        album.setCreateTime(now);
        album.setUpdateTime(now);
        album.setImageCount(0);
        album.setVideoCount(0);
        album.setSize(0L);

        return wideColumnExecutor.create(album);
    }

    /**
     * 获取用户的所有相册
     *
     * @param userId 用户 ID
     * @since 1.4.0
     */
    public List<Album> list(int userId) {
        Album album = Album
                .builder()
                .userId(userId)
                .build();

        return wideColumnExecutor.findAll(album, Album.class);
    }

    /**
     * 更新相册信息
     *
     * @param album 相册实体
     * @since 1.4.0
     */
    public Album update(Album album) {
        return wideColumnExecutor.update(album);
    }

    /**
     * 删除相册
     *
     * @param userId  用户 ID
     * @param albumId 相册 ID
     * @since 1.4.0
     */
    public void delete(int userId, String albumId) {
        Album album = Album
                .builder()
                .userId(userId)
                .albumId(albumId)
                .build();

        wideColumnExecutor.delete(album);
    }

    /**
     * 查找指定相册，若不存在则返回 null
     *
     * @param userId  用户 ID
     * @param albumId 相册 ID
     * @since 1.4.0
     */
    public Album findOne(int userId, String albumId) {
        Album album = Album
                .builder()
                .userId(userId)
                .albumId(albumId)
                .build();

        return wideColumnExecutor.findOne(album, Album.class);
    }

    /**
     * 查找指定相册，若不存在则直接报错
     *
     * @param userId  用户 ID
     * @param albumId 相册 ID
     * @since 1.4.0
     */
    public Album findOneOrElseThrow(int userId, String albumId) {
        Album album = findOne(userId, albumId);
        if (album == null) {
            throw AlbumNotExistException.create(albumId);
        }

        return album;
    }

    /**
     * 刷新（重新计算）相册的“缓存”字段
     *
     * <h2>主要用途
     * <p>相册实体中的部分字段为“缓存”字段——将相册的某些计算结果直接存储，而不是每次获取时再计算一遍，为方便拓展，将所有“缓存”字段的计算封装到当前方法中。
     *
     * <h2>什么时候调用？
     * <p>对相册媒体资源进行增加、删除操作时，调用此方法。
     *
     * @param userId  用户 ID
     * @param albumId 相册 ID
     * @since 1.4.0
     */
    public void refresh(int userId, String albumId) {
        // 首先判断要操作的相册是否存在，如果不存在就不需要继续了
        Album album = findOneOrElseThrow(userId, albumId);

        Media mediaSearch = Media
                .builder()
                .albumId(albumId)
                .build();
        List<Media> mediaList = wideColumnExecutor.findAll(mediaSearch, Media.class);

        if (mediaList.size() > 0) {
            mediaList.sort(Comparator
                    .comparing(Media::getUploadTime)
                    .reversed());

            int imageCount = 0;
            int videoCount = 0;
            long size = 0L;

            for (Media media : mediaList) {
                if (MediaType.IMAGE.equals(media.getType())) {
                    imageCount++;
                } else if (MediaType.VIDEO.equals(media.getType())) {
                    videoCount++;
                } else {
                    log.error("未支持的媒体文件类型：{}", media.getType());
                }

                size += media.getSize();
            }

            album.setImageCount(imageCount);
            album.setVideoCount(videoCount);
            album.setSize(size);

            // 处理相册封面图
            Media lastMedia = mediaList.get(0);
            if (MediaType.IMAGE.equals(lastMedia.getType())) {
                album.setCoverImagePath(lastMedia.getPath());
            } else if (MediaType.VIDEO.equals(lastMedia.getType())) {
                if (StringUtils.hasText(lastMedia.getThumbPath())) {
                    album.setCoverImagePath(lastMedia.getThumbPath());
                }
            }

            // 处理更新时间
            album.setUpdateTime(System.currentTimeMillis());

            wideColumnExecutor.update(album);
        }
    }
}
