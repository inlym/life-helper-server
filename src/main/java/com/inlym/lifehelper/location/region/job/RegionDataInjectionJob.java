package com.inlym.lifehelper.location.region.job;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.inlym.lifehelper.extern.tencentmap.TencentMapHttpService;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapListRegionResponse;
import com.inlym.lifehelper.location.region.entity.Region;
import com.inlym.lifehelper.location.region.mapper.RegionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 行政区划数据注入任务
 * <p>
 * <h2>主要用途
 * <p>将从腾讯位置服务获取的行政区划数据一次性注入到数据表中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/7/29
 * @since 2.0.2
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class RegionDataInjectionJob extends JavaProcessor {
    private final RegionMapper regionMapper;

    private final TencentMapHttpService tencentMapHttpService;

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        log.info("[任务] 执行填充行政区划数据任务");
        execTask();
        return new ProcessResult(true);
    }

    /**
     * 封装要执行的任务
     *
     * @date 2023/7/29
     * @since 2.0.2
     */
    private void execTask() {
        // 开始前先检查数据表中是否已有数据，若有，则不执行任何操作
        // 备注：这里通过查 id 而不是使用 count 的原因是本身业务场景支持这么做且效率更高。
        Region bj = regionMapper.selectOneById(110000);
        if (bj != null) {
            throw new RuntimeException("地区数据库中有值，请先清空后再开始任务！");
        }

        // 从腾讯位置服务的 API 中获取数据
        TencentMapListRegionResponse data = tencentMapHttpService.listRegions();

        // 三个列表分别是省、市、区的数据
        List<TencentMapListRegionResponse.Region> provinceList = data
            .getResult()
            .get(0);

        List<TencentMapListRegionResponse.Region> cityList = data
            .getResult()
            .get(1);

        List<TencentMapListRegionResponse.Region> districtList = data
            .getResult()
            .get(2);

        for (TencentMapListRegionResponse.Region provinceItem : provinceList) {
            Region province = new Region();
            province.setId(Integer.valueOf(provinceItem.getId()));
            province.setShortName(provinceItem.getName());
            province.setFullName(provinceItem.getFullName());
            province.setLevel(1);

            // 单条省级数据存入
            regionMapper.insertSelectiveWithPk(province);

            // 处理该省级底下的市级数据
            int cityStart = provinceItem
                .getChildrenIndex()
                .get(0);
            int cityEnd = provinceItem
                .getChildrenIndex()
                .get(1);

            for (int i = cityStart; i < cityEnd + 1; i++) {
                TencentMapListRegionResponse.Region cityItem = cityList.get(i);
                Region city = new Region();
                city.setId(Integer.valueOf(cityItem.getId()));
                city.setShortName(cityItem.getName());
                city.setFullName(cityItem.getFullName());
                city.setLevel(2);
                city.setParentId(province.getId());

                // 单条市级数据存入
                regionMapper.insertSelectiveWithPk(city);

                // 处理该市级底下的区县级数据
                if (cityItem.getChildrenIndex() != null) {
                    int districtStart = cityItem
                        .getChildrenIndex()
                        .get(0);

                    int districtEnd = cityItem
                        .getChildrenIndex()
                        .get(1);

                    for (int j = districtStart; j < districtEnd + 1; j++) {
                        TencentMapListRegionResponse.Region districtItem = districtList.get(j);
                        Region district = new Region();
                        district.setId(Integer.valueOf(districtItem.getId()));
                        district.setShortName(districtItem.getName());
                        district.setFullName(districtItem.getFullName());
                        district.setLevel(3);
                        district.setParentId(city.getId());

                        // 单条区县级数据存入
                        regionMapper.insertSelectiveWithPk(district);
                    }
                }
            }
        }
    }
}
