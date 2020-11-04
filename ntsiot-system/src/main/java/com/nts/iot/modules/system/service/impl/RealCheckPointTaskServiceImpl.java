package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.miniApp.dto.MaRequestBody;
import com.nts.iot.modules.miniApp.dto.TrajectoryDto;
import com.nts.iot.modules.miniApp.model.Picture;
import com.nts.iot.modules.miniApp.service.PictureService;
import com.nts.iot.modules.system.dao.RealCheckPointTaskMapper;
import com.nts.iot.modules.system.model.CheckPoint;
import com.nts.iot.modules.system.model.RealCheckPoint;
import com.nts.iot.modules.system.model.RealCheckPointTask;
import com.nts.iot.modules.system.model.RealTask;
import com.nts.iot.modules.system.model.*;
import com.nts.iot.modules.system.service.CheckPointService;
import com.nts.iot.modules.system.service.RealCheckPointService;
import com.nts.iot.modules.system.service.RealCheckPointTaskService;
import com.nts.iot.modules.system.service.RealTaskService;
import com.nts.iot.util.EnclosureUtil;
import com.nts.iot.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nts.iot.constant.MiniAppConstants.*;

@Service
class RealCheckPointTaskServiceImpl extends ServiceImpl<RealCheckPointTaskMapper, RealCheckPointTask> implements RealCheckPointTaskService {


    @Autowired
    PictureService pictureService;

    @Autowired
    private CheckPointService checkPointService;

    @Autowired
    RealCheckPointService realCheckPointService;

    @Autowired
    RealTaskService realTaskService;

    @Value("${engineServerUrl}")
    private String E_URL;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveSubTask(MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        // 子任务id
        Long subTaskId = Long.valueOf(requestBody.getSubTaskId());
        RealCheckPointTask subTask = this.getById(subTaskId);
        /* 如果没有此任务，则返回错误 */
        if (subTask == null) {
            result.put("code", 500);
            result.put("message", "保存失败，没有该子任务");
            return result;
        }
        int limit = 0;
        if (subTask.getUploadImgNumber() != null) {
            limit = Integer.valueOf(subTask.getUploadImgNumber());
        }
        if (requestBody.getSourceData() == null || requestBody.getSourceData().size() < limit) {
            result.put("code", 500);
            result.put("message", "图片上传数量小于规定数量");
            return result;
        }
        // 主键
        Long pk = subTask.getId();
        int pictureCount = 0;
        int videoCount = 0;
        int soundCount = 0;
        // 插入图片、视频
        if (pk != null && requestBody.getSourceData() != null && requestBody.getSourceData().size() > 0) {
            // 循环图片path
            for (String source : requestBody.getSourceData()) {
                Picture wdf = new Picture();
                wdf.setPk(pk);
                wdf.setPath(source);
                String type = getType(source);
                // 视频
                if (REAL_TASK_VIDEO.equals(type)) {
                    videoCount++;
                } else if (REAL_TASK_IMG.equals(type)) {
                    pictureCount++;
                }
                wdf.setType(type);
                pictureService.saveFile(wdf);
            }

        }
        // 插入语音
        if (requestBody.getFrequency() != null) {
            Picture wdf = new Picture();
            wdf.setPk(pk);
            wdf.setPath(requestBody.getFrequency());
            wdf.setType(getType(requestBody.getFrequency()));
            pictureService.saveFile(wdf);
            soundCount++;
        }
        /* 如果有此任务，更新 上传相关数据  */
        // 纬度
        subTask.setLatitude(requestBody.getLatitude());
        // 经度
        subTask.setLongitude(requestBody.getLongitude());
        // 已完成
        subTask.setIsOver(true);
        // 备注
        subTask.setRemark(requestBody.getContent());
        // 完成情况（插入n张照片或视频）
        String taskState = "已经上传";
        if (soundCount != 0) {
            taskState = taskState + soundCount + "条语音 ";
        }
        if (pictureCount != 0) {
            taskState = taskState + pictureCount + "张图片 ";
        }
        if (videoCount != 0) {
            taskState = taskState + videoCount + "段视频 ";
        }
        subTask.setTaskState(taskState);
        this.updateById(subTask);
        result.put("code", 200);
        result.put("message", "保存成功");
        return result;
    }

