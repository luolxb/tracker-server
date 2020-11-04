package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.MiniAppConstants;
import com.nts.iot.modules.miniApp.model.Picture;
import com.nts.iot.modules.miniApp.service.PictureService;
import com.nts.iot.modules.system.dao.RealCheckPointMapper;
import com.nts.iot.modules.system.dto.TaskDetailDto;
import com.nts.iot.modules.system.model.RealCheckPoint;
import com.nts.iot.modules.system.service.RealCheckPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
class RealCheckPointServiceImpl extends ServiceImpl<RealCheckPointMapper, RealCheckPoint> implements RealCheckPointService {

    @Autowired
    private PictureService pictureService;

    @Override
    public List<RealCheckPoint> getPointsByTaskId(long taskId) {
        Map<String, Object> columnMap = new HashMap<>(1);
        columnMap.put("real_task_id", taskId);
        return baseMapper.selectByMap(columnMap);
    }

    /**
     * 查询任务详细
     *
     * @param id
     * @return
     */
    @Override
    public List<TaskDetailDto> selectTaskDetail(Long id) {
        List<TaskDetailDto> taskDetailDtoList = baseMapper.selectTaskDetail(id);
        if (taskDetailDtoList != null && taskDetailDtoList.size() > 0) {
            for (int i = 0; i < taskDetailDtoList.size(); i++) {
                List<Picture> pictureList = new ArrayList<>();
                pictureList.addAll(pictureService.getFileList(taskDetailDtoList.get(i).getTaskId(), MiniAppConstants.REAL_TASK_IMG));
                pictureList.addAll(pictureService.getFileList(taskDetailDtoList.get(i).getTaskId(), MiniAppConstants.REAL_TASK_MP3));
                pictureList.addAll(pictureService.getFileList(taskDetailDtoList.get(i).getTaskId(), MiniAppConstants.REAL_TASK_VIDEO));
                taskDetailDtoList.get(i).setPictureList(pictureList);
            }
        }
        return taskDetailDtoList;
    }
}
