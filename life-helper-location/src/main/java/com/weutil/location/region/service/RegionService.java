package com.weutil.location.region.service;

import com.weutil.location.region.entity.Region;
import com.weutil.location.region.exception.RegionNotFoundException;
import com.weutil.location.region.mapper.RegionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 行政区划位置服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/7/30
 * @since 2.0.2
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class RegionService {
    private final RegionMapper regionMapper;

    /**
     * 获取省级行政单位的缩写名称
     *
     * @param adcode 行政区划代码
     *
     * @return 返回值示例："浙江"、"上海"、"香港"、…
     * @date 2023/7/30
     * @since 2.0.2
     */
    public String getProvinceShortName(int adcode) {
        // 6位代码的前2位代表省级
        int provinceCode = adcode / 10000;
        int id = provinceCode * 10000;

        Region region = getById(id);
        return region.getShortName();
    }

    /**
     * 通过行政区划代码查找地区
     *
     * @param adcode 行政区划代码
     *
     * @date 2023/7/30
     * @since 2.0.2
     */
    public Region getById(int adcode) {
        Region region = regionMapper.selectOneById(adcode);

        if (region == null) {
            throw new RegionNotFoundException("未找到地区， adcode=" + adcode);
        }

        return region;
    }
}
