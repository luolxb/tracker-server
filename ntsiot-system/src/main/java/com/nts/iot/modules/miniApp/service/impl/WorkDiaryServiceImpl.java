package com.nts.iot.modules.miniApp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.miniApp.dao.WorkDiaryMapper;
import com.nts.iot.modules.miniApp.model.Picture;
import com.nts.iot.modules.miniApp.model.WorkDiary;
import com.nts.iot.modules.miniApp.service.PictureService;
import com.nts.iot.modules.miniApp.service.WorkDiaryService;
import com.nts.iot.utils.PageUtil;
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
public class WorkDiaryServiceImpl extends ServiceImpl<WorkDiaryMapper, WorkDiary> implements WorkDiaryService {

    @Autowired
    private PictureService pictureService;

    /**
     * 添加工作日志上报
     *
     * @param workDiary
     */
    @Override
    public void saveWorkDiary(WorkDiary workDiary) {

        //创建时间
        workDiary.setCreateTime(System.currentTimeMillis());

        // 插入内容
        baseMapper.insert(workDiary);

        // 主键
        Long pk = workDiary.getId();

        // 插入图片
        if (pk != null && workDiary.getSourceData() != null && workDiary.getSourceData().size() > 0) {
            // 循环图片path
            for (int i = 0; i < workDiary.getSourceData().size(); i++) {
                Picture wdf = new Picture();
                wdf.setPk(pk);
                wdf.setPath(workDiary.getSourceData().get(i));
                wdf.setType(getType(workDiary.getSourceData().get(i)));
                pictureService.saveFile(wdf);
            }
        }

        // 插入语音
        if (workDiary.getFrequency() != null) {
            Picture wdf = new Picture();
            wdf.setPk(pk);
            wdf.setPath(workDiary.getFrequency());
            wdf.setType(getType(workDiary.getFrequency()));
            pictureService.saveFile(wdf);
        }
    }

    @Override
    public Map queryAll(Pageable pageable, String startTime, String endTime, List<Long> jurisdictions) {
        Page<WorkDiary> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<WorkDiary> pageResult = baseMapper.selectByPage(page, jurisdictions,startTime, endTime);
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
                result = "WORK_DIARY_VIDEO";
            }
            if ("jpg".equals(postfix) || "png".equals(postfix)) {
                result = "WORK_DIARY_IMG";
            }
            if ("mp3".equals(postfix)) {
                result = "WORK_DIARY_MP3";
            }
        }
        return result;
    }

}
