package com.inlym.lifehelper.photoalbum.entity;

import lombok.Data;

/**
 * 相册实体
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @since 1.3.0
 **/
@Data
public class Album {
    /** 相册 ID */
    private String id;

    /** 所属用户 ID */
    private Integer userId;

    /** 相册名称 */
    private String name;

    /** 相册描述 */
    private String description;
}
