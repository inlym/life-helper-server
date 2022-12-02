package com.inlym.lifehelper.location.region.repository;

import com.inlym.lifehelper.location.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 行政区划地区存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/1
 * @since 1.7.2
 **/
public interface RegionRepository extends JpaRepository<Region, Integer> {
    /**
     * 通过父级 ID 查找子级地区
     *
     * @param parentId 父级的 ID
     *
     * @return List<Region>
     */
    List<Region> findAllByParentId(Integer parentId);
}
