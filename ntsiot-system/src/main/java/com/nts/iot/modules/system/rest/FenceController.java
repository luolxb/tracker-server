package com.nts.iot.modules.system.rest;
/**
 * @Author zhc@rnstec.com
 * @Description 限行围栏管理
 * @Date 2019-05-05 09:44
 * @Version: 1.0
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Fence;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.DeviceVo;
import com.nts.iot.modules.system.model.vo.FenceDelVo;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.DeviceService;
import com.nts.iot.modules.system.service.FenceService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RestResponse;
import com.nts.iot.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api")
@Api(tags = "围栏管理")
public class FenceController extends JwtBaseController {

    @Autowired
    FenceService fenceService;



    /**
     * 列表查询
     *
     * @return
     */
    @Log("查询限行围栏")
    @GetMapping(value = "/fences")
    @ApiOperation("查看限行围栏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "围栏名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "围栏类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "所属用户id", dataType = "Long", required = true, paramType = "query")
    })
    @PreAuthorize("hasAnyRole('ADMIN','FENCE_ALL','FENCE_DELETE')")
    public RestResponse getList(String name,
                                String type,
                                @RequestParam("userId") Long userId,
                                Pageable pageable,
                                @ApiIgnore @ModelAttribute("user") User user) {
        return RestResponse.success(fenceService.queryAll(name, type, userId, pageable));
    }

    /**
     * 新增限行围栏
     *
     * @param fence
     * @return
     */
    @Log("新增限行围栏")
    @ApiOperation("新增限行围栏")
    @PostMapping(value = "/fences/add")
    @PreAuthorize("hasAnyRole('ADMIN','FENCE_ALL','FENCE_CREATE')")
    public RestResponse create(@Valid @RequestBody Fence fence,
                               @ApiIgnore @ModelAttribute("user") User user) {
        fenceService.create(fence, user);
        return RestResponse.success();
    }

    /**
     * 修改限行围栏
     *
     * @param fence
     * @return
     */
    @Log("修改限行围栏")
    @ApiOperation("修改限行围栏")
    @PutMapping(value = "/fences/edit")
    @PreAuthorize("hasAnyRole('ADMIN','FENCE_ALL','FENCE_EDIT')")
    public RestResponse update(@Valid @RequestBody Fence fence,
                               @ApiIgnore @ModelAttribute("user") User user) {
        fence.setUpdateTime(System.currentTimeMillis());
        fence.setUpdater(user.getUsername());

        //更新围栏表
        fenceService.updateFence(fence);
        return RestResponse.success();
    }

    /**
     * 删除限行围栏
     *
     * @param ids
     * @return
     */
    @ApiOperation("删除限行围栏")
    @Log("删除限行围栏")
    @PostMapping(value = "/fences/delete")
    @PreAuthorize("hasAnyRole('ADMIN','FENCE_ALL','FENCE_DELETE')")
    public RestResponse delete(@RequestBody String ids) {
        JSONObject jsonObject = JSONObject.parseObject(ids);
        List<Integer> results = (List<Integer>) jsonObject.get("ids");
        if (CollectionUtils.isEmpty(results)) {
            return RestResponse.error("操作失败,operation failed");
        }
        List<Long> longList = new ArrayList<>();
        results.forEach( result -> longList.add(Long.parseLong(result + "")));
        fenceService.deleteFence(longList);
        return RestResponse.success();
    }

    /**
     * 获取围栏信息
     *
     * @return
     */
    @Log("查看限行围栏【获取单个围栏】")
    @ApiOperation("查看限行围栏【获取单个围栏】")
    @GetMapping(value = "/fences/get")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fenceId",value = "围栏ID",dataType = "Long",paramType = "query"),
            @ApiImplicitParam(name = "deviceId",value = "设备ID",dataType = "Long",paramType = "query")
    })
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','FENCE_ALL','FENCE_EDIT')")
    public RestResponse getById(Long fenceId,
                                Long deviceId) {
        Fence fence = fenceService.getById(deviceId,fenceId);
        return RestResponse.success(fence);
    }

    /**
     * json 转list
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonConvertList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 根据用户获取围栏信息
     *
     * @param userId
     * @return
     */
//    public Map getFences(Long userId) {
//        Map<Long, Object> map = new HashMap<>();
//        List<Fence> fences = fenceService.selectListByUserId(userId);
//        fences.forEach(it -> {
//            map.put(it.getUserId(), it);
//        });
//        return map;
//    }

    /**
     * 更新缓存
     * @param map 围栏坐标map
     * @param id 围栏主键
     */
//    public void updateRedis(Map map, Long id){
//        List<String> coordinates = new ArrayList<>();
//        Iterator it = map.entrySet().iterator();
//        while (it.hasNext()){
//            Map.Entry entry = (Map.Entry) it.next();
//            coordinates.add(String.valueOf(entry.getValue()));
//        }
//        String redisKey = RedisKey.RESTRICTIONS_FENCE + fenceService.getById(id).getJurisdiction();
//        redisUtil.updateRedis(redisKey, JsonUtil.getJson(coordinates));
//    }
}
