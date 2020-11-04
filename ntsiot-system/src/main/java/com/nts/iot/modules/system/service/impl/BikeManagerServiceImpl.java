package com.nts.iot.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.util.GpsUtil;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.miniApp.dto.RidingTrack;
import com.nts.iot.modules.miniApp.dto.StopPoint;
import com.nts.iot.modules.miniApp.dto.TrajectoryDto;
import com.nts.iot.modules.miniApp.model.HistoryApiDto;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.system.model.*;
import com.nts.iot.modules.system.service.*;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dao.BikeMapper;

import com.nts.iot.util.EnclosureUtil;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.geom.Point2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nts.iot.constant.ConstantClass.*;
import static com.nts.iot.constant.RedisKey.*;
import static com.nts.iot.constant.RedisKey.VIRTUAL_PILE;
import static java.util.logging.Logger.getLogger;

/**
 * @Author zhc@rnstec.com
 * @Description
 * @Date 2019-05-05 10:40
 * @Version: 1.0
 */
@Service
public class BikeManagerServiceImpl extends ServiceImpl<BikeMapper, Bike> implements BikeManagerService {

    private static final Logger log = getLogger(BikeManagerServiceImpl.class.getName());

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MaUserService userService;

    @Autowired
    private BikeLicenseService bikeLicenseService;

    @Autowired
    private JurisdictionConfigurationService deptConfigService;

    @Autowired
    private OrderManagerService orderManagerService;

    @Autowired
    private BikeManagerService bikeManagerService;

    @Autowired
    private LockService lockService;

    @Value("${engineServerUrl}")
    private String E_URL;


    @Override
    public Map queryAll(Pageable pageable, Bike bike, List<String> jurisdictions) {
        Page<Bike> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<Bike> pageResult = baseMapper.selectByPage(page, bike, jurisdictions);
        if (pageResult != null && pageResult.getRecords() != null && pageResult.getRecords().size() > 0) {
            for (int i = 0; i < pageResult.getRecords().size(); i++) {
                String lb = pageResult.getRecords().get(i).getLockBarcode();
                redisUtil.getData(RedisKey.VECHILE_STATE + lb);
                CollectMessage collectMessage = JsonUtil.jsonConvertObject(redisUtil.getData(RedisKey.VECHILE_STATE + lb), CollectMessage.class);
                if (collectMessage != null) {
                    pageResult.getRecords().get(i).setDeviceEndTime(collectMessage.getTime());
                }
            }
        }
        return PageUtil.toPage(pageResult);
    }

    @Override
    public List<Bike> queryAllApp(List<String> jurisdictions) {
        return baseMapper.selectByPageApp(jurisdictions);
    }

    @Override
    public Long getBikeStatus(String bikeBarcode, String maOpenId) {
        QueryWrapper<Bike> query = new QueryWrapper<>();
        query.eq("bike_barcode", bikeBarcode);
        return this.getOne(query, false).getStatus();
    }

    @Override
    public void initBikes() {
//        Set<String> keys = redisUtil.keys(RedisKey.BIKE_KEY);
//        Iterator<String> it = keys.iterator();
//        while (it.hasNext()) {
//            String str = it.next();
//            Bike bike=JSON.parseObject(redisUtil.getData(str),Bike.class);
//            redisUtil.deleteByKey(str);
//            /* 根据车锁删除bike */
//            redisUtil.deleteByKey(RedisKey.LOCK_KEY + bike.getLockBarcode());
//        }
        List<Bike> bikes = baseMapper.selectList(null);
        /* 过滤车辆编号为Null 的脏数据 */
        bikes.stream().filter(p -> p.getBikeBarcode() != null).forEach(bike -> {
            // 车辆编号查询车
            String key1 = RedisKey.VECHILE_KEY + bike.getBikeBarcode();
            redisUtil.addRedis(key1, JSON.toJSONString(bike));
            // 车锁编号查询车
            if (bike.getLockBarcode() != null) {
                String key2 = RedisKey.TRACKER_KEY + bike.getLockBarcode();
                redisUtil.addRedis(key2, JSON.toJSONString(bike));
            }
        });
    }