    /**
     * 获得任务停留时间 （毫秒）
     *
     * @param pointId   必到点id
     * @param startTime 开始时间戳
     * @param endTime   结束时间戳
     * @param lockNo    锁编号
     * @return
     */
    @Override
    public Long getTaskTime(Long pointId, Long startTime, Long endTime, String lockNo) {
        Long res = 0L;
        // 先查询必到点
        CheckPoint checkPoint = checkPointService.getById(pointId);
        List<TrajectoryDto> trajectoryDtoList = getPatrolTrajectory(startTime, endTime, lockNo);
        long start = 0L;
        if (trajectoryDtoList != null && trajectoryDtoList.size() > 0) {
            for (int i = 0; i < trajectoryDtoList.size(); i++) {
                // 半径  圆心=经度,纬度  比较点= 经度,纬度
                Boolean flag = EnclosureUtil.checkPoint(
                        checkPoint.getScope(),
                        checkPoint.getLongitude() + "," + checkPoint.getLatitude(),
                        trajectoryDtoList.get(i).getLongitude() + "," + trajectoryDtoList.get(i).getLatitude());
                // 存在true
                if (flag) {
                    if (start == 0L) {
                        start = trajectoryDtoList.get(i).getMis();
                    } else {
                        res += trajectoryDtoList.get(i).getMis() - start;
                        start = trajectoryDtoList.get(i).getMis();
                    }
                }
            }
        }
        return res;
    }

    /**
     * 打卡
     * @param requestBody
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> toClockOn(MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        Long subTaskId =  Long.valueOf(requestBody.getSubTaskId());
        RealCheckPointTask subTask = this.getById(subTaskId);
        if (subTask == null) {
            result.put("code",500);
            result.put("message","没有找到该子任务");
            return result;
        }
        subTask.setIsOver(true);
        subTask.setLongitude(requestBody.getLongitude());
        subTask.setLatitude(requestBody.getLatitude());
        subTask.setRemark("打开完成");
        this.updateById(subTask);

        /* 查询子任务中是否有逗留任务，如果存在，更新停留时间 */
        RealCheckPoint point =  realCheckPointService.getById(subTask.getRealPointId());
        if (point!=null){
            RealTask task = realTaskService.getById(point.getTaskId());
            if (task!=null && task.getLockBarcode()!=null){
                /* 查看 */
                QueryWrapper<RealCheckPointTask> subTaskWrapper = new QueryWrapper<>();
                subTaskWrapper.eq("real_point_id",point.getTaskId());
                List<RealCheckPointTask> sbTaskList =  this.list(subTaskWrapper);
                for (RealCheckPointTask sTask: sbTaskList){
                    // 目标为停留时间
                    if ("0".equals(sTask.getTarget())){
                        DateFormat df3 = DateFormat.getTimeInstance();
                        /**/
                        Date startData = DateUtil.parse(sTask.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                        Date endTimeData = DateUtil.parse(sTask.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                        if (subTask.getRealPointId()!=null && startData!=null &&  endTimeData!=null &&  task.getLockBarcode() !=null){
                            // 获取停留时间
                            Long stopTime = getTaskTime(subTask.getRealPointId(), startData.getTime(), endTimeData.getTime(), task.getLockBarcode());
                            if (stopTime==null){
                                sTask.setLongitude(requestBody.getLongitude());
                                sTask.setLatitude(requestBody.getLatitude());
                                sTask.setRemark("停留时间未知");
                                sTask.setIsOver(false);
                            }else {
                                sTask.setLongitude(requestBody.getLongitude());
                                sTask.setLatitude(requestBody.getLatitude());
                                sTask.setRemark("停留"+stopTime/1000/60 + " 分钟");
                                sTask.setIsOver(true);
                            }
                            /* 更新 */
                            this.updateById(sTask);
                        }
                    }
                }
            }
        }
        result.put("code",200);
        result.put("message","打开成功");
        return result;
    }

    /**
     * 巡更轨迹
     *
     * @param startTime
     * @param endTime
     * @param lockBarcode
     * @return
     */
    private List<TrajectoryDto> getPatrolTrajectory(Long startTime, Long endTime, String lockBarcode) {
        Map<String, Object> map = new HashMap<>();
        map.put("lockNo", lockBarcode);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        String url = E_URL + "/findRecord?lockNo={lockNo}&startTime={startTime}&endTime={endTime}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, map);
        List<TrajectoryDto> trajectoryDtos = JsonUtil.jsonConvertList(responseEntity.getBody(), TrajectoryDto.class);
        return trajectoryDtos;
    }


    /**
     * 获得类别
     *
     * @param path 路径
     * @return 类型string
     */
    private String getType(String path) {
        String result = "";
        if (path != null && !"".equals(path)) {
            String postfix = path.substring(path.lastIndexOf(".") + 1);
            if ("mp4".equals(postfix)) {
                result = REAL_TASK_VIDEO;
            }
            if ("jpg".equals(postfix)||"png".equals(postfix)) {
                result = REAL_TASK_IMG;
            }
            if ("mp3".equals(postfix)) {
                result = REAL_TASK_MP3;
            }
        }
        return result;
    }

    @Override
    public List<RealCheckPointTask> queryAll(){
        return baseMapper.queryAll();
    }


}
