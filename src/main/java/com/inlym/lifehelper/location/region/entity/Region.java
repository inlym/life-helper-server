package com.inlym.lifehelper.location.region.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 中国行政区划地区（省、市、区三级）
 * <p>
 * <h2>数据表说明
 * <p>1. 表中数据是从“腾讯位置服务”的 API 一次性获取并存入的。
 * <p>2. 表中数据不可单行修改，只可走批量任务一次性清空并存入。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/7/29
 * @since 2.0.2
 **/
@Table("region")
@Data
public class Region {
    /**
     * 主键 ID，行政区划唯一标识（adcode）
     * <p>
     * <h2>说明
     * <p>1. `id` 自带，不需要数据表生成。
     * <p>2. `id` 是6位数字。
     */
    @Id(keyType = KeyType.None)
    private Integer id;

    /**
     * 地区简称
     * <p>
     * <h2>数据说明
     * <p>“省”和“市”两级一般不为空，“区县级”大多为空。
     * <p>
     * <h2>数据示例
     * <p>"浙江"、"广西"、"杭州"……
     */
    private String shortName;

    /**
     * 地区全称
     * <p>
     * <h2>数据示例
     * <p>"浙江省"、"杭州市"……
     */
    private String fullName;

    /**
     * 行政区划等级
     * <p>
     * <h2>数据说明
     * <p>值范围为：`1`, `2`, `3`, 分别表示第1、2、3级行政区划
     * <p>一般第1级表示省、直辖市、自治区，第2级为市或直辖市的区。
     */
    private Integer level;

    /**
     * 父级的 ID
     * <p>
     * <h2>数据说明
     * <p>第1级为空，其余级不为空。
     */
    private Integer parentId;

    /** 创建时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime createTime;

    /** 更新时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime updateTime;
}
