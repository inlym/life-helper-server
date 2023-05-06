package com.inlym.lifehelper.location.region.service;

import com.inlym.lifehelper.location.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 行政区划地区存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/1
 * @since 1.7.2
 **/
public interface RegionRepository extends JpaRepository<Region, Integer> {}
