package com.nts.iot.modules.system.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.system.dao.CheckPointMapper;
import com.nts.iot.modules.system.dto.IconDto;
import com.nts.iot.modules.system.model.CheckPoint;
import com.nts.iot.modules.system.model.CheckPointTask;
import com.nts.iot.modules.system.model.DictDetail;
import com.nts.iot.modules.system.model.TaskPoint;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.CheckPointService;
import com.nts.iot.modules.system.service.CheckPointTaskService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.RedisUtil;
import com.nts.iot.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class CheckPointServiceImpl extends ServiceImpl<CheckPointMapper, CheckPoint> implements CheckPointService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private CheckPointTaskService checkPointTaskService;

    /**
     * 查询所有必到点分页
     *
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public Object queryAll(String name, Pageable pageable, List<Long> jurisdiction) {
        Page<CheckPoint> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<CheckPoint> pageResult = baseMapper.selectByPage(page, name, jurisdiction);
//        IPage<CheckPoint> pageResult = page(page,
//                new QueryWrapper<CheckPoint>()
//                        .and(jurisdiction.size()>0,wrapper->{
//                            for(int i=0;i<jurisdiction.size();i++){
//                                wrapper.eq("jurisdiction", jurisdiction.get(i));
//                                if(i!=jurisdiction.size()-1){
//                                    wrapper.or();
//                                }
//                            }
//                            return  wrapper;
//                        })
//                        .eq(StringUtils.isNoneBlank(name),"name",name));
        return PageUtil.toPage(pageResult);
    }

    /**
     * create
     *
     * @param checkPoint
     * @return
     */
    @Override
    public Integer create(CheckPoint checkPoint, User user) {
        // 插入创建时间
        checkPoint.setCreateTime(System.currentTimeMillis());
        // 操作人id
        checkPoint.setCreator(String.valueOf(user.getId()));
        // 添加必到点
        Integer count = baseMapper.insert(checkPoint);

        List<CheckPoint> checkPointList = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.CHECK_POINT + checkPoint.getJurisdiction()), CheckPoint.class);
        if (checkPointList != null && checkPointList.size() > 0) {
            checkPointList.add(checkPoint);
            // 将必到点加入到缓存中
            redisUtil.addRedis(RedisKey.CHECK_POINT + checkPoint.getJurisdiction(), JsonUtil.getJson(checkPointList));
        } else {
            List<CheckPoint> cps = new ArrayList<>();
            cps.add(checkPoint);
            redisUtil.addRedis(RedisKey.CHECK_POINT + checkPoint.getJurisdiction(), JsonUtil.getJson(cps));
        }

        // 获得主表主键
        Long pk = checkPoint.getId();
        // 添加任务
        saveTask(pk, JsonUtil.jsonConvertList(checkPoint.getCheckPointTaskList(), CheckPointTask.class), user);
        return count;
    }

    /**
     * update
     *
     * @param checkPoint
     */
    @Override
    public void update(CheckPoint checkPoint, User user) {
        checkPoint.setUpdateTime(System.currentTimeMillis());
        checkPoint.setUpdater(String.valueOf(user.getId()));
        // 修改必到点
        baseMapper.updateById(checkPoint);
        // 获得主表主键
        Long pk = checkPoint.getId();
        // 通过编号查询任务集合
        List<CheckPointTask> checkPointTaskList = JsonUtil.jsonConvertList(checkPoint.getCheckPointTaskList(), CheckPointTask.class);

        String json = redisUtil.getData(RedisKey.CHECK_POINT + checkPoint.getJurisdiction());

        // 从缓存中得到bidaodianlist
        List<CheckPoint> checkPointList = JsonUtil.jsonConvertList(json, CheckPoint.class);
        if (checkPointList != null && checkPointList.size() > 0) {
            List<CheckPoint> cps = new ArrayList<>();
            // 遍历必到点
            for (int i = 0; i < checkPointList.size(); i++) {
                // id相等修改
                if (checkPointList.get(i).getId() == checkPoint.getId()) {
                    cps.add(checkPoint);
                } else {
                    cps.add(checkPointList.get(i));
                }
            }
            // 将必到点加入到缓存中
            redisUtil.addRedis(RedisKey.CHECK_POINT + checkPoint.getJurisdiction(), JsonUtil.getJson(cps));
        }
        // 不可以修改辖区
//        else {
//            List<CheckPoint> cps = new ArrayList<>();
//            cps.add(checkPoint);
//            redisUtil.addRedis(RedisKey.CHECK_POINT + checkPoint.getJurisdiction(), JsonUtil.getJson(cps));
//        }

        // 删除TaskidList
        List<Long> taskIdList = JsonUtil.jsonConvertList(checkPoint.getTaskId(), Long.class);
        if (taskIdList != null && taskIdList.size() > 0) {
            checkPointTaskService.removeByIds(taskIdList);
        }

        // 新增加的对象
        List<CheckPointTask> checkPointTaskListNew = new ArrayList<>();
        for (int i = 0; i < checkPointTaskList.size(); i++) {
            if ("1".equals(checkPointTaskList.get(i).getIsNew())) {
                checkPointTaskListNew.add(checkPointTaskList.get(i));
            } else {
                checkPointTaskList.get(i).setUpdater(user.getId());
                checkPointTaskList.get(i).setUpdateTime(System.currentTimeMillis());
                checkPointTaskService.updateById(checkPointTaskList.get(i));
            }
        }
        // 添加任务
        saveTask(pk, checkPointTaskListNew, user);
    }

    /**
     * delete
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        // 根据必到点id删除任务表数据
        checkPointTaskService.deleteTaskById(id);
        //查询必到点
        CheckPoint checkPoint = baseMapper.selectById(id);
        // 删除必到点
        baseMapper.deleteById(id);
        // 从缓存中得到bidaodianlist
        List<CheckPoint> checkPointList = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.CHECK_POINT + checkPoint.getJurisdiction()), CheckPoint.class);
        if (checkPointList != null && checkPointList.size() > 0) {
            List<CheckPoint> cps = new ArrayList<>();
            for (int i = 0; i < checkPointList.size(); i++) {
                if (checkPointList.get(i).getId() != id) {
                    cps.add(checkPointList.get(i));
                }
            }
            redisUtil.deleteByKey(RedisKey.CHECK_POINT + checkPoint.getJurisdiction());
            redisUtil.addRedis(RedisKey.CHECK_POINT + checkPoint.getJurisdiction(), JsonUtil.getJson(cps));
        }

    }

    /**
     * 获得所有必到点信息
     *
     * @return
     */
    @Override
    public void getCheckPointAll() {
        List<CheckPoint> checkPoint = baseMapper.selectAll();
        if (checkPoint != null && checkPoint.size() > 0) {
            // 清空
            for (int i = 0; i < checkPoint.size(); i++) {
                redisUtil.deleteByKey(RedisKey.CHECK_POINT + checkPoint.get(i).getJurisdiction());
            }

            // 添加
            for (int i = 0; i < checkPoint.size(); i++) {
                List<CheckPoint> cpList = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.CHECK_POINT + checkPoint.get(i).getJurisdiction()), CheckPoint.class);
                if (cpList != null && cpList.size() > 0) {
                    cpList.add(checkPoint.get(i));
                    redisUtil.addRedis(RedisKey.CHECK_POINT + checkPoint.get(i).getJurisdiction(), JsonUtil.getJson(cpList));
                } else {
                    List<CheckPoint> cps = new ArrayList<>();
                    cps.add(checkPoint.get(i));
                    redisUtil.addRedis(RedisKey.CHECK_POINT + checkPoint.get(i).getJurisdiction(), JsonUtil.getJson(cps));
                }
            }
        }
    }

    /**
     * 添加任务
     */
    private void saveTask(Long pk, List<CheckPointTask> checkPointTaskList, User user) {
        for (int i = 0; i < checkPointTaskList.size(); i++) {
            checkPointTaskList.get(i).setCheckPointId(pk);
            checkPointTaskList.get(i).setCreateTime(System.currentTimeMillis());
            checkPointTaskList.get(i).setCreator(user.getId());
        }
        // 插入任务表
        checkPointTaskService.saveBatch(checkPointTaskList);
    }

    @Override
    public List<TaskPoint> getCheckPointsByDept(Long deptId) {
        List<TaskPoint> taskPoints = new ArrayList<>();
        Map<String, Object> columnMap = new HashMap<>(1);
        columnMap.put("jurisdiction", deptId);

        List<CheckPoint> points = baseMapper.selectByMap(columnMap);
        points.forEach(it -> {
            TaskPoint taskPoint = new TaskPoint();
            taskPoint.setPointId(it.getId());
            taskPoint.setPointName(it.getName());
            taskPoint.setPointRemark(it.getRemark());
            taskPoint.setLongitude(it.getLongitude());
            taskPoint.setLatitude(it.getLatitude());
            taskPoints.add(taskPoint);
        });
        return taskPoints;
    }

    @Override
    public List<IconDto> selectIcon(Long jurisdiction) {
        return baseMapper.selectIcon(jurisdiction);
    }

    @Override
    public List<DictDetail> selectTasKType() {
        return baseMapper.selectTasKType();
    }
}
