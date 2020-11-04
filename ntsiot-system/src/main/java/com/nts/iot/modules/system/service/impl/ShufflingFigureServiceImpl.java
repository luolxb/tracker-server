package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.ShufflingFigureMapper;
import com.nts.iot.modules.system.model.ShufflingFigure;
import com.nts.iot.modules.system.service.ShufflingFigureService;
import com.nts.iot.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ShufflingFigureServiceImpl extends ServiceImpl<ShufflingFigureMapper, ShufflingFigure> implements ShufflingFigureService {


    /**
     * 查询
     *
     * @param pageable
     * @param name
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Object queryAll(Pageable pageable, String name, String startTime, String endTime,List<Long> jurisdiction) {
        Page<ShufflingFigure> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<ShufflingFigure> pageResult = baseMapper.queryAll(page, name, startTime, endTime,jurisdiction);
//        IPage<ShufflingFigure> pageResult=page(page,
//                new QueryWrapper<ShufflingFigure>()
//                        .and(jurisdiction.size()>0,wrapper->{
//                            for(int i=0;i<jurisdiction.size();i++){
//                                wrapper.eq("jurisdiction", jurisdiction.get(i));
//                                if(i!=jurisdiction.size()-1){
//                                    wrapper.or();
//                                }
//                            }
//                            return  wrapper;
//                        })
//                        .like(StringUtils.isNoneBlank(name),"name",name)
//                        .gt(StringUtils.isNotBlank(startTime),"create_time",startTime)
//                        .lt(StringUtils.isNotBlank(endTime),"create_time",endTime));
        return PageUtil.toPage(pageResult);
    }

    /**
     * 根据辖区id查询轮播图
     * @param jurisdiction
     * @return
     */
    @Override
    public List<ShufflingFigure> findShufflingFigure(String jurisdiction) {
        return baseMapper.findShufflingFigure(jurisdiction);
    }

    /**
     * 根据url获得内容信息
     * @param url
     * @return
     */
    @Override
    public ShufflingFigure getContent(String url) {
        return baseMapper.getContent(url);
    }

    /**
     * 转换成时间戳
     *
     * @param date
     * @return
     */
    private String getTime(String date) {
        String time = "";
        // 2019-05-07T16:00:00.000Z
        if (date != null && !"".equals(date)) {
            date = date.split("T")[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                time = String.valueOf(sdf.parse(date).getTime() + 86400000L);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
    }

}
