package com.inlym.lifehelper.location.region.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 行政区划地区
 *
 * <h2>说明
 * <p>可能是省、市、区的任意一级。
 *
 * <h2>数据表说明
 * <p>数据表是跑任务一次性生成的，之后表数据保持不变，直到下次全量替换。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/1
 * @since 1.7.2
 **/
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    /**
     * 行政区划唯一标识（adcode）
     *
     * <h2>说明
     * <p>`id` 自带，不需要数据表生成。
     */
    @Id
    private Integer id;

    /**
     * 地区简称
     *
     * <h2>数据说明
     * <p>“省”和“市”两级一般不为空，“区县级”大多为空。
     *
     * <h2>数据示例
     * <p>"浙江"、"广西"、"杭州"……
     */
    private String name;

    /** 地区全称 */
    private String fullName;

    /**
     * 行政区划等级
     *
     * <h2>数据说明
     * <p>值范围为：`1`, `2`, `3`, 分别表示第1、2、3级行政区划
     * <p>一般第1级表示省、直辖市、自治区，第2级为市或直辖市的区。
     */
    private Integer level;

    /**
     * 父级的 ID
     *
     * <h2>数据说明
     * <p>第1级为空，其余级不为空。
     */
    private Integer parentId;
}
