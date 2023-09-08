package com.inlym.lifehelper.membership.point.mapper;

import com.inlym.lifehelper.membership.point.entity.PointProfile;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分余额存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/8/15
 * @since 2.0.2
 **/
@Mapper
public interface PointProfileMapper extends BaseMapper<PointProfile> {}
