package com.nts.iot.modules.miniApp.rest;

import com.nts.iot.modules.miniApp.model.BikeApiDto;
import com.nts.iot.modules.miniApp.model.RankByUser;
import com.nts.iot.aop.log.Log;
import com.nts.iot.modules.system.model.Bike;
import com.nts.iot.modules.system.service.BikeManagerService;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.JurisdictionConfigurationService;
import com.nts.iot.modules.system.service.OrderManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:pengchenghe
 * @date: 2019-11-06
 * @time: 15:53
 */
@RestController
@RequestMapping("ma")
public class MaStatisticsController {
    @Autowired
    private DeptService deptService;
    @Autowired
    private OrderManagerService orderManagerService;
    @Autowired
    private JurisdictionConfigurationService jurisdictionConfigurationService;
    @Autowired
    BikeManagerService bikeManagerService;


    /**
     * 排行榜
     * @return
     */
    @GetMapping("ranking")
    public Map<String,Object> ranking(@RequestParam String deptId) throws ParseException {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime=simpleDateFormat.format(date)+" 00:00:00";
        String endTime=simpleDateFormat.format(date)+" 23:59:59";
        Long startDate=s.parse(startTime).getTime();
        Long endDate=s.parse(endTime).getTime();
        Map<String,Object> map=new HashMap<>();
        List<Long> jurisdictions= deptService.getSubDepts(Long.parseLong(deptId));
        List<RankByUser> rankByUsers=orderManagerService.rankByJurisdiction(jurisdictions,startDate,endDate);
        //没有骑行过的用户，补足以0显示在list。因上面统计会去除没有骑行的用户，所以此方法补足
        List<RankByUser> rankByUsers1=orderManagerService.rankByAllUser(jurisdictions);
        for(int i=0;i<rankByUsers1.size();i++){
            int finalI = i;
            if(!rankByUsers.stream().anyMatch(a -> a.getId().longValue()==rankByUsers1.get(finalI).getId().longValue())){
                    RankByUser rankByUser=new RankByUser();
                    rankByUser.setId(rankByUsers1.get(i).getId());
                    rankByUser.setName(rankByUsers1.get(i).getName());
                    rankByUser.setAvatar(rankByUsers1.get(i).getAvatar());
                    rankByUser.setMile(0.0);
                    rankByUsers.add(rankByUser);
            }
        }
        if(rankByUsers!=null&&!rankByUsers.isEmpty()){
            map.put("status","200");
            map.put("message","查询排行榜成功");
            map.put("rankByUsers",rankByUsers);
        }else{
            map.put("status","400");
            map.put("message","暂无排行榜数据");
            map.put("rankByUsers","");
        }
        return map;
    }

    /**
     * 车辆列表查询
     *
     * @return
     */
    @GetMapping(value = "/api/bikes")
    public ResponseEntity getBikes(@RequestParam String deptId) {
        Map<String, Object> map = new HashMap<>();
        List<String> jurisdictions = new ArrayList<>();
        List<Long> j= deptService.getSubDepts(Long.parseLong(deptId));
        for(int i=0;i<j.size();i++){
            jurisdictions.add(j.get(i).toString());
        }
        //JurisdictionConfiguration jurisdictionConfiguration = jurisdictionConfigurationService.getJurisdictionConfiguration(Long.parseLong(deptId));
        List<Bike> bikes = bikeManagerService.queryAllApp(jurisdictions);
        //map.put("jurisdictionConfiguration", jurisdictionConfiguration);
        List<BikeApiDto> bikeApiDtos=new ArrayList<>();
        for(int n=0;n<bikes.size();n++){
            BikeApiDto bikeApiDto=new BikeApiDto();
            String bikeCode="";
            if (bikes.get(n).getBikeBarcode().length() > 7) {
                // 截取后7位
                bikeCode = bikes.get(n).getBikeBarcode().substring(bikes.get(n).getBikeBarcode().length() - 7, bikes.get(n).getBikeBarcode().length());
                bikeApiDto.setShortlockNo(bikeCode);
            }else{
                bikeApiDto.setShortlockNo(bikes.get(n).getBikeBarcode());
            }
            bikeApiDto.setBikeId(bikes.get(n).getId());
            bikeApiDtos.add(bikeApiDto);
        }
        map.put("code","200");
        map.put("message","查询成功");
        map.put("bike_list", bikeApiDtos);
        map.put("total_user",bikeApiDtos.size());
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * 查询历史轨迹
     *
     * @param bikeBarcode
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/api/history")
    public ResponseEntity historicalTrack(@RequestParam String bikeBarcode, @RequestParam Long startTime, @RequestParam Long endTime) {
        return new ResponseEntity(bikeManagerService.historicalTrackApi(bikeBarcode, startTime, endTime), HttpStatus.OK);
    }
}
