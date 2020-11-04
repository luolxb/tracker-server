package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.ConstantClass;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.util.GpsUtil;
import com.nts.iot.modules.miniApp.dto.RidingTrack;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.model.RankByBike;
import com.nts.iot.modules.miniApp.model.RankByUser;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.system.dao.OrderManagerMapper;
import com.nts.iot.modules.system.model.Bike;
import com.nts.iot.modules.system.model.Order;
import com.nts.iot.modules.system.service.BikeManagerService;
import com.nts.iot.modules.system.service.OrderManagerService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.nts.iot.constant.ConstantClass.ORDER_STATUS_02;
import static com.nts.iot.constant.RedisKey.LIST_ORDER_KEY;

/**
 * @Author zhc@rnstec.com
 * @Description
 * @Date 2019-05-05 10:40
 * @Version: 1.0
 */
@Slf4j
@Service
public class OrderManagerServiceImpl extends ServiceImpl<OrderManagerMapper, Order> implements OrderManagerService {

    @Autowired
    private MaUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BikeManagerService bikeManagerService;

    @Value("${engineServerUrl}")
    private String E_URL;

    @Override
    public Map queryAll(Pageable pageable, Order order, List<String> jurisdictions) {
        Page<Order> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<Order> pageResult = baseMapper.selectByPage(page, order, jurisdictions);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public Map<String, Object> closeOrder(String bikeBarcode, String lockBarcode, Long closeSource) {
        Map<String, Object> result = new HashMap<>();
        Long updateTime = System.currentTimeMillis();
        QueryWrapper<Order> query = new QueryWrapper<>();
        query.eq("bike_barcode", bikeBarcode);
        query.eq("status", ORDER_STATUS_02);
        //获取订单
        List<Order> orderList = this.list(query);
        List<Order> changedOrders = new ArrayList<>();
        for (Order order : orderList) {
            order.setEndTime(updateTime);
            order.setStatus(ConstantClass.ORDER_STATUS_01);
            order.setUpdateTime(updateTime);
            order.setMile(getDistance(order.getOrderId(), lockBarcode));
            // 结束来源 0:正常关闭 3:后台关闭
            order.setCloseSource(closeSource);
            changedOrders.add(order);
        }
        if (changedOrders.isEmpty()) {
            result.put("code", 500);
            result.put("message", "未查询到订单");
            return result;
        }
        //更新订单状态
        this.updateBatchById(changedOrders);
        for (Order order : changedOrders) {
            String url = E_URL + "/order/save/" + order.getOrderId();
            String result2 = HttpRequest.post(url).execute().body();
            //删除缓存
            String orderKey = RedisKey.ORDER_KEY + lockBarcode + ":" + order.getOrderId();
            log.info("OrderManagerServiceImpl.closeOrder deleteByKey orderKey=" + orderKey);
            redisUtil.deleteByKey(orderKey);
            // 更新订单编号缓存
            List<String> redisOrders = JsonUtil.jsonConvertList(redisUtil.getData(LIST_ORDER_KEY), String.class);

            CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList(redisOrders);
            if (redisOrders != null) {
                for (String orderId : copyOnWriteArrayList) {
                    if (orderId.equals(order.getOrderId())) {
                        redisOrders.remove(orderId);
                    }
                }
                redisUtil.addRedis(LIST_ORDER_KEY, JSON.toJSONString(redisOrders));
            }
        }
        result.put("code", 200);
        result.put("message", "关闭成功");
        return result;
    }

    @Override
    public Order createOrder(String bikeBarcode, String lockBarcode, String maOpenId) {
        Bike bike = bikeManagerService.getBikeByBarcode(bikeBarcode);
        if (bike == null) {
            return null;
        }
        List<String> orderList = new ArrayList<>();
        orderList = JsonUtil.jsonConvertList(redisUtil.getData(LIST_ORDER_KEY), String.class);
        //订单编号
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long orderId = snowflake.nextId();
        //创建时间
        Long createTime = System.currentTimeMillis();
        //用户信息
        MaUser user = userService.getUserByWxId(maOpenId);

        //订单信息
        Order order = new Order();
        order.setOrderId(String.valueOf(orderId));
        order.setStatus(ORDER_STATUS_02); //进行中
        if (user != null) {
            order.setUserId(user.getId());
            order.setJurisdiction(user.getDeptId());
        }
        order.setBikeBarcode(bikeBarcode);
        order.setStartTime(createTime);
        order.setCreateTime(createTime);
        order.setUnlockMode(bike.getUnlockMode());

        baseMapper.insert(order);
        //加入缓存
        String redisKey = RedisKey.ORDER_KEY + lockBarcode + ":" + orderId;
        redisUtil.addRedis(redisKey, JSON.toJSONString(order));
        // 加入订单编号list
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        orderList.add(order.getOrderId());
        redisUtil.addRedis(LIST_ORDER_KEY, JSON.toJSONString(orderList));

        return order;
    }


//    @Override
//    public List<CollectMessage> getTrajectoryByUserId(Long userId, String lockBarcode) {
//        String redisKey = RedisKey.ORDER_TRAJECTORY_KEY + lockBarcode + this.findOrder(userId).getOrderId();
//        return JsonUtil.jsonConvertList(redisUtil.getData(redisKey), CollectMessage.class);
//    }

    /**
     * 通过人找订单
     *
     * @param userId
     * @return
     */
    private Order findOrder(Long userId) {
        QueryWrapper<Order> query = new QueryWrapper<>();
        query.eq("user_id", userId);
        query.eq("status", ORDER_STATUS_02);
        return baseMapper.selectOne(query);
    }

    @Override
    public RidingTrack getRidingTrack(String orderId) {
        String url = E_URL + "/order/ridingTrack/" + orderId;
        String result = HttpRequest.get(url).execute().body();
        return JsonUtil.jsonConvertObject(result, RidingTrack.class);
    }


    /**
     * 订单实时轨迹
     *
     * @param orderId     订单编号
     * @param lockBarcode 锁编号
     * @return
     */
    @Override
    public Double getDistance(String orderId, String lockBarcode) {
        // redisKey = key + 订单编号 + 锁编号
        String redisKey = RedisKey.ORDER_TRAJECTORY_KEY + orderId + ":" + lockBarcode;

        // 查询轨迹信息json
        String ordeTrackInfo = redisUtil.getData(redisKey);

        // 轨迹信息判空
        if (ordeTrackInfo != null && !"".equals(ordeTrackInfo)) {
            List<com.nts.iot.dto.CollectMessage> list = JsonUtil.jsonConvertList(ordeTrackInfo, com.nts.iot.dto.CollectMessage.class);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Object o = it.next();
                CollectMessage c = JSONObject.parseObject(JsonUtil.getJson(o), CollectMessage.class);
                if (c.getLongitude().equals("0.0") || c.getLatitude().equals("0.0")) {
                    it.remove();
                }
            }
            Double distance = 0.0;
            // Long time = 0L;
            // list判空
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (i + 1 <= list.size() - 1) {
                        distance += getDistance(list.get(i), list.get(i + 1));
                    }
                }
                // time = getTime(list.get(0).getTime(), list.get(list.size() - 1).getTime());
            }
            // 距离取整
            return Math.floor(distance);
        }
        return 0d;
    }

    @Override
    public List<Order> getRunningOrdersByBarcode(String lockBarcode) {
        // 根据用户查询订单状态，如果该用户正在骑车，且正在骑一个车，表示这个人正在行进中
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        // 订单进行中
        queryWrapper.eq("status", ORDER_STATUS_02);
        // 查询某人
        queryWrapper.eq("bike_barcode", lockBarcode);
        return this.list(queryWrapper);
    }

    @Override
    public Order getRunningOrderByUserId(String userId) {
        // 根据用户查询订单状态，如果该用户正在骑车，且正在骑一个车，表示这个人正在行进中
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        // 订单进行中
        queryWrapper.eq("status", 2L);
        // 查询某人
        queryWrapper.eq("user_id", Long.valueOf(userId));
        List<Order> orders = this.list(queryWrapper);
        if (orders != null && orders.size() > 0) {
            return orders.get(0);
        } else {
            return null;
        }
    }


    /**
     * 获得时间
     *
     * @param start
     * @param endTime
     * @return
     */
    private Long getTime(String start, String endTime) {
        Long time = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            time = sdf.parse(endTime).getTime() - sdf.parse(start).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获得距离
     *
     * @return
     */
    private Double getDistance(com.nts.iot.dto.CollectMessage cm1, com.nts.iot.dto.CollectMessage cm2) {
        //return GpsUtil.getDistanceMeter(cm1, cm2, Ellipsoid.WGS84);
        return GpsUtil.getDistance(cm1, cm2);
    }

    @Override
    public void initOrderIds() {
        List<String> orders = new ArrayList<>();
        // 未关闭订单
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("status", 2);
        List<Order> list = baseMapper.selectByMap(columnMap);
        for (Order order : list) {
            orders.add(order.getOrderId());
        }
        redisUtil.addRedis(LIST_ORDER_KEY, JSON.toJSONString(orders));
    }

    /**
     * 根据锁号创建订单
     *
     * @param deviceNo
     */
    @Override
    public Map<String, Object> createByDeviceNo(String deviceNo) {
        Map<String, Object> res = new HashMap<>();
        String stateJson = redisUtil.getData(RedisKey.PRE_ORDER_KEY + deviceNo);
        // 车辆状态存在
        if (stateJson != null && !"".equals(stateJson)) {
            String[] strarr = stateJson.split(",");
            if (strarr.length == 3) {
                String deviceNoStr = strarr[0];
                String openId = strarr[1];
                String bikeBarCode = strarr[2];
                // 查询此人是否已经有订单
                MaUser maUser = userService.getUserByWxId(openId);
                if (maUser != null) {
                    // 查询该用户是否有未完成订单
                    // 根据用户查询订单状态，如果该用户正在骑车，且正在骑一个车，表示这个人正在行进中
                    QueryWrapper<Order> queryWrapper2 = new QueryWrapper<>();
                    // 订单进行中
                    queryWrapper2.eq("status", 2L);
                    // 查询某人
                    queryWrapper2.eq("user_id", maUser.getId());
                    queryWrapper2.eq("bike_barcode", bikeBarCode);
                    List<Order> orders = this.list(queryWrapper2);
                    if (orders == null || orders.size() == 0) {
                        Order order = this.createOrder(bikeBarCode, deviceNo, openId);
                        if (order == null) {
                            res.put("isCreate", "no");
                        }
                        res.put("isCreate", "yes");

                        // 删除缓存
                        redisUtil.deleteByKey(RedisKey.PRE_ORDER_KEY + deviceNo);
                    } else {
                        res.put("isCreate", "no");
                    }
                } else {
                    res.put("isCreate", "no");
                }
            } else {
                res.put("isCreate", "no");
            }
        } else {
            res.put("isCreate", "no");
        }
        return res;
    }

    /**
     * 根据锁号关闭订单
     *
     * @param lockBarCode
     */
    @Override
    public Map<String, Object> closeOrderByDeviceNo(String lockBarCode) {
        Map<String, Object> result = new HashMap<>();
        // check 关锁
//        if (bikeManagerService.checkCloseLock(lockBarCode)) {
        Bike bike = bikeManagerService.getBikeByDeviceNo(lockBarCode);
        if (bike == null) {
            result.put("code", 500);
            result.put("message", "关锁失败，不存在该车");
            return result;
        }
        // 根据用户查询订单状态，如果该用户正在骑车，且正在骑一个车，表示这个人正在行进中
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        // 订单进行中
        queryWrapper.eq("status", 2L);
        // 查询某人
        queryWrapper.eq("bike_barcode", bike.getBikeBarcode());
        List<Order> orders = this.list(queryWrapper);
        if (orders != null && orders.size() > 0) {
            this.closeOrder(bike.getBikeBarcode(), bike.getLockBarcode(), 0L);
        } else {
            result.put("code", 500);
            result.put("message", "关锁失败，未查询到订单");
            return result;
        }
        result.put("code", 200);
        result.put("message", "关闭成功");
//        } else {
//            result.put("code", 500);
//            result.put("message", "关锁失败，未在指定区域内关锁！");
//        }
        return result;
    }


    @Override
    public List<RankByUser> rankByJurisdiction(List<Long> jurisdictions, Long startTime, Long endTime) {
        return baseMapper.rankByJurisdiction(jurisdictions, startTime, endTime);
    }

    @Override
    public List<RankByUser> rankByAllUser(List<Long> jurisdictions){
        return baseMapper.rankByAllUser(jurisdictions);
    }

    @Override
    public Map rankByJurisdictionPc(Page<RankByUser> page,List<Long> jurisdictions, Long startTime, Long endTime,Integer count) {
        Map map = new HashMap();
        //计数器，初始为0。
        //因为需要显示时间段内没有骑行记录的用户，而且有分页存在。计数器用来判断每次取了多少个没有骑行的用户来填充
        map.put("count",0);
        page.setRecords(baseMapper.rankByJurisdictionPc(page,jurisdictions, startTime, endTime));
        //获取有骑行记录的list
        List<RankByUser> rankByUsers=page.getRecords();

        //获取没有骑行记录的lists
        List<RankByUser> lists=baseMapper.rankByAllUserPc(jurisdictions,startTime,endTime);

        //计算原始有骑行记录的数据，分页数
        int pageNum= (int) ((page.getTotal()+page.getSize()-1)/page.getSize());
        //返回list和lists的总数据，给前端计算页数
        page.setTotal(page.getTotal()+lists.size());
        int counts=count;
        if(lists!=null&&!lists.isEmpty()){
            //当前页数刚好等于 原始有骑行记录的数据分页数，则确定最后一页
            if((int)page.getCurrent()==pageNum){
                //如果最后一页的数据没有填充满，则开始取没有骑行记录的数据填充
                if(page.getRecords().size()<page.getSize()){
                    map.put("count",page.getSize()-page.getRecords().size());
                    //List<RankByUser> list=baseMapper.rankByAllUserPc(jurisdictions,startTime,endTime);
                    //计算需要填充的数量
                    int s= (int) (page.getSize()-page.getRecords().size());
                    //判断填充数量是否大于没有骑行记录lists的大小，来决定循环次数
                    if(s>lists.size()){
                        for(int j=0;j<lists.size();j++){
                            rankByUsers.add(lists.get(j));
                        }
                        page.setRecords(rankByUsers);
                    }else{
                        for(int j=0;j<s;j++){
                            rankByUsers.add(lists.get(j));
                        }
                        page.setRecords(rankByUsers);
                    }
                }
            }else if((int)page.getCurrent()>pageNum){
                //List<RankByUser> list=baseMapper.rankByAllUserPc(jurisdictions,startTime,endTime);
                //根据计数器，去除之前已经填充的数据
                for (int n = 0; n < count; n++) {
                    lists.remove(0);
                }
                if(page.getSize()<=lists.size()){
                    for(int j=1;j<=page.getSize();j++){
                        //累积计数器
                        map.put("count",counts+j);
                        rankByUsers.add(lists.get(j-1));
                    }
                }else{
                    for(int j=1;j<=lists.size();j++){
                        rankByUsers.add(lists.get(j-1));
                    }
                }
                page.setRecords(rankByUsers);
            }
        }
        //List<RankByUser> list=baseMapper.rankByAllUserPc(jurisdictions);
        map.putAll(PageUtil.toPage(page));
        return map;
    }

    @Override
    public Map rankByBikeJurisdictionPc(Page<RankByBike> page, List<Long> jurisdictions, Long startTime, Long endTime) {
        page.setRecords(baseMapper.rankByBikeJurisdictionPc(page,jurisdictions, startTime, endTime));
        return PageUtil.toPage(page);
    }




}
