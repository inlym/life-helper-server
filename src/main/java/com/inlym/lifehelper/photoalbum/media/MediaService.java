package com.inlym.lifehelper.photoalbum.media;

import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.common.base.aliyun.ots.widecolumn.WideColumnExecutor;
import com.inlym.lifehelper.photoalbum.album.AlbumService;
import com.inlym.lifehelper.photoalbum.album.entity.Album;
import com.inlym.lifehelper.photoalbum.media.constant.MediaType;
import com.inlym.lifehelper.photoalbum.media.entity.Media;
import com.inlym.lifehelper.photoalbum.media.pojo.MediaVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
@RequiredArgsConstructor
public class MediaService {
    private final WideColumnExecutor wideColumnExecutor;

    private final AlbumService albumService;

    private final OssService ossService;

    /**
     * 获取封面图路径
     *
     * @param media 媒体文件实体
     *
     * @since 1.4.0
     */
    private String calcAlbumCoverImagePath(Media media) {
        if (MediaType.IMAGE.equals(media.getType())) {
            return media.getPath();
        }

        if (MediaType.VIDEO.equals(media.getType()) && StringUtils.hasText(media.getThumbPath())) {
            return media.getThumbPath();
        }

        return null;
    }

    /**
     * 从数据库冲重新根据实际数据获取相册的封面图
     *
     * @param media 媒体文件实体
     *
     * @since 1.4.0
     */
    private String regainAlbumCoverImagePath(Media media) {
        List<Media> list = wideColumnExecutor.findAll(media, Media.class);
        if (list.size() > 0) {
            return calcAlbumCoverImagePath(list.get(0));
        }

        return null;
    }

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
        vo.setUrl(ossService.concatUrl(media.getPath()));
        vo.setUploadTime(media.getUploadTime());
        if (StringUtils.hasText(media.getThumbPath())) {
            vo.setThumbUrl(media.getThumbPath());
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
        Album album = albumService.findOneOrElseThrow(userId, media.getAlbumId());

        long now = System.currentTimeMillis();

        // 给一些字段赋初始值
        media.setCreateTime(now);

        Media media2 = wideColumnExecutor.create(media);

        // 添加媒体文件，需要更新相册实体的缓存字段
        album.setUpdateTime(now);
        album.setTotal(album.getTotal() + 1);
        album.setCoverImagePath(calcAlbumCoverImagePath(media));

        albumService.update(album);

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
        Album album = albumService.findOneOrElseThrow(userId, media.getAlbumId());

        wideColumnExecutor.delete(media);

        album.setUpdateTime(System.currentTimeMillis());
        album.setTotal(album.getTotal() - 1);
        album.setCoverImagePath(regainAlbumCoverImagePath(media));

        albumService.update(album);
    }

    /**
     * 获取指定相册的媒体文件列表
     *
     * @param userId  用户 ID
     * @param albumId 相册 ID
     *
     * @since 1.4.0
     */
    public List<Media> list(int userId, String albumId) {
        // 首先判断要操作的相册是否存在，如果不存在就不需要继续了
        albumService.findOneOrElseThrow(userId, albumId);

        Media media = Media
            .builder()
            .albumId(albumId)
            .build();

        return wideColumnExecutor.findAll(media, Media.class);
    }
}
