package com.nts.iot.modules.system.rest;

import cn.hutool.http.HttpRequest;
import com.nts.iot.constant.MiniAppConstants;
import com.nts.iot.dto.CmdPackage;
import com.nts.iot.modules.system.model.Lock;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.LockService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dto.DeviceAttribute;
import com.nts.iot.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class LockController extends JwtBaseController {

    @Autowired
    private LockService lockService;

    private static final String ENTITY_NAME = "lock";

    @Value("${excelTemplate.path}")
    private String path;

    @Value("${jt808ServerUrl}")
    private String jt808ServerUrl;

    @Value("${jt808ScottServerUrl}")
    private String jt808ScottServerUrl;

    // 开锁
    public static final String MINI_OPEN_LOCK = "open";

    // 关锁
    public static final String MINI_OPEN_UNLOCK = "close";

    /**
     * 查询
     *
     * @param lockBarcode
     * @param pageable
     * @return
     */
    @Log("车锁查询")
    @GetMapping(value = "/lock")
    @PreAuthorize("hasAnyRole('ADMIN','LOCK_ALL','LOCK_SELECT')")
    public ResponseEntity getCheckPoint(Pageable pageable, @RequestParam(required = false) String lockBarcode, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
        System.out.println("String startTime=================================================================================================" + startTime);
        return new ResponseEntity(lockService.queryAll(pageable, lockBarcode, startTime, endTime), HttpStatus.OK);
    }

    /**
     * 新增
     *
     * @param lock
     * @return
     */
    @Log("车锁新增")
    @PostMapping(value = "/lock/add")
    @PreAuthorize("hasAnyRole('ADMIN','LOCK_ALL','LOCK_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody Lock lock, @ModelAttribute("user") User user) {
        if (lock.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity(lockService.create(lock, user), HttpStatus.CREATED);
    }

    /**
     * 修改
     *
     * @param lock
     * @return
     */
    @Log("车锁修改")
    @PutMapping(value = "/lock/edit")
    @PreAuthorize("hasAnyRole('ADMIN','LOCK_ALL','LOCK_EDIT')")
    public ResponseEntity update(@RequestBody Lock lock, @ModelAttribute("user") User user) {
        lockService.update(lock, user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Log("车锁删除")
    @DeleteMapping(value = "/lock/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LOCK_ALL','LOCK_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        lockService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 验证锁是否在使用
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/lock/checkDel/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LOCK_ALL','LOCK_DELETE')")
    public Boolean checkLock(@PathVariable Long id) {
        return lockService.selectLockStatus(id);
    }

    /**
     * 设置终端参数
     * @param lockCode
     * @param ip
     * @param port
     * @return
     */
    @Log("切换服务器")
    @PostMapping("/settingValue/{lockCode}/{ip}/{port}/{workfre}/{savefre}")
    public Map<String, Object> settingValue(@PathVariable String lockCode, @PathVariable String ip, @PathVariable String port,@PathVariable String workfre,@PathVariable String savefre) {
        Map<String, Object> result = new HashMap<>();
//        CmdPackage cmdPackage = new CmdPackage();
//        cmdPackage.setDeviceNo(lockCode);
//        Map<String, Object> map = new HashMap<>();
//        map.put("ip", ip);
//        map.put("port", port);
//        cmdPackage.setValue(map);
//        Long timesteamp = System.currentTimeMillis() / 1000;
//        cmdPackage.setTimestamp(String.valueOf(timesteamp));
        DeviceAttribute attribute=new DeviceAttribute();
        attribute.setIpAddress(ip);
        attribute.setPort(Integer.parseInt(port));
        attribute.setApn("cmcc");
        attribute.setDeviceNo(lockCode);
        attribute.setWork_fre(Integer.parseInt(workfre));
        attribute.setSavemode_fre(Integer.parseInt(savefre));
        String result2;
//        if(!lockCode.startsWith("c3")){
//            result2 = HttpRequest.post(jt808ScottServerUrl + "/api/deviceconfig").body(JsonUtil.getJson(attribute)).execute().body();
//        }else {
            result2 = HttpRequest.post(jt808ServerUrl + "/api/deviceconfig").body(JsonUtil.getJson(attribute)).execute().body();
//        }
        System.out.println(result2);
        result.put("code", 200);
        result.put("message", "参数设置成功");
        result.put("result",result2);
        return result;
    }

    /**
     * 接收客户端发来的消息(开锁页面)
     */
    @Log("开关锁")
    @PostMapping("/sendOpen")
    public void sendOpen(@RequestBody CmdPackage cmdPackage) {
        Long timesteamp = System.currentTimeMillis() / 1000;
        cmdPackage.setTimestamp(String.valueOf(timesteamp));
        // 消息头 反控
        cmdPackage.setHeader(MiniAppConstants.MESSAGE_HEAD_CONTROL);
        if (MINI_OPEN_LOCK.equals(cmdPackage.getType())) {
            // 消息类型 开锁
            cmdPackage.setType(MiniAppConstants.CONTENT_CONTROL_OPEN_LOCK);
        } else if (MINI_OPEN_UNLOCK.equals(cmdPackage.getType())) {
            // 消息类型 开锁
            cmdPackage.setType(MiniAppConstants.CONTENT_CONTROL_CLOSE_LOCK);
            {
                System.out.println("closed 预留");
            }
        }
        String result1;
//        if(!cmdPackage.getDeviceNo().startsWith("c3")){
//            result1 = HttpRequest.post(jt808ScottServerUrl + "/api/counterControl")
//                    .body(JsonUtil.getJson(cmdPackage))
//                    .execute().body();
//        }else {
            result1 = HttpRequest.post(jt808ServerUrl + "/api/counterControl")
                    .body(JsonUtil.getJson(cmdPackage))
                    .execute().body();
//        }
//        System.out.println(result1);
    }
}
