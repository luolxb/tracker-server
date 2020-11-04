package com.nts.iot.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.system.dao.LockMapper;
import com.nts.iot.modules.system.model.Bike;
import com.nts.iot.modules.system.model.Lock;
import com.nts.iot.modules.system.model.Order;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.BikeManagerService;
import com.nts.iot.modules.system.service.LockService;
import com.nts.iot.modules.system.service.OrderManagerService;
import com.nts.iot.util.ExcelExportUtil;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.RedisUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nts.iot.constant.ConstantClass.ORDER_STATUS_02;

@Service
public class LockServiceImpl extends ServiceImpl<LockMapper, Lock> implements LockService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    MaUserService maUserService;

    @Autowired
    BikeManagerService bikeService;

    @Autowired
    OrderManagerService orderService;

    /**
     * queryAll
     *
     * @param pageable
     * @param lockBarcode
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Object queryAll(Pageable pageable, String lockBarcode, String startTime, String endTime) {
        Page<Lock> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<Lock> pageResult = baseMapper.queryAllById(page, lockBarcode, startTime, endTime);
        if (pageResult != null && pageResult.getRecords() != null && pageResult.getRecords().size() > 0) {
            for (int i = 0; i < pageResult.getRecords().size(); i++) {
                String lb = pageResult.getRecords().get(i).getLockBarcode();
                redisUtil.getData(RedisKey.VECHILE_STATE + lb);
                CollectMessage collectMessage = JsonUtil.jsonConvertObject(redisUtil.getData(RedisKey.VECHILE_STATE + lb), CollectMessage.class);
                if (collectMessage != null) {
                    pageResult.getRecords().get(i).setTime(collectMessage.getTime());
                }
            }
        }
        return PageUtil.toPage(pageResult);
    }

    /**
     * create
     *
     * @param lock
     * @param user
     * @return
     */
    @Override
    public Integer create(Lock lock, User user) {
        lock.setIsUse("0");
        lock.setCreator(String.valueOf(user.getId()));
        lock.setCreateTime(System.currentTimeMillis());
        return baseMapper.insert(lock);
    }

    /**
     * update
     *
     * @param lock
     * @param user
     */
    @Override
    public void update(Lock lock, User user) {
        lock.setUpdater(String.valueOf(user.getId()));
        lock.setUpdateTime(System.currentTimeMillis());
        baseMapper.updateById(lock);
    }

    /**
     * delete
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

    /**
     * 查询未使用的车锁  (添加.修改.车辆信息智能模块编号（锁编号使用）)
     *
     * @return
     */
    @Override
    public List<Lock> selectLock() {
        return baseMapper.selectLock();
    }

    /**
     * 查询车锁状态
     *
     * @param id
     * @return
     */
    @Override
    public Boolean selectLockStatus(Long id) {
        Boolean flag = false;
        Lock lock = baseMapper.selectLockStatus(id);
        if ("0".equals(lock.getIsUse())) {
            flag = true;
        }
        return flag;
    }

    /**
     * 修改车锁状态
     *
     * @param status
     * @param lockBarcode
     */
    @Override
    public void updateStatusLock(String status, String lockBarcode) {
        baseMapper.updateStatusLock(status, lockBarcode);
    }

    @Override
    public List<Lock> findAll() {
        return baseMapper.selectAll();
    }

    @Override
    public void initLock() {
        List<Lock> locks = this.findAll();
        List<String> list = new ArrayList<>();
        for (Lock lock : locks) {
            list.add(lock.getLockBarcode());
        }
        redisUtil.addRedis(RedisKey.TRACKER_LIST_KEY, JSON.toJSONString(list));
    }

    @Override
    public Lock findLockByNo(String no) {
        List<Lock> locks = baseMapper.selectLockByLockNo(no);
        Lock lock = null;
        if (locks != null && locks.size() > 0) {
            lock = locks.get(0);
        }
        return lock;
    }

    @Override
    public void exportLock(HttpServletResponse res, String path,String lockBarcode, String startTime, String endTime) {
        String templateFilePath = path + File.separator + "lock.xlsx";
        List<Lock> lockList = baseMapper.selectLockAll(lockBarcode, startTime, endTime);
        if (lockList != null && lockList.size() > 0) {
            ExcelExportUtil excel = new ExcelExportUtil();
            try {
                excel.writeData(templateFilePath, null, 0);
                List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
                for (int i = 0; i < lockList.size(); i++) {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, lockList.get(i).getLockBarcode());
                    data.put(2, lockList.get(i).getSimIccidNo());
                    data.put(3, lockList.get(i).getMacAddress());
                    data.put(4, lockList.get(i).getTime());
                    data.put(5, lockList.get(i).getAuthorizationCode());
                    datalist.add(data);
                }
                String[] heads = new String[]{"A3", "B3", "C3", "D3", "E3"};
                excel.writeDateList(templateFilePath, heads, datalist, 0, res, "车锁列表");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过用户id 查询 车锁编号
     *
     * @param userId 用户编号
     * @return
     */
    @Override
    public String getLockBarcodeByUserId(String userId) {
        // 查询 t_ma_user
        if (userId==null){
            return null;
        }
        MaUser user = maUserService.getById(Long.valueOf(userId));
        /* 不存在人则返回NULL  */
        if (user==null){
            return null;
        }
        QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("user_id", userId);
        // 订单状态：进行中
        orderWrapper.eq("status", ORDER_STATUS_02);
        /* 查询该用户是否有订单 */
        List<Order> orders = orderService.list(orderWrapper);
        // 当有且只有一个开启订单时
        if (orders != null && orders.size()==1) {
            String bikeBarcode = orders.get(0).getBikeBarcode();
            if (bikeBarcode == null) {
                return null;
            }else {
                /* 用车编号 */
                Bike bike = bikeService.getBikeByBarcode(bikeBarcode);
                return bike.getLockBarcode();
            }
        }else {
            /* 如果没有订单或者这个人订单存在脏数据的话，查询该人绑定的车 */
            if (user.getPhone()==null){
                return null;
            }else {
                QueryWrapper<Bike> bikeWrapper = new QueryWrapper<>();
                bikeWrapper.eq("phone", user.getPhone());
                Bike bike = bikeService.getOne(bikeWrapper,false);
                if (bike==null){
                    return null;
                }else {
                    return bike.getLockBarcode();
                }
            }
        }
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
}
