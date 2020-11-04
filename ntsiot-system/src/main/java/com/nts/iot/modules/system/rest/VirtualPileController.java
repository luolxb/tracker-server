package com.nts.iot.modules.system.rest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.VirtualPile;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.VirtualPileService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * @Author zhc@rnstec.com
 * @Description 虚拟桩
 * @Date 2019-05-05 10：11
 * @Version: 1.0
 */
@RestController
@RequestMapping("api")
public class VirtualPileController extends JwtBaseController {

    @Autowired
    private VirtualPileService virtualPileService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DeptService deptService;

    /**
     * 列表查询
     *
     * @return
     */
    @Log("虚拟桩查询")
    @GetMapping(value = "/piles")
    @PreAuthorize("hasAnyRole('ADMIN','VIRTUALPILE_ALL','VIRTUALPILE_DELETE')")
    public ResponseEntity getList(String name, String jurisdiction, Pageable pageable, @ModelAttribute("user") User user) {

        List<String> jurisdictions = new ArrayList<>();

        if (jurisdiction != null) {
            jurisdictions.add(jurisdiction);
        } else {
            List<Dept> deptList = deptService.findByUserRole(user);
            for (int i = 0; i < deptList.size(); i++) {
                Dept dept = deptList.get(i);
                jurisdictions.add(String.valueOf(dept.getId()));
            }
        }

        return new ResponseEntity(virtualPileService.queryAll(name, jurisdictions, pageable), HttpStatus.OK);
    }

    /**
     * 删除虚拟桩
     *
     * @param id
     * @return
     */
    @Log("删除虚拟桩")
    @DeleteMapping(value = "/piles/{id}")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','VIRTUALPILE_ALL','VIRTUALPILE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        VirtualPile pile = virtualPileService.getById(id);
        //删除缓存
        Map<Long, VirtualPile> pileMap = this.getPileByJurisdiction(pile.getJurisdiction());
        pileMap.remove(id);
        this.updateRedis(pileMap, pile.getJurisdiction());
        return new ResponseEntity(virtualPileService.removeById(id), HttpStatus.OK);
    }

    /**
     * 新增虚拟桩
     *
     * @param virtualPile
     * @return
     */
    @Log("新增虚拟桩")
    @PostMapping(value = "/piles/add")
    @PreAuthorize("hasAnyRole('ADMIN','VIRTUALPILE_ALL','VIRTUALPILE_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody VirtualPile virtualPile, @ModelAttribute("user") User user) {
        virtualPile.setCreateTime(System.currentTimeMillis());
        virtualPile.setCreator(String.valueOf(user.getId()));
        virtualPileService.save(virtualPile);
        //更新缓存
        String redisKey = RedisKey.VIRTUAL_PILE + virtualPile.getJurisdiction();
        boolean hasKey = redisUtil.hasKey(redisKey);
        if(hasKey){
            //已存在，则更新
            List<VirtualPile> piles = JsonUtil.jsonConvertList(redisUtil.getData(redisKey), VirtualPile.class);
            piles.add(virtualPile);
            redisUtil.updateRedis(redisKey, JSON.toJSONString(piles));
        } else {
            //不存在，则新增
            List<VirtualPile> piles = new ArrayList<>();
            piles.add(virtualPile);
            redisUtil.addRedis(redisKey, JSON.toJSONString(piles));
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 修改虚拟桩
     *
     * @param virtualPile
     * @return
     */
    @Log("修改虚拟桩")
    @PutMapping(value = "/piles/edit")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','VIRTUALPILE_ALL','VIRTUALPILE__EDIT')")
    public ResponseEntity update(@RequestBody VirtualPile virtualPile, @ModelAttribute("user") User user) {
        virtualPile.setUpdateTime(System.currentTimeMillis());
        virtualPile.setUpdater(String.valueOf(user.getId()));
        virtualPileService.updateById(virtualPile);

        //更新缓存
        Map<Long, VirtualPile> pileMap = this.getPileByJurisdiction(virtualPile.getJurisdiction());
        pileMap.put(virtualPile.getId(), virtualPile);
        this.updateRedis(pileMap, virtualPile.getJurisdiction());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 获取虚拟桩
     * @return
     */
    @Log("查看虚拟桩")
    @GetMapping(value = "/piles/get/{id}")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','VIRTUALPILE_ALL','VIRTUALPILE_EDIT')")
    public ResponseEntity getById(@PathVariable Long id) {
        VirtualPile virtualPile = virtualPileService.getById(id);
        return new ResponseEntity(virtualPile, HttpStatus.OK);
    }

    /**
     * 根据辖区获取虚拟桩
     * @param jurisdiction
     * @return
     */
    public Map<Long, VirtualPile> getPileByJurisdiction(Long jurisdiction){
        Map<Long, VirtualPile> pileMap = new HashMap<>();
        QueryWrapper<VirtualPile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("jurisdiction", jurisdiction);
        List<VirtualPile> list = virtualPileService.list(queryWrapper);
        list.forEach(it -> {
            pileMap.put(it.getId(), it);
        });
        return pileMap;
    }

    /**
     * 更新缓存
     * @param map
     * @param jurisdiction
     */
    public void updateRedis(Map map, Long jurisdiction){
        List<VirtualPile> piles = new ArrayList<>();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            piles.add((VirtualPile)entry.getValue());
        }
        String redisKey = RedisKey.VIRTUAL_PILE + jurisdiction;
        redisUtil.updateRedis(redisKey, JsonUtil.getJson(piles));
    }
}
