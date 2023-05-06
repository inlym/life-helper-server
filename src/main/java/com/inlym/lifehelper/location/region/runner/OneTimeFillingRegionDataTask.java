package com.inlym.lifehelper.location.region.runner;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.inlym.lifehelper.extern.tencentmap.TencentMapHttpService;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapListRegionResponse;
import com.inlym.lifehelper.location.region.entity.Region;
import com.inlym.lifehelper.location.region.service.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 一次性填充行政区划数据任务
 *
 * <h2>说明
 * <p>这个不是定时任务，仅在需要的时候从后台手动执行一次即可。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/1
 * @since 1.7.2
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class OneTimeFillingRegionDataTask extends JavaProcessor {
    private final TencentMapHttpService tencentMapHttpService;

    private final RegionRepository regionRepository;

    @Override
    public ProcessResult process(JobContext context) {
        log.info("[任务] 执行填充行政区划数据任务");
        execTask();
        return new ProcessResult(true);
    }

    /**
     * 初始化地区数据
     *
     * <h2>主要流程
     * <p>从接口请求获取全量数据，并存入数据库中。
     *
     * @since 1.7.2
     */
    private void execTask() {
        // 开始前检查数据库中是否有数据，如果有则停止
        if (regionRepository.findAll()
                            .size() > 0) {
            throw new RuntimeException("地区数据库中有值，请先清空后再开始任务！");
        }

        TencentMapListRegionResponse data = tencentMapHttpService.listRegions();

        List<TencentMapListRegionResponse.Region> provinceList = data.getResult()
                                                                     .get(0);

        List<TencentMapListRegionResponse.Region> cityList = data.getResult()
                                                                 .get(1);

        List<TencentMapListRegionResponse.Region> districtList = data.getResult()
                                                                     .get(2);

        for (TencentMapListRegionResponse.Region item : provinceList) {
            Region province = buildBaseEntity(item);
            province.setLevel(1);

            regionRepository.save(province);

            // 处理“市”级
            int cityStart = item.getChildrenIndex()
                                .get(0);
            int cityEnd = item.getChildrenIndex()
                              .get(1);

            for (int i = cityStart; i < cityEnd + 1; i++) {
                TencentMapListRegionResponse.Region cityItem = cityList.get(i);
                Region city = buildBaseEntity(cityItem);
                city.setLevel(2);
                city.setParentId(province.getId());

                regionRepository.save(city);

                // 处理“区县”级
                if (cityItem.getChildrenIndex() != null) {
                    int districtStart = cityItem.getChildrenIndex()
                                                .get(0);

                    int districtEnd = cityItem.getChildrenIndex()
                                              .get(1);

                    for (int j = districtStart; j < districtEnd + 1; j++) {
                        TencentMapListRegionResponse.Region districtItem = districtList.get(j);
                        Region district = buildBaseEntity(districtItem);
                        district.setLevel(3);
                        district.setParentId(city.getParentId());

                        regionRepository.save(district);
                    }
                }
            }
        }
    }

    /**
     * 构建地区实体
     *
     * @param item 响应数据获取的地区列表项
     *
     * @since 1.7.2
     */
    private Region buildBaseEntity(TencentMapListRegionResponse.Region item) {
        return Region.builder()
                     .id(Integer.valueOf(item.getId()))
                     .shortName(item.getName())
                     .fullName(item.getFullName())
                     .build();
    }
}
