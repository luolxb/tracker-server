package com.nts.iot.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dao.DeviceFenceMapper;
import com.nts.iot.modules.system.dao.FenceMapper;
import com.nts.iot.modules.system.model.*;
import com.nts.iot.modules.system.model.vo.DeviceFenceVo;
import com.nts.iot.modules.system.model.vo.DeviceVo;
import com.nts.iot.modules.system.model.vo.FenceStatisticRq;
import com.nts.iot.modules.system.model.vo.FenceStatisticVo;
import com.nts.iot.modules.system.service.DeviceFenceService;
import com.nts.iot.modules.system.service.DeviceService;
import com.nts.iot.modules.system.service.FenceService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RestResponse;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.RedisUtil;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author zhc@rnstec.com
 * @Description
 * @Date 2019-05-05 10:40
 * @Version: 1.0
 */
@Service
public class FenceServiceImpl extends ServiceImpl<FenceMapper, Fence> implements FenceService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DeviceFenceService deviceFenceService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceFenceMapper deviceFenceMapper;

    @Override
    @Transactional(readOnly = true)
    public Map queryAll(String name, String type, Long userId, Pageable pageable) {
        Page<Fence> page = new Page(pageable.getPageNumber(), pageable.getPageSize());
        IPage<Fence> pageResult = this.baseMapper.selectByPage(page, name, type, userId);
        List<Fence> records = pageResult.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            records.forEach(record -> {
                // 根据围栏id 获取设备集合
                List<DeviceVo> deviceVos = deviceService.queryListByFenceId(record.getId());
                record.setDeviceVos(deviceVos);
            });
        }
        return PageUtil.toPage(pageResult);
    }