    @Override
    public RidingTrack historicalTrack(String bikeBarcode, Long startTime, Long endTime) {

        Map<String, Object> queryMap = new HashMap<>();

        Bike bike = bikeManagerService.getBikeByBarcode(bikeBarcode);
        if (bike == null) {
            throw new BadRequestException("车辆不存在！");
        }
        queryMap.put("lockNo", bike.getLockBarcode());
        queryMap.put("startTime", startTime);
        queryMap.put("endTime", endTime);
        /* 根据订单查询历史轨迹 */
//        String url = E_URL + "/bikes/historicalRoute?bikeBarcode={bikeBarcode}&startTime={startTime}&endTime={endTime}";
        String url = E_URL + "/findRecord?lockNo={lockNo}&startTime={startTime}&endTime={endTime}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, queryMap);
//        List<OrderDto> orderDtos = JsonUtil.jsonConvertList(responseEntity.getBody(), OrderDto.class);

        List<TrajectoryDto> trajectoryDtos = JsonUtil.jsonConvertList(responseEntity.getBody(), TrajectoryDto.class);

        List<Object> paths = new ArrayList<>();
        List<Map<String, Object>> trajectoryList = new ArrayList<>();
        List<String> gpsTimes = new ArrayList<>();
        List<Object> points = new ArrayList<>();
        List<Object> speeds = new ArrayList<>();
        List<StopPoint> stopPoints = new ArrayList<>();

        Long stopTime = 0L;
        String startStopTime;
        StopPoint stopPoint = new StopPoint();
        //轨迹、GPS时间
        //计数器，判断是否已经到最后一个点
        int count = 0;
        double distance = 0.0;
        for (int j = 0; j < trajectoryDtos.size(); j++) {
            count = j;
            TrajectoryDto from_tj = trajectoryDtos.get(j);
            TrajectoryDto to_tj = null;
            if (count == trajectoryDtos.size() - 1) {
                to_tj = trajectoryDtos.get(j);
            } else {
                to_tj = trajectoryDtos.get(j + 1);
            }
            double[] point = new double[2];
            //坐标
            point[0] = Double.parseDouble(from_tj.getLongitude());
            point[1] = Double.parseDouble(from_tj.getLatitude());
            paths.add(point);
            Map<String, Object> trajectory = new HashMap<>();
            trajectory.put("latitude", point[1]);
            trajectory.put("longitude", point[0]);
            trajectory.put("speed", Double.parseDouble(from_tj.getSpeed()));
            trajectory.put("time", from_tj.getTime());
            trajectory.put("course", from_tj.getCourse());
            trajectoryList.add(trajectory);

            //计算距离
            com.nts.iot.dto.CollectMessage from = new com.nts.iot.dto.CollectMessage();
            com.nts.iot.dto.CollectMessage to = new com.nts.iot.dto.CollectMessage();
            from.setLatitude(from_tj.getLatitude());
            from.setLongitude(from_tj.getLongitude());
            to.setLatitude(to_tj.getLatitude());
            to.setLongitude(to_tj.getLongitude());
            distance = distance + GpsUtil.getDistance(from, to);
            //速度
            speeds.add(Double.parseDouble(from_tj.getSpeed()));
            gpsTimes.add(from_tj.getTime());
            // 计算停留点
            try {
                // 坐标相同，累计停留时间
                if (from_tj.getLatitude().equals(to_tj.getLatitude()) && from_tj.getLongitude().equals(to_tj.getLongitude())) {
                    stopTime += this.getDiffTime(from_tj, to_tj);
                } else {
                    // 坐标不同，计算停留时间
                    // 暂定停留超过5分钟，为一个停留点 TODO
                    if (stopTime >= 30000L) {
                        startStopTime = from_tj.getTime();
                        String time = "停止时间：" + startStopTime + "</br>停留时间：" + this.longTimeToDay(stopTime);
                        stopPoint.setStopTime(time);
                        stopPoint.setTimeLength("停留时间：" + this.longTimeToDay(stopTime));
                        stopPoint.setStopMs(stopTime);
                        stopPoint.setLongitude(from_tj.getLongitude());
                        stopPoint.setLatitude(from_tj.getLatitude());
                        stopPoints.add(stopPoint);
                    }
                    stopTime = 0L;
                    stopPoint = new StopPoint();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        RidingTrack ridingTrack = new RidingTrack();
        ridingTrack.setTrajectoryList(trajectoryList);
        // 骑行路径
        ridingTrack.setPath(paths);
        // gps时间
        ridingTrack.setGpstime(gpsTimes);
        // 必到点
        ridingTrack.setPoints(points);
        // 速度
        ridingTrack.setSpeeds(speeds);
        // 停留点
        ridingTrack.setStopPoints(stopPoints);
        //距离
        ridingTrack.setDistance(distance);
        return ridingTrack;
    }

    private Long getDiffTime(TrajectoryDto from, TrajectoryDto to) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 坐标相同，累计停留时间
        // gps时间
        String startTime = from.getTime();
        String endTime = to.getTime();
        // 转换成date类型
        Date start = sdf.parse(startTime);
        Date end = sdf.parse(endTime);
        // 获取毫秒数
        Long startLong = start.getTime();
        Long endLong = end.getTime();
        //计算时间差,单位毫秒
        return endLong - startLong;
    }

    /**
     * 转换函数
     *
     * @param ms 毫秒
     * @return
     */
    private String longTimeToDay(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
//        Integer dd = hh * 24;
//        Long day = ms / dd;
//        Long hour = (ms - day * dd) / hh;
//        Long minute = (ms - day * dd - hour * hh) / mi;
//        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
//        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        Long hour = (ms) / hh;
        Long minute = (ms - hour * hh) / mi;
        Long second = (ms - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - hour * hh - minute * mi - second * ss;


        StringBuilder sb = new StringBuilder();
//        if(day > 0) {
//            sb.append(day).append("天");
//        }
        if (hour > 0) {
            if (hour > 10) {
                sb.append("<b>").append(hour).append("</b>").append("小时");
            } else {
                sb.append("<b>").append("0").append(hour).append("</b>").append("</b>").append("小时");
            }
        } else {
            sb.append("00小时");
        }
        if (minute > 0) {
            if (minute > 10) {
                sb.append("<b>").append(minute).append("分");
            } else {
                sb.append("<b>").append("0").append(minute).append("</b>").append("分");
            }
        } else {
            sb.append("00分");
        }
        if (second > 0) {
            if (second > 10) {
                sb.append("<b>").append(second).append("秒");
            } else {
                sb.append("<b>").append("0").append(second).append("</b>").append("秒");
            }
        } else {
            sb.append("00秒");
        }
        if (milliSecond > 0) {
            if (milliSecond > 10) {
                sb.append("<b>").append(milliSecond).append("毫秒");
            } else {
                sb.append("<b>").append("0").append(milliSecond).append("</b>").append("毫秒");
            }
        }
        return sb.toString();
    }

    /**
     * 根据bikeBarcode查询 Bike
     *
     * @param bikeBarCode
     * @return
     */
    @Override
    public Bike getBikeByBarcode(String bikeBarCode) {
        // 根据bikeBarcode查询 Bike
        QueryWrapper<Bike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bike_barcode", bikeBarCode);
        List<Bike> bikes = this.list(queryWrapper);
        if (bikes == null || bikes.size() != 1) {
            return null;
        } else {
            return bikes.get(0);
        }
    }

    @Override
    public Bike getBikeByDeviceNo(String deviceNo) {
        // 根据bikeBarcode查询 Bike
        QueryWrapper<Bike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lock_barcode", deviceNo);
        List<Bike> bikes = this.list(queryWrapper);
        if (bikes == null || bikes.size() != 1) {
            return null;
        } else {
            return bikes.get(0);
        }
    }

    /**
     * 根据车编号判断是否可以关锁
     *
     * @param lockBarCode
     * @return
     */
    @Override
    public boolean checkCloseLock(String lockBarCode) {
        // 根据车锁获得到这个车所在的辖区
        Bike bike = JsonUtil.jsonConvertObject(redisUtil.getData(TRACKER_KEY + lockBarCode), Bike.class);
        // 车辆的当前坐标点
        CollectMessage collectMessage = JsonUtil.jsonConvertObject(redisUtil.getData(VECHILE_STATE + lockBarCode), CollectMessage.class);
        if (collectMessage == null) {
            return true;
        }
        // RNS ※  当位置信息是 0 、 NULL 、 空的时候，则直接通过 ※
        String longitude = collectMessage.getLongitude();
        String latitude = collectMessage.getLatitude();
        if (longitude == null || "".equals(longitude) || "0".equals(longitude)) {
            return true;
        }
        if (latitude == null || "".equals(latitude) || "0".equals(latitude)) {
            return true;
        }
        // 获得辖区编号
        Long jurisdiction = bike.getJurisdiction();
        // 根据辖区编号获得这个辖区的配置情况：是否启动了虚拟装停车的校验、是否启动了限行围栏停车的校验
        JurisdictionConfiguration config = deptConfigService.getJurisdictionConfiguration(jurisdiction);
        if (config != null) {
            if ((config.getIsParkingRestrictions() == null || "0".equals(config.getIsParkingRestrictions())) && (config.getIsVirtual() == null || "0".equals(config.getIsVirtual()))) {
                return true;
            } else {
                // 限行围栏判断
                boolean flagParkingRestrictions = false;
                boolean flagVirtual = false;
                if (config.getIsParkingRestrictions() != null && "1".equals(config.getIsParkingRestrictions())) {
                    // 获得限行围栏缓存
                    String fencesJson = redisUtil.getData(RESTRICTIONS_FENCE + jurisdiction);
                    List<String> fences = JsonUtil.jsonConvertList(fencesJson, String.class);
                    log.info("checkCloseLock fencesJson = " + fencesJson);

                    if (fences != null && fences.size() > 0) {
                        for (String fence : fences) {
                            String[] fenceArr = fence.split(";");
                            Point2D.Double pd = new Point2D.Double();
                            pd.setLocation(Double.parseDouble(collectMessage.getLongitude()), Double.parseDouble(collectMessage.getLatitude()));
                            List<Point2D.Double> list = EnclosureUtil.getPDList(fenceArr);
                            // 如果在限行围栏中，那么就说明找到了
                            if (EnclosureUtil.isInPolygon(pd, list)) {
                                log.info("checkCloseLock EnclosureUtil.isInPolygon返回true");
                                flagParkingRestrictions = true;
                                break;
                                //return true;
                            } else {
                                log.info("checkCloseLock EnclosureUtil.isInPolygon返回false,pd.getX=" + pd.getX() + ",pd.getY=" + pd.getY());
                            }
                        }
                    }
                } else {
                    flagParkingRestrictions = true;
                    log.info("checkCloseLock 限行围栏为空或者为0，IsParkingRestrictions = " + config.getIsParkingRestrictions());
                }
                // 虚拟装判断
                if (config.getIsVirtual() != null && "1".equals(config.getIsVirtual())) {
                    // 获得限行围栏缓存
                    String virtualJson = redisUtil.getData(VIRTUAL_PILE + jurisdiction);
                    List<VirtualPile> virtualPiles = JsonUtil.jsonConvertList(virtualJson, VirtualPile.class);
                    log.info("checkCloseLock virtualJson = " + virtualJson);
                    if (virtualPiles != null && virtualPiles.size() > 0) {
                        for (VirtualPile virtualPile : virtualPiles) {
                            // 虚拟点坐标
                            String point = virtualPile.getLongitude() + "," + virtualPile.getLatitude();
                            // 车辆实时点坐标
                            String bikePoint = collectMessage.getLongitude() + "," + collectMessage.getLatitude();
                            if (EnclosureUtil.checkPoint(String.valueOf(virtualPile.getScope()), point, bikePoint)) {
                                log.info("checkCloseLock EnclosureUtil.checkPoint返回true");
                                flagVirtual = true;
                                break;
                                //return true;
                            } else {
                                log.info("checkCloseLock EnclosureUtil.checkPoint返回false, virtualPile.getScope=" + virtualPile.getScope() + ",point=" + point + ",bikePoint=" + bikePoint);
                            }
                        }
                    }
                } else {
                    flagVirtual = true;
                    log.info("checkCloseLock 虚拟桩配置为空或者为0，IsVirtual = " + config.getIsVirtual());
                }
                return flagParkingRestrictions && flagVirtual;
                //return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 根据车编号判断是否可以开锁
     *
     * @param lockBarCode
     * @return
     */
    @Override
    public boolean checkOpenLock(String lockBarCode) {
        // 根据车锁获得到这个车所在的辖区
        Bike bike = JsonUtil.jsonConvertObject(redisUtil.getData(TRACKER_KEY + lockBarCode), Bike.class);
        // 车辆的当前坐标点
        CollectMessage collectMessage = JsonUtil.jsonConvertObject(redisUtil.getData(VECHILE_STATE + lockBarCode), CollectMessage.class);
        if (collectMessage == null) {
            return true;
        }
        // RNS ※  当位置信息是 0 、 NULL 、 空的时候，则直接通过 ※
        String longitude = collectMessage.getLongitude();
        String latitude = collectMessage.getLatitude();
        if (longitude == null || "".equals(longitude) || "0".equals(longitude)) {
            return true;
        }
        if (latitude == null || "".equals(latitude) || "0".equals(latitude)) {
            return true;
        }
        // 获得辖区编号
        Long jurisdiction = bike.getJurisdiction();
        // 根据辖区编号获得这个辖区的配置情况：是否启动了虚拟装停车的校验、是否启动了限行围栏停车的校验
        JurisdictionConfiguration config = deptConfigService.getJurisdictionConfiguration(jurisdiction);
        if (config != null) {
            if ((config.getIsParkingRestrictions() == null || "0".equals(config.getIsParkingRestrictions()))) {
                return true;
            } else {
                // 限行围栏判断
                boolean flagParkingRestrictions = false;
                if (config.getIsParkingRestrictions() != null && "1".equals(config.getIsParkingRestrictions())) {
                    // 获得限行围栏缓存
                    String fencesJson = redisUtil.getData(RESTRICTIONS_FENCE + jurisdiction);
                    List<String> fences = JsonUtil.jsonConvertList(fencesJson, String.class);
                    log.info("checkCloseLock fencesJson = " + fencesJson);

                    if (fences != null && fences.size() > 0) {
                        for (String fence : fences) {
                            String[] fenceArr = fence.split(";");
                            Point2D.Double pd = new Point2D.Double();
                            pd.setLocation(Double.parseDouble(collectMessage.getLongitude()), Double.parseDouble(collectMessage.getLatitude()));
                            List<Point2D.Double> list = EnclosureUtil.getPDList(fenceArr);
                            // 如果在限行围栏中，那么就说明找到了
                            if (EnclosureUtil.isInPolygon(pd, list)) {
                                log.info("checkCloseLock EnclosureUtil.isInPolygon返回true");
                                flagParkingRestrictions = true;
                                break;
                                //return true;
                            } else {
                                log.info("checkCloseLock EnclosureUtil.isInPolygon返回false,pd.getX=" + pd.getX() + ",pd.getY=" + pd.getY());
                            }
                        }
                    }
                } else {
                    flagParkingRestrictions = true;
                    log.info("checkCloseLock 限行围栏为空或者为0，IsParkingRestrictions = " + config.getIsParkingRestrictions());
                }
                return flagParkingRestrictions;
                //return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public Map<String, Object> getBikeStatus(Bike bike, String openId) {
        String bikeBarCode = bike.getBikeBarcode();
        String lockBarCode = bike.getLockBarcode();
        Map<String, Object> result = new HashMap<>();
        result.put("lockBarCode", lockBarCode);
        result.put("bikeBarCode", bikeBarCode);
        /* 车辆订单判断(进行中的车辆不能被开锁) */
        List<Order> orders = orderManagerService.getRunningOrdersByBarcode(lockBarCode);
        boolean checkFlag = false;
        // 不存在订单
        if (orders == null || orders.size() == 0) {
            /* 授权判断 */
            // 辖区是否需要进行授权
            Long deptId = bike.getJurisdiction();
            // 更新系统后，用最新的辖区配置表进行校验，之前的不在使用。
            JurisdictionConfiguration jurisdictionConfiguration = deptConfigService.getJurisdictionConfiguration(deptId);
            if (jurisdictionConfiguration == null || "0".equals(jurisdictionConfiguration.getIsVehicleLicense())) {
                checkFlag = true;
            } else {
                MaUser user = userService.getUserByWxId(openId);
                ;
                if (user == null) {
                    result.put("code", 400);
                    result.put("message", "该用户不存在");
                } else {
                    List<BikeLicense> bikeLicenses = bikeLicenseService.findAll(user.getPhone(), String.valueOf(deptId));
                    // 判断车辆的授权状态
                    if (bikeLicenses != null && bikeLicenses.size() > 0) {
                        for (BikeLicense bikeLicense : bikeLicenses) {
                            // 授权类型 1：永久；0：临时
                            if (bikeLicense.getType() == 1L) {
                                checkFlag = true;
                                // 如果有一个符合逻辑，跳出循环
                                break;
                            } else if (bikeLicense.getType() == 0L) {
                                Long endTime = bikeLicense.getEndTime();
                                Long startTime = bikeLicense.getStartTime();
                                // 判断超时时间，如果设置临时授权且超时时间还未空，则视为授权过期
                                if (endTime == null || (System.currentTimeMillis() - endTime) > 0) {
                                    checkFlag = false;
                                    result.put("code", 400);
                                    result.put("message", "授权已过期");
                                } else if (startTime - System.currentTimeMillis() > 0) {
                                    checkFlag = false;
                                    result.put("code", 400);
                                    result.put("message", "未授权");
                                } else {
                                    checkFlag = true;
                                    // 如果有一个符合逻辑，跳出循环
                                    break;
                                }
                            } else {
                                result.put("code", 400);
                                result.put("message", "授权类型错误");
                            }
                        }
                    } else {
                        result.put("code", 400);
                        result.put("message", "未授权");
                    }
                }
            }
        } else {
            result.put("code", 400);
            result.put("message", " 存在进行中订单。");
        }
        if (checkFlag) {
            // 查询车辆状态
            Long status = this.getBikeStatus(bikeBarCode, openId);
            result.put("power", getPowerByLockBarcode(lockBarCode));
            result.put("macAddress", getMacAddressByLock(lockBarCode));
            if (BIKE_STATUS_00.equals(status)) {
                result.put("code", 200);
                result.put("message", "车辆正常");
            } else if (BIKE_STATUS_01.equals(status)) {
                result.put("code", 201);
                result.put("message", "故障车");
            } else if (BIKE_STATUS_02.equals(status)) {
                result.put("code", 202);
                result.put("message", "电量低");
            } else {
                result.put("code", 209);
                result.put("message", "车辆状态未知");
            }
        }
        return result;
    }




    @Override
    public HistoryApiDto historicalTrackApi(String bikeBarcode, Long startTime, Long endTime) {

        Map<String, Object> queryMap = new HashMap<>();

        Bike bike = bikeManagerService.getBikeByBarcode(bikeBarcode);
        if (bike == null) {
            throw new BadRequestException("车辆不存在！");
        }
        queryMap.put("lockNo", bike.getLockBarcode());
        queryMap.put("startTime", startTime);
        queryMap.put("endTime", endTime);
        /* 根据订单查询历史轨迹 */
//        String url = E_URL + "/bikes/historicalRoute?bikeBarcode={bikeBarcode}&startTime={startTime}&endTime={endTime}";
        String url = E_URL + "/findRecord?lockNo={lockNo}&startTime={startTime}&endTime={endTime}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, queryMap);
//        List<OrderDto> orderDtos = JsonUtil.jsonConvertList(responseEntity.getBody(), OrderDto.class);

        List<TrajectoryDto> trajectoryDtos = JsonUtil.jsonConvertList(responseEntity.getBody(), TrajectoryDto.class);

        List<Object> paths = new ArrayList<>();

        Long stopTime = 0L;
        //计算总停留时间
        Long stopTimes=0L;
        String startStopTime;
        StopPoint stopPoint = new StopPoint();
        //轨迹、GPS时间
        //计数器，判断是否已经到最后一个点
        int count = 0;
        double distance = 0.0;
        for (int j = 0; j < trajectoryDtos.size(); j++) {
            count = j;
            TrajectoryDto from_tj = trajectoryDtos.get(j);
            TrajectoryDto to_tj = null;
            if (count == trajectoryDtos.size() - 1) {
                to_tj = trajectoryDtos.get(j);
            } else {
                to_tj = trajectoryDtos.get(j + 1);
            }

            double[] point = new double[2];
            //坐标
            point[0] = Double.parseDouble(from_tj.getLongitude());
            point[1] = Double.parseDouble(from_tj.getLatitude());
            paths.add(point);

            //计算距离
            com.nts.iot.dto.CollectMessage from = new  com.nts.iot.dto.CollectMessage ();
            com.nts.iot.dto.CollectMessage to = new com.nts.iot.dto.CollectMessage();
            from.setLatitude(from_tj.getLatitude());
            from.setLongitude(from_tj.getLongitude());
            to.setLatitude(to_tj.getLatitude());
            to.setLongitude(to_tj.getLongitude());
            distance = distance + GpsUtil.getDistance(from, to);

            // 计算停留点
            try {
                // 坐标相同，累计停留时间
                if (from_tj.getLatitude().equals(to_tj.getLatitude()) && from_tj.getLongitude().equals(to_tj.getLongitude())) {
                    stopTime += this.getDiffTime(from_tj, to_tj);
                } else {
                    // 坐标不同，计算停留时间
                    // 暂定停留超过5分钟，为一个停留点 TODO
                    if (stopTime >= 30000L) {
                        startStopTime = from_tj.getTime();
                        stopTimes=stopTimes+stopTime;
                    }
                    stopTime = 0L;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        HistoryApiDto historyApiDto=new HistoryApiDto();
        historyApiDto.setPath(paths);
        //距离
        historyApiDto.setDistance(distance);
        historyApiDto.setStopTime(stopTimes);
        return historyApiDto;
    }

    /**
     * 通过锁的条码号获取电量
     *
     * @param lockBarcode
     * @return
     */
    private String getPowerByLockBarcode(String lockBarcode) {
        // 当前状态
        String currentState = redisUtil.getData(VECHILE_STATE + lockBarcode);
        CollectMessage collectMessage = JsonUtil.jsonConvertObject(currentState, CollectMessage.class);
        // 续航里程计算，用 电量的一半 除以 2
        if (collectMessage == null) {
            return "0";
        }
        String power = collectMessage.getOutCellPower();
        if (power != null && isNumeric(power)) {
            return String.valueOf(Long.valueOf(power) / 2);
        } else {
            return "电量获取失败";
        }
    }


    /**
     * 通过锁的条码号获取蓝牙地址
     *
     * @param lockBarcode
     * @return
     */
    private String getMacAddressByLock(String lockBarcode) {
        if (lockBarcode == null) {
            return null;
        }
        QueryWrapper<Lock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lock_barcode", lockBarcode);
        // 当前状态
        Lock lock = lockService.getOne(queryWrapper, false);
        if (lock == null) {
            return null;
        } else {
            return lock.getMacAddress();
        }
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }


}
