package com.nts.iot.modules.system.rest;

import com.alibaba.fastjson.JSONObject;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.UserOperationalVo;
import com.nts.iot.modules.system.model.vo.UserRq;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.UserService;
import com.nts.iot.util.RestResponse;
import com.nts.iot.utils.EncryptUtils;
import com.nts.iot.utils.RedisUtil;
import com.nts.iot.utils.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jie
 * @date 2018-11-23
 */
@RestController
@RequestMapping("api")
@Api(tags = "用户")
public class UserController extends JwtBaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RedisUtil redisUtil;


    @Log("查询用户")
    @ApiOperation(value = "查询用户")
    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public RestResponse getUsers(UserRq userRq, Pageable pageable) {
        // 根据条件 查询用户信息
        Map<String, Object> users = userService.getUsers(userRq, pageable);
        return RestResponse.success(users);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("导出用户")
    @GetMapping("/user/export/{userId}")
    @ApiOperation("导出用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "搜索条件", required = false, dataType = "String", paramType = "query")
    })
    public RestResponse export( String userName,
                               @PathVariable Long userId) {
        // 导出用户信息
        return userService.export(userId,userName);

    }

    @Log("查询用户树")
    @ApiOperation(value = "查询用户树")
    @GetMapping(value = "/usersTree")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public RestResponse usersTree(UserRq userRq) {
        // 根据条件 查询用户信息
        List<User> users = userService.usersTree(userRq);
        return RestResponse.success(users);
    }

    @Log("新增用户")
    @ApiOperation("新增用户")
    @PostMapping(value = "/user")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public RestResponse createUser(@Valid @RequestBody UserRq userRq,
                                   BindingResult result,
                                   @ApiIgnore @ModelAttribute("user") User user) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getFieldError().getDefaultMessage());
        }
        userService.create(userRq,user);
        return RestResponse.success();
    }

    @Log("修改用户")
    @ApiOperation("修改用户")
    @PutMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_EDIT')")
    public RestResponse updateUser(@Valid @RequestBody UserRq resources) {
        userService.update(resources);
        return RestResponse.success();
    }

    @Log("删除用户")
    @ApiOperation("删除用户")
    @PostMapping(value = "/users/delete")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    public RestResponse deleteUser(@RequestBody String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new BadRequestException("删除ID不能为空");
        }
        JSONObject parse = (JSONObject)JSONObject.parse(ids);
        List<Integer> stringList = (List<Integer>)parse.get("ids");
        if (CollectionUtils.isEmpty(stringList)) {
            throw new BadRequestException("删除ID不能为空");
        }
        List<Long> longList= new ArrayList<>();
        stringList.forEach(s -> longList.add(Long.parseLong(s + "")));

        //删除用户信息
        userService.deleteByIds(longList);
        return RestResponse.success();
    }

    /**
     * 修改密码
     *
     * @param jsonPass
     * @return
     */
    @Log("修改密码")
    @ApiOperation("修改密码")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @PutMapping(value = "/users/updatePass")
    public RestResponse updatePass(@RequestBody String jsonPass) {
        // 判断json 是否为空字符串
        if (StringUtils.isBlank(jsonPass)) {
            throw new BadRequestException("密码不能为空,password can not be blank");
        }
        JSONObject parse = (JSONObject)JSONObject.parse(jsonPass);
        String oldPassword = (String)parse.get("oldPassword");
       if (StringUtils.isBlank(oldPassword)) {
           throw new BadRequestException("旧密码不能为空,old password can not be blank");
       }
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        if (!userDetails.getPassword().equals(EncryptUtils.encryptPassword(oldPassword))) {
            throw new BadRequestException("旧密码验证错误,old password verification error");
        }

        // 判断密码 是否为空字符串
        String pass = (String)parse.get("password");
        if (StringUtils.isBlank(pass)) {
            throw new BadRequestException("密码不能为空,password can not be blank");
        }
        // 获取缓存中的用户信息
        if (userDetails.getPassword().equals(EncryptUtils.encryptPassword(pass))) {
            throw new BadRequestException("新密码不能与旧密码相同,The new password cannot be the same as the old password");
        }
        // 页面修改密码后直接回到登陆页面没有调用退出登录接口，这里需要修改密码成功后清除redis 缓存
        String key = RedisKey.USER_INFO_KEY + userDetails.getUsername();
        redisUtil.deleteByKey(key);
        userService.updatePass(userDetails.getUsername(), EncryptUtils.encryptPassword(pass));
        return RestResponse.success();
    }

    @Log("运营统计")
    @ApiOperation("运营统计")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "query")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @GetMapping(value = "/user/operationalStatistics/{id}")
    public RestResponse operationalStatistics(@PathVariable("id") Long userId) {
        UserOperationalVo userOperationalVo = userService.operationalStatistics(userId);
        return RestResponse.success(userOperationalVo);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("重置用户密码")
    @PutMapping("/user/resetPass/{id}")
    @ApiOperation("重置用户密码")
    public RestResponse resetPass(@PathVariable Long id ,
                                  @ApiIgnore @ModelAttribute("user") User user) {
        userService.resetPass(user,id);
        return RestResponse.success();
    }


/*********************************************************分割线**************************************************************/

//    /**
//     * 验证密码
//     * @param pass
//     * @return
//     */
//    @ApiOperation("验证密码")
//    @GetMapping(value = "/users/validPass/{pass}")
//    public ResponseEntity validPass(@PathVariable String pass){
//        UserDetails userDetails = SecurityContextHolder.getUserDetails();
//        Map map = new HashMap();
//        map.put("status",200);
//        if(!userDetails.getPassword().equals(EncryptUtils.encryptPassword(pass))){
//            map.put("status",400);
//        }
//        return new ResponseEntity(map,HttpStatus.OK);
//    }
//
//    /**
//     * 修改头像
//     * @param urlPath
//     * @param user
//     * @return
//     */
//    @ApiOperation("修改头像")
//    @PostMapping(value = "/users/updateAvatar")
//    public ResponseEntity updateAvatar(@RequestParam String urlPath, @ModelAttribute("user") User user){
//        userService.updateAvatar(user.getUsername(), urlPath);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    /**
//     * 修改邮箱
//     * @param user
//     * @param user
//     * @return
//     */
//    @Log("修改邮箱")
//    @ApiOperation("修改邮箱")
//    @PostMapping(value = "/users/updateEmail/{code}")
//    public ResponseEntity updateEmail(@PathVariable String code,@RequestBody User user){
//        UserDetails userDetails = SecurityContextHolder.getUserDetails();
//        if(!userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getPassword()))){
//            throw new BadRequestException("密码错误");
//        }
////        VerificationCode verificationCode = new VerificationCode(code, ElAdminConstant.RESET_MAIL,"email",user.getEmail());
////        verificationCodeService.validated(verificationCode);
//        userService.updateEmail(userDetails.getUsername(),user.getEmail());
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    /**
//     * 根据辖区查找所属辖区及子辖区的用户
//     * @param user
//     * @return
//     */
//    @ApiOperation("根据辖区查找所属辖区及子辖区的用户")
//    @GetMapping(value = "/get_user_list")
//    public ResponseEntity getUsersByJurisdiction(@ModelAttribute("user") User user){
//        List<String> jurisdictions = new ArrayList<>();
//        List<Dept> deptList = deptService.findByUserRole(user);
//        for (int i = 0; i < deptList.size(); i++) {
//            Dept dept = deptList.get(i);
//            jurisdictions.add(String.valueOf(dept.getId()));
//        }
//        return new ResponseEntity(userService.queryListByjurisdictions(jurisdictions), HttpStatus.OK);
//    }
}
