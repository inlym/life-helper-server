package com.inlym.lifehelper.membership.point.mapper;

import com.inlym.lifehelper.membership.point.entity.PointTransaction;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分交易记录存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/8
 * @since 2.0.2
 **/
@Mapper
public interface PointTransactionMapper extends BaseMapper<PointTransaction> {}
