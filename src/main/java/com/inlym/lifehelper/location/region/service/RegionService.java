package com.inlym.lifehelper.location.region.service;

import com.inlym.lifehelper.location.region.entity.Region;
import com.inlym.lifehelper.location.region.exception.RegionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 行政区划数据服务
 *
 * <h2>主要用途
 * <p>处理与省市区等行政区划相关的数据问题。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/2
 * @since 1.7.2
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository repository;

    /**
     * 获取地区
     *
     * @param id 地区的 adcode
     *
     * @since 1.7.2
     */
    public Region getById(int id) {
        return repository.findById(id)
                         .orElseThrow(RegionNotFoundException::new);
    }
}
