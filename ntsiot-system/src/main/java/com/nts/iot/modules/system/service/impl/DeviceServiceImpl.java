package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dao.DeviceMapper;
import com.nts.iot.modules.system.dto.DeviceStateQueryDTO;
import com.nts.iot.modules.system.dto.DeviceStatisticDto;
import com.nts.iot.modules.system.model.*;
import com.nts.iot.modules.system.model.vo.*;
import com.nts.iot.modules.system.service.*;
import com.nts.iot.util.ExcelUtil;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RestResponse;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.nts.iot.constant.ConstantClass.switchlanguage;

/**
 * @PackageName: com.nts.iot.modules.system.service.impl
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-06 15:13
 **/
@Service
@Slf4j
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

    @Value("${track.proxy.url}")
    private String trackProxyUrl;

    @Value("${ntsiot.data.engine.host}")
    private String host;

    @Value("${ntsiot.data.engine.port}")
    private String port;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserDeviceService userDeviceService;

    @Autowired
    private NewDictService newDictService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DeviceFenceService deviceFenceService;

    /**
     * DEL_FLAG 删除标记 1：正常  2：删除
     */
    private static Integer DEL_FLAG = 2;

    /**
     * 新增设备
     *
     * @param deviceRq
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(DeviceRq deviceRq, User user) {

        deviceRq.setDeviceNo(deviceRq.getDeviceNo().trim());
        List<Device> deviceList = this
                .baseMapper
                .selectList(new QueryWrapper<Device>()
                        .eq("device_no", deviceRq.getDeviceNo())
                        .eq("del_flag", 1));
        if (!CollectionUtils.isEmpty(deviceList)) {
            throw new BadRequestException("设备号(IMEI)或者(SIM) 已存在请勿重复新增,The device number (IMEI) or (SIM) already exists  please do not add it again");
        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1);
        Date d = c.getTime();
        // 保存设备信息
        Device device = new Device();
        BeanUtils.copyProperties(deviceRq, device);
        // 默认数据初始化
        device.setCreateBy(user.getUsername());
        device.setCreateTime(new Date());
        device.setLeaveFactoryTime(new Date());
//        device.setInstallTime(new Date());
        device.setPlatformExpiresTime(d);
        device.setUserExpiresTime(d);
        if (StringUtils.isBlank(deviceRq.getDeviceName())) {
            device.setDeviceName(user.getUsername() + UUID.randomUUID().toString().replaceAll("-", ""));
        }
        boolean aBoolean = this.save(device);
        if (!aBoolean) {
            throw new BadRequestException("新增设备失败,Failed to add device");
        }
        // 保存设备与用户的关联信息
        UserDevice userDevice = new UserDevice();
        if (deviceRq.getCustomId() == 0) {
            throw new BadRequestException("所属客户输入错误,Customer's input error");
        }
        userDevice.setUserId(deviceRq.getCustomId());
        userDevice.setDeviceId(device.getId());
        boolean save = userDeviceService.save(userDevice);
        if (!save) {
            throw new BadRequestException("新增设备失败,Failed to add device");
        }

        // 将deviceNo新增到redis LOCK_LIST_KEY中
        List<String> lockList = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.TRACKER_LIST_KEY), String.class);
        if (CollectionUtils.isEmpty(lockList)) {
            lockList = new ArrayList<>();
        }
        lockList.add(device.getDeviceNo());
        Boolean addRedis = redisUtil.addRedis(RedisKey.TRACKER_LIST_KEY, JsonUtil.getJson(lockList));
        log.info("新增到redis成功与否：{}", addRedis);

        // lock_key  bike_key  新增到缓存中
        // 车牌号不为空
        if (StringUtils.isNotBlank(device.getCarNo())) {
            String key = RedisKey.VECHILE_KEY + device.getCarNo();
            redisUtil.addRedis(key, JSON.toJSONString(device));
        }
        // 车上设备、车锁
        String key = RedisKey.TRACKER_KEY + device.getDeviceNo();
        redisUtil.addRedis(key, JSON.toJSONString(device));
    }

    /**
     * 获取设备列表信息
     *
     * @param search
     * @param pageable
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceVo> queryPage(String search,
                                    String userExpiresTimeStart,
                                    String userExpiresTimeEnd,
                                    String platformExpiresTimeStart,
                                    String platformExpiresTimeEnd,
                                    Pageable pageable,
                                    Long userId) {
        Page<DeviceVo> page = new Page(pageable.getPageNumber(), pageable.getPageSize());

        List<DeviceVo> deviceVoList = this.baseMapper.queryPage(page, userId, search, userExpiresTimeStart,
                userExpiresTimeEnd, platformExpiresTimeStart, platformExpiresTimeEnd);
        page.setRecords(deviceVoList);
        return page;
    }

    /**
     * 查询全部数据
     *
     * @param search
     * @param userExpiresTimeStart
     * @param userExpiresTimeEnd
     * @param platformExpiresTimeStart
     * @param platformExpiresTimeEnd
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public RestResponse export(String search,
                               String userExpiresTimeStart,
                               String userExpiresTimeEnd,
                               String platformExpiresTimeStart,
                               String platformExpiresTimeEnd,
                               Long userId) {
        List<DeviceVo> deviceVoList = this.baseMapper.queryPage(null, userId, search, userExpiresTimeStart,
                userExpiresTimeEnd, platformExpiresTimeStart, platformExpiresTimeEnd);
        if (CollectionUtils.isEmpty(deviceVoList)) {
            throw new BadRequestException("导出数据为空,Export data is empty");
        }
        ExcelUtil<DeviceVo> excelUtil = new ExcelUtil<>(DeviceVo.class);
        return excelUtil.exportExcel(deviceVoList, "Device data");
    }

    /**
     * 计算当前时间的前后多少天时间
     *
     * @param day
     * @return
     */
    public static String getTime(Integer day) {
        if (null == day) {
            return null;
        }
        day = day > 0 ? --day : ++day;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, day);
        Date d = c.getTime();
        return format.format(d);
    }

    /**
     * 获取设备详情
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceVo detail(Long id) {
        DeviceVo deviceVo = this.baseMapper.selectById1(id);
        // 设备不为空并且已经激活 才可以获取redis数据
        if (null != deviceVo && deviceVo.getActivation() == 2) {
            //  设备定位信息
            getLocationData(deviceVo);
        }
        return deviceVo;
    }


    /**
     * 设备定位信息
     *
     * @param deviceVo
     */
    private void getLocationData(DeviceVo deviceVo) {
        // 获得redis key 年月日时:车辆条码号
        String key = RedisKey.VECHILE_STATE + deviceVo.getDeviceNo();
        String trackInfoJson = redisUtil.getData(key);
        if (StringUtils.isNotBlank(trackInfoJson)) {
            CollectMessage message = JsonUtil.jsonConvertObject(trackInfoJson, CollectMessage.class);
            if (message != null) {
                deviceVo.setLocation(message.getLongitude() + "," + message.getLatitude());
            }
        }
    }

    /**
     * 修改设备
     *
     * @param deviceRq
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DeviceRq deviceRq, User user) {
        Device device = new Device();
        // 检验参数
        checkParam(deviceRq);
        BeanUtils.copyProperties(deviceRq, device);
        Date date = new Date();
        // 如果激活码不为空 修改设备为激活状态，激活时间为当前时
        if (StringUtils.isNotBlank(deviceRq.getActivationCode())) {
            // 校验验证码
            valid(deviceRq.getActivationCode());
            device.setActivation(2);
            device.setActivationTime(date);
            device.setLastOnLineTime(date);
        }
        device.setUpdateTime(date);
        device.setUpdateBy(user.getUsername());

        int update = this.baseMapper.updateDevice(device);
        if (update <= 0) {
            throw new BadRequestException("修改设备信息失败,Failed to modify device information");
        }
        // 修改redis  中 lock_key  bike_key
        redisUtil.updateRedis(RedisKey.TRACKER_KEY + device.getDeviceNo(), JSON.toJSONString(device));
    }

    // 检验参数
    private void checkParam(DeviceRq deviceRq) {
        Device device = this.baseMapper.selectById(deviceRq.getId());
        if (device == null) {
            throw new BadRequestException("修改设备信息失败,设备信息不存在,Failed to modify device information, device information does not exist");
        }

        // 判断设备名称是否重复
        deviceRq.setDeviceName(deviceRq.getDeviceName().trim());
        Device deviceName = this.baseMapper.selectOne(new QueryWrapper<Device>()
                .eq("device_name", deviceRq.getDeviceName())
                .eq("del_flag", 1));
        if (null != deviceName &&
                StringUtils.isNotBlank(device.getDeviceName()) &&
                StringUtils.isNotBlank(deviceName.getDeviceName()) &&
                !deviceName.getDeviceName().equals(device.getDeviceName())) {
            throw new BadRequestException("修改设备信息失败,设备名称已存在,Failed to modify device information, device name already exists");
        }
        // 判断设备号是否重复
        deviceRq.setDeviceNo(deviceRq.getDeviceNo().trim());
        Device deviceNo = this.baseMapper.selectOne(new QueryWrapper<Device>()
                .eq("device_no", deviceRq.getDeviceNo())
                .eq("del_flag", 1));

        if (null != deviceNo &&
                StringUtils.isNotBlank(device.getDeviceNo()) &&
                StringUtils.isNotBlank(deviceNo.getDeviceNo()) &&
                !deviceNo.getDeviceNo().equals(device.getDeviceNo())) {
            throw new BadRequestException("修改设备信息失败,设备号已存在,Failed to modify device information, device number already exists");
        }

        // 判断设备（sim）是否重复
        if (StringUtils.isNoneBlank(deviceRq.getCardSim())) {
            deviceRq.setCardSim(deviceRq.getCardSim().trim());
        }
        Device deviceSim = this.baseMapper.selectOne(new QueryWrapper<Device>()
                .eq("card_sim", deviceRq.getCardSim())
                .eq("del_flag", 1));
        if (null != deviceSim && StringUtils.isNotBlank(device.getDeviceNo()) &&
                StringUtils.isNotBlank(deviceSim.getDeviceNo()) &&
                !deviceSim.getCardSim().equals(device.getCardSim())) {
            throw new BadRequestException("修改设备信息失败,设备（sim）已存在,Failed to modify device information, device (sim) already exists");
        }
    }

    /**
     * 校验激活码
     * TODO 待验证激活码
     *
     * @param activationCode
     */
    void valid(String activationCode) {

    }

    /**
     * 销售
     *
     * @param deviceRqList
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sell(List<DeviceRq> deviceRqList, User user) {
        deviceRqList.forEach(deviceRq -> {
            if (StringUtils.isBlank(deviceRq.getTargetName())) {
                throw new BadRequestException("销售目标人不能为空,Sales target person cannot be empty");
            }
            if (null == deviceRq.getTargetId()) {
                throw new BadRequestException("销售目标人不能为空,Sales target person cannot be empty");
            }
            if (null == deviceRq.getId()) {
                throw new BadRequestException("销售设备不能为空,Sales equipment cannot be empty");
            }
            UpdateWrapper<UserDevice> userDeviceUpdateWrapper = new UpdateWrapper<>();
            userDeviceUpdateWrapper.eq("device_id", deviceRq.getId());
            UserDevice userDevice = new UserDevice();
            userDevice.setUserId(deviceRq.getTargetId());
            // 修改设备与用户之间的关联
            boolean update = userDeviceService.update(userDevice, userDeviceUpdateWrapper);
            if (!update) {
                throw new BadRequestException("销售失败,Sales failure");
            }

            // 修改设备信息
            Device device = new Device();
            BeanUtils.copyProperties(deviceRq, device);
            device.setUpdateTime(new Date());
            device.setUpdateBy(user.getUsername());
            int i = this.baseMapper.updateById(device);
            if (i <= 0) {
                throw new BadRequestException("销售失败,Sales failure");
            }
            // 删除设备围栏关联
            deviceFenceService.remove(new QueryWrapper<DeviceFence>().eq("device_id", deviceRq.getId()));
        });

        deviceRqList.forEach(deviceRq -> {
            DeviceVo deviceVo = this.baseMapper.queryByDeviceId(deviceRq.getId());
            // 更新缓存中的 lock_key
            if (null != deviceVo) {
                deviceVo.setCustomId(deviceRq.getTargetId());
                deviceVo.setCustomName(deviceRq.getTargetName());
                redisUtil.updateRedis(RedisKey.TRACKER_KEY + deviceRq.getDeviceNo(), JSON.toJSONString(deviceVo));
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPass(User user, Long id) {
        Device device = new Device();
        // 默认密码为123456
        device.setId(id);
        device.setPassword("123456");
        device.setUpdateBy(user.getUsername());
        device.setUpdateTime(new Date());
        int i = this.baseMapper.updateById(device);
        if (i <= 0) {
            throw new BadRequestException("重置密码失败,Password reset failed");
        }
    }

    /**
     * 修改型号
     *
     * @param deviceRqList
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateType(List<DeviceRq> deviceRqList, User user) {
        deviceRqList.forEach(deviceRq -> {
            Device device = new Device();
            device.setId(deviceRq.getId());
            device.setDeviceType(deviceRq.getDeviceType());
            device.setUpdateTime(new Date());
            device.setUpdateBy(user.getUsername());
            // 修改设备类型
            int i = this.baseMapper.updateById(device);
            if (i <= 0) {
                throw new BadRequestException("修改型号失败,Failed to modify the model");
            }
        });
    }

    /**
     * 删除设备
     *
     * @param ids
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids, User user) {
        ids.forEach(id -> {
            Device device = new Device();
            device.setId(id);
            device.setDelFlag(DEL_FLAG);
            device.setUpdateTime(new Date());
            device.setUpdateBy(user.getUsername());
            // 修改设备状态为删除
            int i = this.baseMapper.updateById(device);
            if (i <= 0) {
                throw new BadRequestException("删除设备失败,Failed to delete device");
            }
            // 删除用户与围栏的管理
            userDeviceService.remove(new QueryWrapper<UserDevice>().eq("device_id", id));
            //删除设备围栏关联
            deviceFenceService.remove(new QueryWrapper<DeviceFence>().eq("device_id", id));
        });

        // 删除 lock_list_key
        // 根据ids 获取设备
        Collection<Device> devices = this.listByIds(ids);
        // 获取设备deviceNo的集合
        List<String> collect = devices.stream().map(Device::getDeviceNo).collect(Collectors.toList());
        // 获取设备缓存数据
        String data = redisUtil.getData(RedisKey.TRACKER_LIST_KEY);
        List<String> stringList = JsonUtil.jsonConvertList(data, String.class);
        // 移除
        stringList.removeAll(collect);
        redisUtil.updateRedis(RedisKey.TRACKER_LIST_KEY, JsonUtil.getJson(stringList));

        // 删除lock_key
        collect.forEach(no -> {
            redisUtil.deleteByKey(RedisKey.TRACKER_KEY + no);
        });


    }

    /**
     * 运营统计
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public UserOperationalVo operationalStatistics(Long userId) {
        // 获取三天前的时间
        String date = getTime(-3);
        return this.baseMapper.operationalStatistics(userId, date);
    }

    /**
     * 用户延期
     *
     * @param deviceRqList
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userExtension(List<DeviceRq> deviceRqList, User user) {
        deviceRqList.forEach(deviceRq -> {
            if (deviceRq.getUserExpiresTime() == null) {
                throw new BadRequestException("用户延期失败，用户到期不能为空,User extension failed, user expiration cannot be empty");
            }
            Device device = new Device();
            device.setId(deviceRq.getId());
            // 修改用户到期时间
            device.setUserExpiresTime(deviceRq.getUserExpiresTime());
            device.setUpdateTime(new Date());
            device.setUpdateBy(user.getUsername());
            int i = this.baseMapper.updateById(device);
            if (i < 0) {
                throw new BadRequestException("用户延期失败,User extension failed");
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceVo> queryPageOffLine(String search, String startDate, String endDate, Pageable pageable, Long userId) {
        Page<DeviceVo> page = new Page(pageable.getPageNumber(), pageable.getPageSize());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<DeviceVo> deviceVoList = this.baseMapper.queryPageOffLine(page, userId, search, startDate, endDate);
        deviceVoList.forEach(deviceVo -> {
            Date begin = null;
            Date end = null;
            String str = "";
            String strE = "";
            try {
                begin = dfs.parse(DateUtil.format(deviceVo.getLastOnLineTime(), "yyyy-MM-dd HH:mm:ss"));
                end = dfs.parse(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                throw new BadRequestException("时间转换异常,Abnormal time conversion");
            }
            //  计算时间
            long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
            long day = between / (24 * 3600);
            long hour = between % (24 * 3600) / 3600;
            long min = (between / 60) - day * 24 * 60 - hour * 60;
            long s = between % (24 * 60 * 60) % (60 * 60) % 60;
            if (day > 0) {
                str += day + "天";
                strE += day + "day ";
            }
            if (hour > 0) {
                str += hour + "小时";
                strE += hour + "hours ";
            }
            if (min > 0) {
                str += min + "分钟";
                strE += min + "minutes ";
            }
            if (s > 0) {
                str += s + "秒";
                strE += s + "seconds";
            }
            //  离线时常赋值
            deviceVo.setOfflineTime(str + "," + strE);
        });
        page.setRecords(deviceVoList);
        return page;
    }

    /**
     * 导入设备
     *
     * @param file
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer importDevice(MultipartFile file, Long userId, User user) throws Exception {
        if (null == file) {
            throw new BadRequestException("导入文件不能为空,Import file cannot be empty");
        }
        ExcelUtil<DeviceDownVo> util = new ExcelUtil<>(DeviceDownVo.class);
        List<DeviceDownVo> deviceDownVoList = util.importExcel(file.getInputStream());
        // 保存设备信息
        // 判断设备是否已经存在
        int count = 0;
        for (DeviceDownVo deviceDownVo : deviceDownVoList) {
            if (validDevice(deviceDownVo)) {
                Device device = new Device();
                BeanUtils.copyProperties(deviceDownVo, device);
                NewDict newDict = newDictService.getOne(new QueryWrapper<NewDict>().eq("value", deviceDownVo.getDeviceType()));
                if (null == newDict) {
                    throw new BadRequestException("设备类型错误,Device type error");
                }
                device.setDeviceType(newDict.getCode());
                device.setCreateTime(new Date());
                device.setCreateBy(user.getUsername());
                int insert = this.baseMapper.insert(device);
                if (insert <= 0) {
                    throw new BadRequestException("导入设备失败,Import device failed");
                }
                // 保存设备用户关联信息
                // 校验user id 是否存在
                User byId = userService.getById(userId);
                if (byId == null) {
                    throw new BadRequestException("用户不存在,User does not exist");
                }
                UserDevice userDevice = new UserDevice();
                userDevice.setUserId(userId);
                userDevice.setDeviceId(device.getId());
                boolean save = userDeviceService.save(userDevice);
                if (!save) {
                    throw new BadRequestException("导入设备失败,Import device failed");
                }
                count++;
            }
        }
        return count;
    }

    /**
     * 导入激活码
     *
     * @param file
     * @param userId
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer importActivationCode(MultipartFile file, Long userId, User user) throws Exception {
        if (null == file) {
            throw new BadRequestException("导入文件不能为空,Import file cannot be empty");
        }
        ExcelUtil<DeviceActivationCode> util = new ExcelUtil<>(DeviceActivationCode.class);
        List<DeviceActivationCode> deviceActivationCodeList = util.importExcel(file.getInputStream());
        if (CollectionUtils.isEmpty(deviceActivationCodeList)) {
            throw new BadRequestException("导入内容不能为空,Import file cannot be empty");
        }
        // 保存设备信息
        // 判断设备是否已经存在
        int count = 0;
        for (DeviceActivationCode activationCode : deviceActivationCodeList) {
            if (null == activationCode) {
                throw new BadRequestException("请检查导入模板,Please check the import template");
            }
            // 校验设备id 激活码不为空
            if (StringUtils.isBlank(activationCode.getDeviceNo())) {
                throw new BadRequestException("设备编号不能为空激活码:" + activationCode.getActivationCode() + ",Device number cannot be empty activation code" + activationCode.getActivationCode());
            }
            if (StringUtils.isBlank(activationCode.getActivationCode())) {
                throw new BadRequestException("激活码不能为空设备号:" + activationCode.getDeviceNo() + ",Activation code cannot be empty device number" + activationCode.getDeviceNo());
            }
            // 验证设备是否存在
            Device deviceNo = this.getOne(new QueryWrapper<Device>()
                    .eq("device_no", activationCode.getDeviceNo())
                    .eq("del_flag", 1)
                    .eq("enabled", 1));
            if (null == deviceNo) {
                throw new BadRequestException("设备不存在设备号:" + activationCode.getDeviceNo() + ",Device number does not exist" + activationCode.getDeviceNo());
            }
            // 校验激活码
            valid(activationCode.getActivationCode());
            // 修设备为激活状态，激活时间，激活码
            Device device = new Device();
            device.setUpdateTime(new Date());
            device.setUpdateBy(user.getUsername());
            device.setActivation(2);
            device.setActivationTime(new Date());
            device.setLastOnLineTime(new Date());
            device.setActivationCode(activationCode.getActivationCode());
            device.setId(deviceNo.getId());
            this.updateById(device);

            count++;
        }
        return count;
    }


    /**
     * 项目启动的时候将设备信息保存到Redis缓存中
     */
    @Override
    @Transactional(readOnly = true)
    public void initDevices() {
        // 查询没有禁用，没有删除的设备
        List<DeviceVo> deviceVos = this.baseMapper.queryPage(null, null, null, null, null, null, null);

        deviceVos.forEach(device -> {
            // 车牌号不为空
            if (StringUtils.isNotBlank(device.getCarNo())) {
                String key = RedisKey.VECHILE_KEY + device.getCarNo();
                redisUtil.addRedis(key, JSON.toJSONString(device));
            }
            // 车上设备、车锁
            String key = RedisKey.TRACKER_KEY + device.getDeviceNo();
            redisUtil.addRedis(key, JSON.toJSONString(device));
        });
        String data = redisUtil.getData(RedisKey.TRACKER_LIST_KEY);
        List<String> stringList = JsonUtil.jsonConvertList(data, String.class);
        if (CollectionUtils.isEmpty(stringList)) {
            stringList = new ArrayList<>();
        }
        for (DeviceVo device : deviceVos) {
            if (!stringList.contains(device.getDeviceNo())) {
                stringList.add(device.getDeviceNo());
            }
        }
        redisUtil.addRedis(RedisKey.TRACKER_LIST_KEY, JSON.toJSONString(stringList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeviceLocation(Device device) {
        try {
            int update = this.baseMapper.update(device, new UpdateWrapper<Device>().eq("device_no", device.getDeviceNo()));
            if (update <= 0) {
                log.error("定时修改设备位置失败：" + device.getDeviceNo());
            }
        } catch (Exception e) {
            log.error("定时修改设备位置失败：", e);
        }
    }

    /**
     * 设备的位置
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeviceLocationInfoVo> deviceLocation(String search, Long userId) {
        List<DeviceLocationInfoVo> deviceLocationInfoVos = new ArrayList<>();
        // 获取当前用户未删除设备
        List<DeviceVo> deviceVos = this.baseMapper.queryPage(null, userId, search, null, null, null, null);
        // 获取设备编号 deviceNo
        // 根据设备编号查询redis上该设备的位置信息，如果没有缓存中没有当前设备位置，就用mysql中的
        getDeviceLocationInfo(deviceLocationInfoVos, deviceVos);
        return deviceLocationInfoVos;
    }

    private void getDeviceLocationInfo(List<DeviceLocationInfoVo> deviceLocationInfoVos, List<DeviceVo> deviceVos) {
        deviceVos.forEach(deviceVo -> {
            String addr = "";

//            // 获取当前小时
//            String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd-HH");
            String key = RedisKey.VECHILE_STATE + deviceVo.getDeviceNo();
            String data = redisUtil.getData(key);
            DeviceLocationInfoVo deviceLocationInfoVo = new DeviceLocationInfoVo();

            if (deviceVo.getActivation() == 1 || StringUtils.isBlank(data)) {
                deviceLocationInfoVo.setDeviceNo(deviceVo.getDeviceNo());
                if (StringUtils.isNotBlank(deviceVo.getLocation())) {
                    String[] strings = null;
                    if (deviceVo.getLocation().contains(",")) {
                        strings = deviceVo.getLocation().split(",");
                    } else if (deviceVo.getLocation().contains(";")) {
                        strings = deviceVo.getLocation().split(";");
                    }
                    if (strings != null && strings.length > 0) {
                        deviceLocationInfoVo.setLatitude(strings[1]);
                        deviceLocationInfoVo.setLongitude(strings[0]);
                    }
                }
                deviceLocationInfoVo.setStatus(String.valueOf(deviceVo.getStatus()));
                deviceLocationInfoVo.setLocation(deviceVo.getLocation());
                deviceLocationInfoVo.setActivation(deviceVo.getActivation());
                deviceLocationInfoVo.setCustomName(deviceVo.getCustomName());
            } else {
                // 如果没有缓存中没有当前设备位置，就用mysql中的
                if (StringUtils.isNotBlank(data)) {
                    // 获得缓存中的数据
                    CollectMessage message = JsonUtil.jsonConvertObject(data, CollectMessage.class);
                    if (null != message) {
                        String latitude = message.getLatitude();
                        String longitude = message.getLongitude();
                        BeanUtils.copyProperties(message, deviceLocationInfoVo);
                        deviceLocationInfoVo.setCustomName(deviceVo.getCustomName());
                        deviceLocationInfoVo.setLocation(longitude + "," + latitude);
                        deviceLocationInfoVo.setStatus(String.valueOf(deviceVo.getStatus()));
                        deviceLocationInfoVo.setActivation(deviceVo.getActivation());
                        if (StringUtils.isNotBlank(deviceLocationInfoVo.getLatitude()) &&
                                StringUtils.isNotBlank(deviceLocationInfoVo.getLongitude())) {
                            try {
                                String url = trackProxyUrl + "/geocode/getBaiDuMap?language=" + switchlanguage +
                                        "&lat=" + Double.parseDouble(deviceLocationInfoVo.getLatitude()) + "&lon=" + Double.parseDouble(deviceLocationInfoVo.getLongitude());
                                ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
                                addr = forEntity.getBody();
                                deviceLocationInfoVo.setAddr(addr == null ? "" : addr);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            deviceLocationInfoVo.setDeviceName(deviceVo.getDeviceName());
            if (null != deviceVo.getRestTime()) {
                deviceLocationInfoVo.setRestTime(DateFormatUtils.format(deviceVo.getRestTime(), "yyyy-MM-dd HH:mm:ss"));
            }
            if (null != deviceVo.getLastOnLineTime()) {
                deviceLocationInfoVo.setLastOnLineTime(DateFormatUtils.format(deviceVo.getLastOnLineTime(), "yyyy-MM-dd HH:mm:ss"));
            }
            deviceLocationInfoVo.setDeviceLogo(deviceVo.getDeviceLogo());
            deviceLocationInfoVo.setId(deviceVo.getId());

            deviceLocationInfoVos.add(deviceLocationInfoVo);
        });
    }

    /**
     * 查询全部
     *
     * @param search
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DeviceMonitorVo queryAll(String search, Long userId) {
        List<DeviceLocationInfoVo> deviceLocationInfoVos = this.deviceLocation(search, userId);
        return getDeviceMonitorVo(deviceLocationInfoVos);
    }

    private DeviceMonitorVo getDeviceMonitorVo(List<DeviceLocationInfoVo> deviceLocationInfoVos) {
        String rest = "3";
        String oneLine = "2";
        String offLine = "1";
        Integer UnActivation = 1;
        Integer activation = 2;
        DeviceMonitorVo deviceMonitorVos = new DeviceMonitorVo();
        List<DeviceLocationInfoVo> deviceOnLine = new ArrayList<>();
        List<DeviceLocationInfoVo> deviceOffLine = new ArrayList<>();
        List<DeviceLocationInfoVo> deviceUnEnable = new ArrayList<>();
        deviceLocationInfoVos.forEach(deviceLocationInfoVo -> {
            // 在线
            if ((oneLine.equals(deviceLocationInfoVo.getStatus())
                    || rest.equals(deviceLocationInfoVo.getStatus()))
                    && (activation.equals(deviceLocationInfoVo.getActivation()))) {
                getRestDate(deviceLocationInfoVo);
                deviceOnLine.add(deviceLocationInfoVo);
            }
            // 未启用
            if (UnActivation.equals(deviceLocationInfoVo.getActivation())
                    || deviceLocationInfoVo.getActivation() == null) {
                deviceUnEnable.add(deviceLocationInfoVo);
            }
            // 离线
            if (offLine.equals(deviceLocationInfoVo.getStatus())
                    && (activation.equals(deviceLocationInfoVo.getActivation()))) {
                getRestDate(deviceLocationInfoVo);
                deviceOffLine.add(deviceLocationInfoVo);
            }
        });
        // 全部
        deviceMonitorVos.setDeviceAll(deviceLocationInfoVos);
        deviceMonitorVos.setDeviceOnLine(deviceOnLine);
        deviceMonitorVos.setDeviceOffLine(deviceOffLine);
        deviceMonitorVos.setUnEnable(deviceUnEnable);
        return deviceMonitorVos;
    }

    private void getRestDate(DeviceLocationInfoVo deviceLocationInfoVo) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date begin = null;
        Date end = null;
        String str = "";
        String strE = "";
        // 静止
        if (deviceLocationInfoVo.getStatus().equals("3")) {
            try {
                begin = dfs.parse(deviceLocationInfoVo.getRestTime());
                deviceLocationInfoVo.setTime(deviceLocationInfoVo.getRestTime());
            } catch (ParseException e) {
                throw new BadRequestException("时间转换异常,Abnormal time conversion");
            }
            // 离线
        } else if (deviceLocationInfoVo.getActivation() == 2 && deviceLocationInfoVo.getStatus().equals("1")) {
            try {
                begin = dfs.parse(deviceLocationInfoVo.getLastOnLineTime());
                deviceLocationInfoVo.setTime(deviceLocationInfoVo.getLastOnLineTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        try {
            end = dfs.parse(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 如果开始时间或者结束时间为空 直接返回
        if (end == null || begin == null) {
            return;
        }
        //  计算时间
        long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
        long day = between / (24 * 3600);
        long hour = between % (24 * 3600) / 3600;
        long minutes = (between - day * (60 * 60 * 24) - hour * (60 * 60)) / (60);
        long s = between % (24 * 60 * 60) % (60 * 60) % 60;
//        System.out.println(minutes);
        if (day > 0) {
            str += day + "天";
            strE += day + "day ";
        }
        if (hour > 0) {
            str += hour + "小时";
            strE += hour + "hours ";
        }
        if (minutes > 0) {
            str += minutes + "分钟";
            strE += minutes + "minutes ";
        }
        if (s > 0) {
            str += s + "秒";
            strE += s + "seconds";
        }

        //  离线时常赋值
        if (deviceLocationInfoVo.getStatus().equals("1")) {
            deviceLocationInfoVo.setLastOnLineTime(str + "," + strE);
        }
        // 静止
        if (deviceLocationInfoVo.getStatus().equals("3")) {
            deviceLocationInfoVo.setRestTime(str + "," + strE);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceRouteVo> deviceHistoricalRoute(Long deviceId, Long startTime, Long endTime) {
        // 时间间隔不能大于30天
        if ((endTime - startTime) / (1000 * 24 * 60 * 60) > 30) {
            throw new BadRequestException("时间间隔不能大于30天,The time interval cannot be greater than 30 days");
        }
        String url = "http://" + host + ":" + port;
        DeviceVo deviceVo = this.baseMapper.queryByDeviceId(deviceId);
        if (null == deviceVo) {
            throw new BadRequestException("设备数据不存在,Device data does not exist");
        }
        if (deviceVo.getActivation() != 2 &&
                deviceVo.getDelFlag() != 1 &&
                deviceVo.getEnabled() != 1) {
            throw new BadRequestException("设备数据不存在,Device data does not exist");
        }
        System.out.println("根据车辆编号，查询历史轨迹 url : " + url);
        log.info("根据车辆编号，查询历史轨迹 url : " + url);

        // 请请求发送到 ntsiot-data-engine FindTrajectoryController 查询指定设备的轨迹
        String str = null;
        try {
            str = restTemplate
                    .getForObject(url + "/findRecord?lockNo=" + deviceVo.getDeviceNo() + "&startTime=" + startTime + "&endTime=" + endTime, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("获取数据失败,Failed to get data");
        }
        List<DeviceRouteVo> deviceRouteVos = JsonUtil.jsonConvertList(str, DeviceRouteVo.class);
        if (!CollectionUtils.isEmpty(deviceRouteVos)) {
            deviceRouteVos.forEach(deviceRouteVo -> BeanUtils.copyProperties(deviceVo, deviceRouteVo));
        }
        return deviceRouteVos;
    }

    /**
     * 监控
     *
     * @param deviceId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeviceLocationInfoVo> monitor(Long deviceId) {
        Device device = this.getById(deviceId);
        List<DeviceLocationInfoVo> deviceLocationInfoVos = new ArrayList<>();
        if (null == device) {
            throw new BadRequestException("设备不存在,Device does not exist");
        }
        DeviceVo deviceVo = new DeviceVo();
        BeanUtils.copyProperties(device, deviceVo);
        this.getDeviceLocationInfo(deviceLocationInfoVos, Collections.singletonList(deviceVo));
        DeviceMonitorVo deviceMonitorVos = this.getDeviceMonitorVo(deviceLocationInfoVos);
        return deviceMonitorVos.getDeviceAll();

    }


    @Override
    public List<String> queryPageDeviceNo(String search, Long userId) {

        List<DeviceVo> deviceVoList = this.baseMapper.queryPage(null, userId, search, null,
                null, null, null);
        return deviceVoList.stream().map(DeviceVo::getDeviceNo).collect(Collectors.toList());
    }

    /**
     * 查询统计信息（查询的统计的时间范围，不能超过30天，超过则直接提示---查询不能超过30天）
     *
     * @param
     * @return
     */
    @Override
    public List<DeviceStatisticDto> findDeviceStatistic(Long userId, Long startTime, Long endTime, String deviceNo) {
        List<DeviceVo> deviceVos = this.baseMapper.queryPageExpires(null, userId, null, null, null, null, null);
        if (CollectionUtils.isEmpty(deviceVos)) {
            throw new BadRequestException("该用户下没有数据,No data under this user");
        }

        // 时间间隔不能大于30天
        if ((endTime - startTime) / (1000 * 24 * 60 * 60) > 30) {
            throw new BadRequestException("时间间隔不能大于30天,The time interval cannot be greater than 30 days");
        }
        DeviceStateQueryDTO deviceStateQueryDTO = new DeviceStateQueryDTO();
        deviceStateQueryDTO.setEndTime(endTime);
        deviceStateQueryDTO.setStartTime(startTime);
        if (StringUtils.isNotBlank(deviceNo)) {
            // 判断 deviceNo是否合法
            Device device = this.baseMapper.selectOne(new QueryWrapper<Device>()
                    .eq("device_no", deviceNo)
                    .eq("del_flag", 1)
                    .eq("enabled", 1)
                    .eq("activation", 2));
            if (null == device) {
                throw new BadRequestException("该用户下没有数据,No data under this user");
            }
//            deviceStateQueryDTO.setDeviceNo(Arrays.asList(deviceNo));
            deviceStateQueryDTO.setDeviceNo(Collections.singletonList(deviceNo));
        } else {
            deviceStateQueryDTO.setDeviceNo(deviceVos.stream().map(DeviceVo::getDeviceNo).collect(Collectors.toList()));
        }
        String url = "http://" + host + ":" + port;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<Object> entity = new HttpEntity<>(JsonUtil.getJson(deviceStateQueryDTO), headers);

        ResponseEntity<String> exchange = null;
        try {
            exchange = restTemplate.exchange(url + "/findDeviceStatistic", HttpMethod.POST, entity, String.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("获取数据失败,Failed to get data");
        }
        String body = exchange.getBody();
        return JsonUtil.jsonConvertList(body, DeviceStatisticDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceVo> queryAllByUserId(Long userId, User user, String deviceNo) {
        return this.baseMapper.queryPage(null, userId, deviceNo, null, null, null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceVo> queryListByFenceId(Long fenceId) {
        return this.baseMapper.queryListByFenceId(fenceId);
    }

    @Override
    public Page<DeviceVo> queryPageExpires(String search, String userExpiresTimeStart, String userExpiresTimeEnd, String platformExpiresTimeStart, String platformExpiresTimeEnd, Pageable pageable, Long userId) {
        Page<DeviceVo> page = new Page(pageable.getPageNumber(), pageable.getPageSize());
        List<DeviceVo> deviceVoList = this.baseMapper.queryPageExpires(page, userId, search, userExpiresTimeStart,
                userExpiresTimeEnd, platformExpiresTimeStart, platformExpiresTimeEnd);
        page.setRecords(deviceVoList);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceRouteVo> deviceStatisticRoute(Long userId, String deviceNo, Long startTime, Long endTime) {
        List<DeviceVo> deviceVos = this.baseMapper.queryPageExpires(null, userId, null, null, null, null, null);
        if (CollectionUtils.isEmpty(deviceVos)) {
            throw new BadRequestException("该用户下没有数据,No data under this user");
        }

        // 时间间隔不能大于30天
        if ((endTime - startTime) / (1000 * 24 * 60 * 60) > 30) {
            throw new BadRequestException("时间间隔不能大于30天,The time interval cannot be greater than 30 days");
        }

        DeviceStateQueryDTO deviceStateQueryDTO = new DeviceStateQueryDTO();
        deviceStateQueryDTO.setEndTime(endTime);
        deviceStateQueryDTO.setStartTime(startTime);
        if (StringUtils.isNotBlank(deviceNo)) {
            // 判断 deviceNo是否合法
            Device device = this.baseMapper.selectOne(new QueryWrapper<Device>()
                    .eq("device_no", deviceNo)
                    .eq("del_flag", 1)
                    .eq("enabled", 1)
                    .eq("activation", 2));
            if (null == device) {
                throw new BadRequestException("该用户下没有数据,No data under this user");
            }
//            deviceStateQueryDTO.setDeviceNo(Arrays.asList(deviceNo));
            deviceStateQueryDTO.setDeviceNo(Collections.singletonList(deviceNo));
        } else {
            deviceStateQueryDTO.setDeviceNo(deviceVos.stream().map(DeviceVo::getDeviceNo).collect(Collectors.toList()));
        }
        String url = "http://" + host + ":" + port;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<Object> entity = new HttpEntity<>(JsonUtil.getJson(deviceStateQueryDTO), headers);

        ResponseEntity<String> exchange = null;
        try {
            exchange = restTemplate.exchange(url + "/findRecord/list", HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            throw new BadRequestException("获取数据失败,Failed to get data");
        }
        String body = exchange.getBody();
        List<DeviceRouteVo> deviceRouteVos = JsonUtil.jsonConvertList(body, DeviceRouteVo.class);
        if (!CollectionUtils.isEmpty(deviceRouteVos)) {
            deviceVos.forEach(deviceVo -> {
                deviceRouteVos.forEach(deviceRouteVo -> {
                    if (StringUtils.equals(deviceVo.getDeviceNo(), deviceRouteVo.getDeviceNo())) {
                        BeanUtils.copyProperties(deviceVo, deviceRouteVo);
                    }
                });

            });
        }
        return deviceRouteVos;
    }

    @Override
    public List<OverSpeedAlarmVo> overSpeedAlarm(Long userId, Long startTime, Long endTime, String deviceNo) {
        List<DeviceVo> deviceVos = this.baseMapper.queryPageExpires(null, userId, null, null, null, null, null);
        if (CollectionUtils.isEmpty(deviceVos)) {
            throw new BadRequestException("该用户下没有数据,No data under this user");
        }

        // 时间间隔不能大于30天
        if ((endTime - startTime) / (1000 * 24 * 60 * 60) > 30) {
            throw new BadRequestException("时间间隔不能大于30天,The time interval cannot be greater than 30 days");
        }
        // 根据用户判断 deviceNo是否是用户的
        List<String> list = deviceVos.stream().map(DeviceVo::getDeviceNo).collect(Collectors.toList());

        if (!list.contains(deviceNo)) {
            throw new BadRequestException("该用户下没有该设备,The user does not have the device");
        }

        String url = "http://" + host + ":" + port;
        ResponseEntity<String> voResponseEntity = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("获取数据失败,Failed to get data");
        }
        voResponseEntity = restTemplate
                .getForEntity(url + "/overSpeedAlarm?startTime=" + startTime + "&endTime=" + endTime + "&deviceNo=" + deviceNo, String.class);
        return JsonUtil.jsonConvertList(voResponseEntity.getBody(), OverSpeedAlarmVo.class);
    }

    /**
     * 判断设备是否已经存在
     *
     * @param deviceDownVo
     * @return
     */
    private boolean validDevice(DeviceDownVo deviceDownVo) {
        // 设备号/设备ID 不能为空
        if (StringUtils.isBlank(deviceDownVo.getDeviceNo())) {
            throw new BadRequestException("设备号/设备ID不能为空,Device number/device ID cannot be empty");
        }
        if (StringUtils.isBlank(deviceDownVo.getDeviceType())) {
            throw new BadRequestException("设备类型不能为空,Device type cannot be empty");
        }
        // 设备名称 不能为空
        if (StringUtils.isBlank(deviceDownVo.getDeviceName())) {
            throw new BadRequestException("设备名称不能为空设备号/设备ID:" + deviceDownVo.getDeviceNo() + ",Device name cannot be empty device number/device ID" + deviceDownVo.getDeviceNo());
        } else {
            Integer count = this.baseMapper.selectCount(new QueryWrapper<Device>().eq("device_name", deviceDownVo.getDeviceName()).eq("del_flag", 1));
            if (count > 0) {
                // 名称重复
                throw new BadRequestException("名称重复:" + deviceDownVo.getDeviceName() + ",Duplicate name:" + deviceDownVo.getDeviceName());
            }
        }
        // 设备SIM不能为空
        if (StringUtils.isBlank(deviceDownVo.getCardSim())) {
            throw new BadRequestException("设备SIM不能为空设备名称:" + deviceDownVo.getDeviceName() + ",Device SIM cannot be empty device name:" + deviceDownVo.getDeviceName());
        } else {
            Integer count = this.baseMapper.selectCount(new QueryWrapper<Device>().eq("card_sim", deviceDownVo.getCardSim()).eq("del_flag", 1));
            if (count > 0) {
                // 设备SIM重复
                throw new BadRequestException("设备SIM重复:" + deviceDownVo.getCardSim() + ",Device SIM duplicate:" + deviceDownVo.getCardSim());
            }
        }
        // 车牌号不能为空
        if (StringUtils.isBlank(deviceDownVo.getCarNo())) {
            throw new BadRequestException("车牌号不能为空,设备名称:" + deviceDownVo.getDeviceName() + ",License plate number cannot be empty, device name:" + deviceDownVo.getDeviceName());
        } else {
            Integer count = this.baseMapper.selectCount(new QueryWrapper<Device>().eq("car_no", deviceDownVo.getCarNo()).eq("del_flag", 1));
            if (count > 0) {
                // 设备SIM重复
                throw new BadRequestException("车牌号重复:" + deviceDownVo.getCarNo() + ",CarNo duplicate:" + deviceDownVo.getCarNo());
            }
        }
        // 车主姓名不能为空
        if (StringUtils.isBlank(deviceDownVo.getContactPerson())) {
            throw new BadRequestException("车主姓名不能为空设备名称:" + deviceDownVo.getDeviceName() + ",Owner name cannot be empty device name:" + deviceDownVo.getDeviceName());
        } else {
            Integer count = this.baseMapper.selectCount(new QueryWrapper<Device>().eq("contact_person", deviceDownVo.getContactPerson()).eq("del_flag", 1));
            if (count > 0) {
                // 车主姓名重复
                throw new BadRequestException("车主姓名重复:" + deviceDownVo.getContactPerson() + ",Owner has duplicate name:" + deviceDownVo.getContactPerson());

            }
        }
        // 车主电话不能为空
        if (StringUtils.isBlank(deviceDownVo.getPhone())) {
            throw new BadRequestException("车主电话不能为空设备名称:" + deviceDownVo.getDeviceName() + ",Owner's phone cannot be empty device name:" + deviceDownVo.getDeviceName());
        } else {
            Integer count = this.baseMapper.selectCount(new QueryWrapper<Device>().eq("phone", deviceDownVo.getPhone()).eq("del_flag", 1));
            if (count > 0) {
                // 车主电话重复
                throw new BadRequestException("车主电话重复:" + deviceDownVo.getPhone() + ",Owner's phone repeated:" + deviceDownVo.getPhone());
            }
        }

        if (deviceDownVo.getUserExpiresTime() == null) {
            throw new BadRequestException("用户到期时间不能为空，设备名称:" + deviceDownVo.getDeviceName() + ",User expiration time cannot be empty, device name:" + deviceDownVo.getDeviceName());
        }

        if (deviceDownVo.getPlatformExpiresTime() == null) {
            throw new BadRequestException("平台到期时间不能为空，设备名称:" + deviceDownVo.getDeviceName() + ",Platform expiration time cannot be empty, device name:" + deviceDownVo.getDeviceName());
        }
        return true;
    }


    /**
     * 延期
     *
     * @param deviceRqList
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void extension(List<DeviceRq> deviceRqList, User user) {
        deviceRqList.forEach(deviceRq -> {
            if (deviceRq.getPlatformExpiresTime() == null) {
                throw new BadRequestException("延期失败平台到期不能为空,Failure to extend the platform can not be empty");
            }
            Device device = new Device();
            device.setId(deviceRq.getId());
            // 修改平台到期时间
            device.setPlatformExpiresTime(deviceRq.getPlatformExpiresTime());
            // 修改用户到期时间
            device.setUserExpiresTime(deviceRq.getUserExpiresTime());
            device.setUpdateTime(new Date());
            device.setUpdateBy(user.getUsername());
            // 修改设备信息
            int i = this.baseMapper.updateById(device);
            if (i < 0) {
                throw new BadRequestException("Postponement failed");
            }
        });
    }
}
