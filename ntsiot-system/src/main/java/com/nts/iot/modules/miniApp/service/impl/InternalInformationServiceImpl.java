package com.nts.iot.modules.miniApp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.miniApp.dao.InternalInformationMapper;
import com.nts.iot.modules.miniApp.model.InternalInformation;
import com.nts.iot.modules.miniApp.model.Picture;
import com.nts.iot.modules.miniApp.service.InternalInformationService;
import com.nts.iot.modules.miniApp.service.PictureService;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class InternalInformationServiceImpl extends ServiceImpl<InternalInformationMapper, InternalInformation> implements InternalInformationService {

    @Autowired
    private PictureService pictureService;

    /**
     * 添加
     *
     * @param internalInformation
     */
    @Override
    public void saveInternalInformation(InternalInformation internalInformation) {
        internalInformation.setCreateTime(System.currentTimeMillis());
        baseMapper.insert(internalInformation);
        // 主键
        Long pk = internalInformation.getId();

        // 插入图片
        if (pk != null && internalInformation.getSourceData() != null && internalInformation.getSourceData().size() > 0) {
            // 循环图片path
            for (int i = 0; i < internalInformation.getSourceData().size(); i++) {
                Picture wdf = new Picture();
                wdf.setPk(pk);
                wdf.setPath(internalInformation.getSourceData().get(i));
                wdf.setType(getType(internalInformation.getSourceData().get(i)));
                pictureService.saveFile(wdf);
            }
        }
    }

    /**
     * 列表查询
     *
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Map queryAll(Pageable pageable, String startTime, String endTime, List<Long> jurisdiction) {
        Page<InternalInformation> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        //IPage<InternalInformation> pageResult = baseMapper.selectByPage(page, startTime, endTime, jurisdiction);
        IPage<InternalInformation> pageResult=this.page(page,
                new QueryWrapper<InternalInformation>()
                                    .and(jurisdiction.size()>0,wrapper->{
                                        for(int i=0;i<jurisdiction.size();i++){
                                            wrapper.eq("jurisdiction", jurisdiction.get(i));
                                            if(i!=jurisdiction.size()-1){
                                                wrapper.or();
                                            }
                                        }
                                        return  wrapper;
                                    })
                                    .gt(StringUtils.isNotBlank(startTime),"create_time",startTime)
                                    .lt(StringUtils.isNotBlank(endTime),"create_time",endTime)
                                    .orderByDesc("create_time"));
        return PageUtil.toPage(pageResult);
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

    /**
     * 获得类别
     *
     * @param path
     * @return
     */
    private String getType(String path) {
        String result = "";
        if (path != null && !"".equals(path)) {
            String postfix = path.substring(path.lastIndexOf(".") + 1);
            System.out.println(path.substring(path.lastIndexOf(".")));
            if ("mp4".equals(postfix)) {
                result = "INTERNAL_INFORMATION_VIDEO";
            }
            if ("jpg".equals(postfix) || "png".equals(postfix)) {
                result = "INTERNAL_INFORMATION_IMG";
            }
        }
        return result;
    }
}
