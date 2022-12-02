package com.inlym.lifehelper.location.region;

import com.inlym.lifehelper.location.region.entity.Region;
import com.inlym.lifehelper.location.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 获取省级行政区列表
     *
     * @since 1.7.2
     */
    public List<Region> getProvinceList() {
        return repository.findAllByParentId(null);
    }

    /**
     * 获取指定省级行政区下的市级行政区列表（对于直辖市是对应“区”）
     *
     * @param provinceId 省级行政区 ID
     *
     * @since 1.7.2
     */
    public List<Region> getCityList(int provinceId) {
        return repository.findAllByParentId(provinceId);
    }
}
