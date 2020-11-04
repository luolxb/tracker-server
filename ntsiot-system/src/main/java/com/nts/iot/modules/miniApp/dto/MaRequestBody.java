package com.nts.iot.modules.miniApp.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MaRequestBody implements Serializable {

    private String userId;

    private String username;

    private String code;

    private String appId;

    private String openId;

    // 手机号
    private String phoneNum;

    // 验证码
    private String verificationCode;

    // user 密码
    private String password;

    private String sessionKey;

    private String encryptedData;

    private String ivStr;

    private String bikeBarcode;

    private String lockBarcode;

    // 经度
    private String longitude;

    // 纬度
    private String latitude;

    // 辖区
    private String deptId;

    // 订单号list
    private String orderIds;

    // 开始日期 yyyy-mm-dd
    private String startTime;

    // 结束日期 yyyy-mm-dd
    private String endTime ;

    // 充值金额
    private String rechargeAmount;

    // 通信加密时间戳
    private String time;

    // 通信加密公钥
    private String str;

    // 通信加密签名
    private String signedStr;

    // 实际花费钱数
    private String money;

    /**
     * 图片urlList
     */
    private List<String> sourceData;

    // 音频urlList
    private String frequency;

    // 任务id
    private String taskId;

    // 子任务id
    private String subTaskId;

    // testarea 内容
    private String content;

    // 消息id
    private String messageId;

    //mauser头像
    private String avatar;
}
