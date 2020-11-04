package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.MiniAppConstants;
import com.nts.iot.modules.miniApp.dto.TrajectoryDto;
import com.nts.iot.modules.miniApp.model.Picture;
import com.nts.iot.modules.miniApp.service.PictureService;
import com.nts.iot.modules.miniApp.util.ToolUtil;
import com.nts.iot.modules.system.dao.RealTaskMapper;
import com.nts.iot.modules.system.dto.MissionDto;
import com.nts.iot.modules.system.dto.MissionPointDto;
import com.nts.iot.modules.system.dto.MissionPointTaskDto;
import com.nts.iot.modules.system.model.*;
import com.nts.iot.modules.system.service.*;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static com.nts.iot.constant.MiniAppConstants.*;

@Service
class RealTaskServiceImpl extends ServiceImpl<RealTaskMapper, RealTask> implements RealTaskService {

    @Autowired
    RealCheckPointService realCheckPointService;

    @Autowired
    RealCheckPointTaskService realCheckPointTaskService;

    @Autowired
    TaskPatrolmanService taskPatrolmanService;

    @Autowired
    TaskPointService taskPointService;

    @Autowired
    CheckPointService checkPointService;

    @Autowired
    CheckPointTaskService checkPointTaskService;

    @Autowired
    private TaskApprovalService taskApprovalService;

    @Autowired
    MessageService messageService;

    @Autowired
    PictureService pictureService;

    @Value("${engineServerUrl}")
    private String E_URL;

