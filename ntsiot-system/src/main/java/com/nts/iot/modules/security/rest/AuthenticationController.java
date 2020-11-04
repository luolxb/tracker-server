package com.nts.iot.modules.security.rest;

import com.nts.iot.aop.log.Log;
import com.nts.iot.constant.ConstantClass;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.security.config.SecurityProperties;
import com.nts.iot.modules.security.security.AuthenticationInfo;
import com.nts.iot.modules.security.security.AuthorizationUser;
import com.nts.iot.modules.security.security.JwtUser;
import com.nts.iot.modules.security.utils.JwtTokenUtil;
import com.nts.iot.util.RestResponse;
import com.nts.iot.utils.EncryptUtils;
import com.nts.iot.utils.RedisUtil;
import com.nts.iot.utils.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jie
 * @date 2018-11-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("auth")
@Api(tags = "授权、根据token获取用户详细信息")
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    private SecurityProperties properties;


    /**
     * 登录授权
     *
     * @param authorizationUser
     * @return
     */
    @Log("用户登录,User login")
    @ApiOperation(value = "用户登录")
    @PostMapping(value = "${jwt.auth.path}")
    public ResponseEntity login(@Validated @RequestBody AuthorizationUser authorizationUser, HttpServletRequest request) {

        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authorizationUser.getUsername());

        if (!jwtUser.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getPassword()))) {
            throw new AccountExpiredException("密码错误,wrong password");
        }

        if (!jwtUser.isEnabled()) {
            throw new AccountExpiredException("账号已停用请联系管理员,The account has been disabled, please contact the administrator");
        }

        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);
        if (redisUtil.hasKey(tokenHeader + token)) {
            throw new AccountExpiredException("账号已登录,Account is logged in");
        }
        ;
        StringBuffer sb = new StringBuffer();
        sb.append(jwtUser.getUsername()).append(":").append(":").append(jwtUser.getPhone());

        redisUtil.set(tokenHeader + token, (String) (sb.toString()), 1000000);

        // 返回 token
        return ResponseEntity.ok(new AuthenticationInfo(token, jwtUser));
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "${jwt.auth.account}")
    public ResponseEntity getUserInfo() {
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(jwtUser);
    }


//    @ApiOperation("获取验证码")
//    @GetMapping(value = "/code")
//    public ResponseEntity<Object> getCode(){
//        // 算术类型 https://gitee.com/whvse/EasyCaptcha
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
//        // 几位数运算，默认是两位
//        captcha.setLen(2);
//        // 获取运算的结果
//        String result = captcha.text();
//        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
//        // 保存
//        redisUtil.set(uuid, result, 2, TimeUnit.MINUTES);
//        // 验证码信息
//        Map<String,Object> imgResult = new HashMap<String,Object>(2){{
//            put("img", captcha.toBase64());
//            put("uuid", uuid);
//        }};
//        return ResponseEntity.ok(imgResult);
//    }

    @ApiOperation("退出登录")
    @PostMapping(value = "/logout")
    public RestResponse logout(@RequestParam String token) {
        String username = null;
        try {
            username = jwtTokenUtil.getUsernameFromToken(token);
            String key = RedisKey.USER_INFO_KEY + username;
            redisUtil.deleteByKey(key);
        } catch (Exception e) {
            log.error("登出异常：", e);
        }

        return RestResponse.success();
    }

    @Log("切换语言")
    @ApiOperation("切换语言")
    @ApiImplicitParam(name = "language", value = "语言【中文(zh_cn),英文(en)】", dataType = "String", paramType = "path", required = true)
    @GetMapping("/switchLanguage/{language}")
    public RestResponse switchLanguage(@PathVariable String language) {

        System.out.println(language);
        ConstantClass.switchlanguage = language;
        return RestResponse.success();

    }
}