//    @Override
//    public List<Fence> selectListByUserId(Long userId) {
//        return baseMapper.selectListByUserId(userId);
//    }

    @Override
    public void initFences() {
        // 获取数据库中所有围栏数据
        List<Fence> fenceList = this.list();
        // 使用java8 groupingBy 根据围栏ID进行分组
        Map<Long, List<Fence>> collect = fenceList
                .stream()
                .collect(Collectors.groupingBy(Fence::getId));
        Set<Long> longs = collect.keySet();
        for (Long l : longs) {
            List<Fence> fences = collect.get(l);
            redisUtil.addRedis(RedisKey.RESTRICTIONS_FENCE + l, JSON.toJSONString(fences));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Fence fence, User user) {
        fence.setCreateTime(System.currentTimeMillis());
        fence.setCreator(user.getUsername());
        checkDeviceFenceBind(fence);

        // 保存围栏信息
        boolean save = this.save(fence);
        if (!save) {
            throw new BadRequestException("新增围栏失败,Failed to add fence");
        }
        // 保存设备设备围栏的关联
        if (!CollectionUtils.isEmpty(fence.getDeviceIds())) {
            fence.getDeviceIds().forEach(id -> {
                DeviceFence deviceFence = new DeviceFence();
                deviceFence.setDeviceId(id);
                deviceFence.setFenceId(fence.getId());
                boolean save1 = deviceFenceService.save(deviceFence);
                if (!save1) {
                    throw new BadRequestException("新增围栏失败,Failed to add fence");
                }
            });
        }

        // 将新增围栏数据保存到缓存中
        redisUtil.addRedis(RedisKey.RESTRICTIONS_FENCE + fence.getId(), JsonUtil.getJson(Arrays.asList(fence)));
    }

    /**
     * 检查设备与围栏绑定情况 一个设备只能与一个围栏绑定
     */
    void checkDeviceFenceBind(Fence fence) {
        // 围栏可能没有绑定设备 直接返回
        if (CollectionUtils.isEmpty(fence.getDeviceIds())) {
            return;
        }
        // 根据设备IDS 获取设备名称，编号，围栏id ，围栏名称
        List<DeviceFenceVo> deviceFenceVoByDeviceId = deviceFenceMapper.getDeviceFenceVoByDeviceId(fence.getDeviceIds());
        String str = "";
        String strE = "";
        for (DeviceFenceVo deviceFenceVo : deviceFenceVoByDeviceId) {
            if (fence.getId() == null || !deviceFenceVo.getFenceId().equals(fence.getId()) ) {
                str +=
                        "<div>设备编码："
                                + (deviceFenceVo.getDeviceNo())
                                + " 已与围栏："
                                + (deviceFenceVo.getFenceName())
                                + ("绑定 </div>");

                strE +=
                        "<div>equipment number："
                                + (deviceFenceVo.getDeviceNo())
                                + " Has been fenced："
                                + (deviceFenceVo.getFenceName())
                                + ("Binding </div>");
            }

        }
        if (!CollectionUtils.isEmpty(deviceFenceVoByDeviceId)) {
            if (StringUtils.isNotBlank(str)) {
                throw new BadRequestException(str +","+strE);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFence(Fence fence) {
        checkDeviceFenceBind(fence);
        boolean update = this.updateById(fence);
        if (!update) {
            throw new BadRequestException("修改围栏失败,Failed to modify the fence");
        }
        // 修改设备围栏关联
        // 删除旧关联
        QueryWrapper<DeviceFence> deviceFenceQueryWrapper = new QueryWrapper<>();
        deviceFenceQueryWrapper.eq("fence_id", fence.getId());
        deviceFenceService.remove(deviceFenceQueryWrapper);
        // 建立新关联
        if (!CollectionUtils.isEmpty(fence.getDeviceIds())) {
            fence.getDeviceIds().forEach(id -> {
                DeviceFence deviceFence = new DeviceFence();
                deviceFence.setFenceId(fence.getId());
                deviceFence.setDeviceId(id);
                deviceFenceService.save(deviceFence);
            });
            // 修改围栏缓存数据
            redisUtil.updateRedis(RedisKey.RESTRICTIONS_FENCE + fence.getId(), JsonUtil.getJson(Arrays.asList(fence)));
        }
    }

    /**
     * 删除围栏
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFence(List<Long> ids) {
        // 删除围栏
        boolean b = this.removeByIds(ids);
        if (!b) {
            throw new BadRequestException("删除围栏异常,Abnormal delete fence");
        }

        // 删除设备围栏关联  有可能设备没有所属的围栏
        ids.forEach(id -> {
            QueryWrapper<DeviceFence> deviceFenceQueryWrapper = new QueryWrapper<>();
            deviceFenceQueryWrapper.eq("fence_id", id);
            boolean remove = deviceFenceService.remove(deviceFenceQueryWrapper);

        });
        // 删除缓存数据
        ids.forEach(id -> redisUtil.deleteByKey(RedisKey.RESTRICTIONS_FENCE + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Fence getById(Long deviceId, Long fenceId) {
        if (deviceId == null && fenceId == null) {
            throw new BadRequestException("请求参数不能为空,Request parameter cannot be empty");
        }
        Fence fence = this.baseMapper.selectById(deviceId, fenceId);
        if (null == fence) {
            throw new BadRequestException("没有找到围栏数据,No fence data found");
        }
        List<DeviceVo> deviceVos = deviceService.queryListByFenceId(fence.getId());
        fence.setDeviceVos(deviceVos);
        return fence;
    }

    /**
     * 围栏报表
     *
     * @param fenceStatisticRq
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Page<FenceStatisticVo> fenceStatistic(FenceStatisticRq fenceStatisticRq,Pageable pageable) {
        Page<FenceStatisticVo> page = new Page(pageable.getPageNumber(), pageable.getPageSize());

        List<FenceStatisticVo> fenceStatisticVoList = this.baseMapper.fenceStatistic(page, fenceStatisticRq);
        page.setRecords(fenceStatisticVoList);
        return page;

    }

    public static <T> List<T> jsonConvertList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

}