    /**
     * 创建任务以及相关数据
     *
     * @param taskInstance 模板bean
     * @param yyyymmmdd    年月日
     *                     <p>此处创建任务需要把任务模板中的数据都copy一份出来
     *                     以防止修改模板后关联数据发生变化</p>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(TaskInstance taskInstance, String yyyymmmdd) {
        /* 根据模板task id 为每个执行者生成任务 */
        List<RealTask> newTasks = new ArrayList<>();
        QueryWrapper<TaskPatrolman> taskPatrolmanWrapper = new QueryWrapper<>();
        taskPatrolmanWrapper.eq("task_id", taskInstance.getId());
        List<TaskPatrolman> taskPatrolmens = taskPatrolmanService.list(taskPatrolmanWrapper);
        List<Message> messageList = new ArrayList<>();
        for (TaskPatrolman taskPatrolman : taskPatrolmens) {
            RealTask realTask = new RealTask();
            realTask.setId(null);
            realTask.setTaskName(taskInstance.getTaskName());
            realTask.setTaskTempId(taskInstance.getId());
            realTask.setYyyymmdd(yyyymmmdd);
            // 任务初始化状态
            realTask.setStatus(MISSION_UNSUCCESS);
            // 开始时间
            realTask.setBeginTime(null);
            // 结束时间
            realTask.setOverTime(null);
            // TODO  ※ 生成日期 加上 时间 （如果 开始时间的结束时间大于开始时间，跨一天,且不支持也不准备支持跨多天）
            LocalDate localDate = LocalDate.now();
            LocalDate nextDate = localDate.plusDays(1);
            Date startData = DateUtil.parse(localDate + " " + taskInstance.getSysPatrolStartTime(), "yyyy-MM-dd HH:mm:ss");
            Date endData = DateUtil.parse(localDate + " " + taskInstance.getSysPatrolEndTime(), "yyyy-MM-dd HH:mm:ss");
            if (startData.getTime() >= endData.getTime()) {
                endData = DateUtil.parse(nextDate + " " + taskInstance.getSysPatrolEndTime(), "yyyy-MM-dd HH:mm:ss");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //系统巡更开始时间
            realTask.setSysPatrolStartTime(sdf.format(startData));
            //  系统巡更结束时间
            realTask.setSysPatrolEndTime(sdf.format(endData));
            realTask.setPath(null);
            //  是否启动
            realTask.setStartUp(taskInstance.getStartUp());
            realTask.setRemark(taskInstance.getRemark());
            /* 巡更人员相关 */
            realTask.setPatrolmanId(taskPatrolman.getPatrolmanId());
            realTask.setPatrolman(taskPatrolman.getPatrolman());
            realTask.setPhone(taskPatrolman.getPhone());
            realTask.setDeptId(taskPatrolman.getDeptId());
            realTask.setCreateTime(System.currentTimeMillis());
            realTask.setIsApproval(0L);
            newTasks.add(realTask);
        }
        // 保存task
        this.saveBatch(newTasks);


        /* 根据task id 查询任务必到点并保存到实际生成任务的必到点表中 */
        QueryWrapper<TaskPoint> taskPointWrapper = new QueryWrapper<>();
        taskPointWrapper.eq("task_id", taskInstance.getTemplateId());
        List<TaskPoint> taskPoints = taskPointService.list(taskPointWrapper);
        // 必到点
        List<RealCheckPoint> realCheckPoints = new ArrayList<>();
        for (RealTask realTask : newTasks) {
            for (int i = 0; i < taskPoints.size(); i++) {
                Long checkId = taskPoints.get(i).getPointId();
                CheckPoint checkPoint = checkPointService.getById(checkId);
                if (checkPoint != null) {
                    /* 设定实际生成任务的必到点 */
                    RealCheckPoint realCheckPoint = new RealCheckPoint();
                    realCheckPoint.setId(null);
                    // ※ task id 使用真实id
                    realCheckPoint.setTaskId(realTask.getId());
                    // 必到点ID （非模板）
                    realCheckPoint.setPointId(checkId);
                    realCheckPoint.setLongitude(checkPoint.getLongitude());
                    realCheckPoint.setLatitude(checkPoint.getLatitude());
                    // todo todo todo暂定index
                    realCheckPoint.setSort((long) i);
                    realCheckPoint.setScope(checkPoint.getScope());
                    realCheckPoint.setName(checkPoint.getName());
                    realCheckPoint.setRemark(checkPoint.getRemark());
                    realCheckPoint.setJurisdiction(checkPoint.getJurisdiction());
                    realCheckPoint.setCreateTime(checkPoint.getCreateTime());
                    realCheckPoint.setCreator(checkPoint.getCreator());
                    realCheckPoint.setUpdateTime(checkPoint.getUpdateTime());
                    realCheckPoint.setUpdater(checkPoint.getUpdater());
                    realCheckPoint.setMainStartTime(realTask.getSysPatrolStartTime());
                    realCheckPoint.setMainEndTime(realTask.getSysPatrolEndTime());
                    realCheckPoints.add(realCheckPoint);
                }
            }
            /* 生成消息 */
            Message newMessage = new Message();
            newMessage.setId(null);
            newMessage.setContent("您有新的巡逻任务，请查收！");
            newMessage.setType("task");
            newMessage.setTypeId(String.valueOf(realTask.getId()));
            newMessage.setUserId(realTask.getPatrolmanId());
            newMessage.setCreateTime(System.currentTimeMillis());
            newMessage.setIsRead(false);
            messageList.add(newMessage);
        }
        realCheckPointService.saveBatch(realCheckPoints);
        // 保存message 消息
        messageService.saveBatch(messageList);
        List<RealCheckPointTask> realCheckPointTasks = new ArrayList<>();
        for (RealCheckPoint realCheckPoint : realCheckPoints) {
            /* 根据必到点查询必到点任务 */
            QueryWrapper<CheckPointTask> checkPointTaskQueryWrapper = new QueryWrapper<>();
            checkPointTaskQueryWrapper.eq("check_point_id", realCheckPoint.getPointId());
            List<CheckPointTask> checkPointTasks = checkPointTaskService.list(checkPointTaskQueryWrapper);
            for (CheckPointTask checkPointTask : checkPointTasks) {
                RealCheckPointTask realCheckPointTask = new RealCheckPointTask();
                realCheckPointTask.setId(null);
                // 保存实际生成的必到点ID
                realCheckPointTask.setRealPointId(realCheckPoint.getId());
                realCheckPointTask.setName(checkPointTask.getName());
                // 是否完成任务,默认为false  未完成
                realCheckPointTask.setIsOver(false);
                realCheckPointTask.setTarget(checkPointTask.getTarget());
                realCheckPointTask.setRemark(checkPointTask.getRemark());
                realCheckPointTask.setCreateTime(checkPointTask.getCreateTime());
                realCheckPointTask.setCreator(checkPointTask.getCreator());
                realCheckPointTask.setUpdateTime(checkPointTask.getUpdateTime());
                realCheckPointTask.setUpdater(checkPointTask.getUpdater());
                // TODO  ※ 生成日期 加上 时间 （如果 开始时间的结束时间大于开始时间，跨一天,且不支持也不准备支持跨多天）
                LocalDate localDate = LocalDate.now();
                LocalDate nextDate = localDate.plusDays(1);
                // 如果必到点任务没有设置开始和结束时间，则取总任务的开始和结束时间
                if (ToolUtil.isOneEmpty(checkPointTask.getStartTime(), checkPointTask.getEndTime())) {
                    realCheckPointTask.setStartTime(realCheckPoint.getMainStartTime());
                    realCheckPointTask.setEndTime(realCheckPoint.getMainEndTime());
                } else {
                    Date startData = DateUtil.parse(localDate + " " + checkPointTask.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                    Date endData = DateUtil.parse(localDate + " " + checkPointTask.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                    if (startData.getTime() >= endData.getTime()) {
                        endData = DateUtil.parse(nextDate + " " + checkPointTask.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    realCheckPointTask.setStartTime(sdf.format(startData));
                    realCheckPointTask.setEndTime(sdf.format(endData));
                }
                realCheckPointTask.setIsPerform(checkPointTask.getIsPerform());
                realCheckPointTask.setUploadImgNumber(checkPointTask.getUploadImgNumber());
                realCheckPointTasks.add(realCheckPointTask);
            }
        }
        realCheckPointTaskService.saveBatch(realCheckPointTasks);
    }

    @Override
    public RealTask getTaskById(Long taskId) {
        RealTask task = baseMapper.selectById(taskId);
        if (task != null) {
            // 必到点信息
            task.setPoints(realCheckPointService.getPointsByTaskId(taskId));
            // 审核信息
            task.setTaskApproval(taskApprovalService.getApprovalByTaskId(taskId));
        }
        // 巡更轨迹
        if (!ToolUtil.isOneEmpty(task.getLockBarcode(), task.getBeginTime(), task.getOverTime())) {
            task.setTrajectorys(this.getPatrolTrajectory(task.getLockBarcode(), task.getBeginTime(), task.getOverTime()));
        }

        return task;
    }

    /**
     * 巡更轨迹
     *
     * @param lockBarcode
     * @param startTime
     * @param endTime
     * @return
     */
    private List<TrajectoryDto> getPatrolTrajectory(String lockBarcode, Long startTime, Long endTime) {
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

    @Override
    public void changeApprovalStatus(Long taskId) {
        baseMapper.changeApprovalStatus(taskId);
    }

    @Override
    public List<MissionDto> getTaskDtoByUserId(String userId) {
        List<MissionDto> missionDtoList = new ArrayList<>();
        /* 查询出未完成和进行中的任务 */
        QueryWrapper<RealTask> realTaskWrapper = new QueryWrapper<>();
        realTaskWrapper.eq("patrolman_id", Long.valueOf(userId));
        realTaskWrapper.and(wrapper -> wrapper.eq("status", MISSION_UNSUCCESS).or().eq("status", MISSION_ON));
        // 根据id 正序排列
        realTaskWrapper.orderByAsc("id");
        List<RealTask> realTaskList = this.list(realTaskWrapper);
        if (realTaskList == null || realTaskList.size() == 0 || realTaskList.get(0) == null){
            return missionDtoList;
        }
        List<Long> taskIds = new ArrayList<>();
        realTaskList.forEach(p -> taskIds.add(p.getId()));
        /* 查询出任务的必到点 */
        QueryWrapper<RealCheckPoint> checkPointWrapper = new QueryWrapper<>();
        checkPointWrapper.in("real_task_id", taskIds);
        List<RealCheckPoint> checkPointAll = realCheckPointService.list(checkPointWrapper);
        List<RealCheckPointTask> subTaskAll = realCheckPointTaskService.list();

        for (RealTask task : realTaskList) {
            MissionDto missionDto = new MissionDto();
            missionDto.setId(task.getId());
            missionDto.setTitle(task.getTaskName());
            missionDto.setStartTime(task.getSysPatrolStartTime().substring(0, 16));
            missionDto.setEndTime(task.getSysPatrolEndTime().substring(0, 16));
            missionDto.setStarted(MISSION_ON.equals(task.getStatus()));
            List<MissionPointDto> checkPointList = getPointsByTaskId(checkPointAll, task.getId());
            // 结果值
            List<MissionPointDto> missionPointDtoList = new ArrayList<>();
            for (MissionPointDto pointDto : checkPointList) {
                /* 获取子任务并保存 */
                missionPointDtoList.add(getSubMission(subTaskAll, pointDto));
            }
            missionDto.setPoints(missionPointDtoList);
            missionDtoList.add(missionDto);
        }
        return missionDtoList;
    }


    /**
     * 根据taskId 从所有 task 中查询必到点
     *
     * @param checkPointAll 所有 task
     * @param taskId        任务id
     * @return 必到点list
     */
    private List<MissionPointDto> getPointsByTaskId(List<RealCheckPoint> checkPointAll, Long taskId) {
        List<MissionPointDto> missionPointDtos = new ArrayList<>();
        for (RealCheckPoint checkPoint : checkPointAll) {
            if (taskId.equals(checkPoint.getTaskId())) {
                MissionPointDto missionPointDto = new MissionPointDto();
                missionPointDto.setId(checkPoint.getId());
                missionPointDto.setTitle(checkPoint.getName());
                missionPointDto.setLatitude(checkPoint.getLatitude());
                missionPointDto.setLongitude(checkPoint.getLongitude());
                missionPointDto.setScope(checkPoint.getScope());
                missionPointDtos.add(missionPointDto);
            }
        }
        return missionPointDtos;
    }

    /**
     * 根据必到点id 从所有 子任务 中查询子任务
     *
     * @param subMissonAll 所有子任务
     * @param pointDto     必到点dto
     * @return 子任务dto
     */
    private MissionPointDto getSubMission(List<RealCheckPointTask> subMissonAll, MissionPointDto pointDto) {
        List<MissionPointTaskDto> missionPointDtos = new ArrayList<>();
        Boolean isOver = true;
        /* 遍历子任务 */
        for (RealCheckPointTask subMission : subMissonAll) {
            /* 找到子任务 */
            if (pointDto.getId().equals(subMission.getRealPointId())) {
                MissionPointTaskDto subTaskDto = new MissionPointTaskDto();
                subTaskDto.setId(subMission.getId());
                subTaskDto.setTitle(subMission.getName());
                subTaskDto.setTarget(subMission.getTarget());
                subTaskDto.setUploadImgNumber(subMission.getUploadImgNumber());
                if (subMission.getStartTime() != null) {
                    subTaskDto.setStartTime(subMission.getStartTime().substring(10, 16));
                } else {
                    subTaskDto.setStartTime("");
                }
                if (subMission.getEndTime() != null) {
                    subTaskDto.setEndTime(subMission.getEndTime().substring(10, 16));
                } else {
                    subTaskDto.setEndTime("");
                }
                /* 如果该点下面有一个为未完成，则该点均为未完成任务 */
                if (!subMission.getIsOver()) {
                    isOver = false;
                }
                subTaskDto.setCompleted(subMission.getIsOver());
                subTaskDto.setRemark(subMission.getRemark());
                /*   查询子任务相关图片 视频 mp3 */
                QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("pk", subTaskDto.getId());
                List<Picture> urlList = pictureService.list(queryWrapper);
                // 图片 视频 url
                List<String> sourceData = new ArrayList<>();
                // 音频 url
                for (Picture picture : urlList) {
                    if (REAL_TASK_VIDEO.equals(picture.getType()) || REAL_TASK_IMG.equals(picture.getType())) {
                        // 图片
                        sourceData.add(picture.getPath());
                    } else if (REAL_TASK_MP3.equals(picture.getType())) {
                        // 音频
                        subTaskDto.setFrequency(picture.getPath());
                    }
                }
                subTaskDto.setSourceData(sourceData);
                // 堆入DTO
                missionPointDtos.add(subTaskDto);
            }
        }
        pointDto.setSubMission(missionPointDtos);
        pointDto.setIsOver(isOver);
        return pointDto;
    }

    @Override
    public List<RealTask> getTask() {
        QueryWrapper<RealTask> queryWrapper = new QueryWrapper<>();
        // 未完成、进行中的
        queryWrapper.eq("status", MISSION_UNSUCCESS)
                .or()
                .eq("status", MISSION_ON);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void changeStatus(RealTask task) {
        baseMapper.updateById(task);
    }


}
