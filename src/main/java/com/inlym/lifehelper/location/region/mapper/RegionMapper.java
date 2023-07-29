package com.inlym.lifehelper.location.region.mapper;

import com.inlym.lifehelper.location.region.entity.Region;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 地区实体映射器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/7/29
 * @since 2.0.2
 **/
@Mapper
public interface RegionMapper extends BaseMapper<Region> {}
