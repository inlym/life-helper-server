package com.inlym.lifehelper.photoalbum.album.entity;

import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyField;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 相册实体
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @since 1.3.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    // ================================= 主键列 =================================

    /** 所属用户 ID - 分区键 */
    @PrimaryKeyField(name = "uid", order = 1, hashed = true)
    private Long userId;

    /** 相册 ID */
    @PrimaryKeyField(order = 2, mode = PrimaryKeyMode.SIMPLE_UUID)
    private String albumId;

    // ================================= 属性列 =================================

    /** 相册名称 */
    private String name;

    /** 创建时间 */
    private Long createTime;

    /**
     * 更新时间
     *
     * <h2>哪些操作会被当成“更新”？
     * <li>1. 修改相册信息。
     * <li>2. 上传、删除照片等。
     */
    private Long updateTime;

    // ------------------------------------------------------------------------
    // ------------------ 以下是“缓存字段”——数据可以通过重新计算获得 -----------------
    // ------------------ 为了避免查询时计算压力过大，存入当前实体 -------------------

    /** 图片总数 */
    private Integer imageCount;

    /** 视频总数 */
    private Integer videoCount;

    /**
     * 相册文件总大小（单位：B）
     *
     * <h2>备注（2022.09.13）
     * <p>只将照片和视频本身计入，视频的封面图占用的空间不计入。
     */
    private Long size;

    /**
     * 封面图的在 OSS 中的存储路径
     *
     * <h2>封面图的来源：
     * <p>最后一次上传的图片或视频（取首帧缩略图）。
     */
    private String coverImagePath;
}
