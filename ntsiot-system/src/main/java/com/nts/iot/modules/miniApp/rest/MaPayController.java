package com.nts.iot.modules.miniApp.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.MiniAppConstants;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.nts.iot.modules.miniApp.common.WeixinPayConstants;
import com.nts.iot.modules.miniApp.config.WxPayConfiguration;
import com.nts.iot.modules.miniApp.message.Result;
import com.nts.iot.modules.miniApp.message.ResultCode;
import com.nts.iot.modules.miniApp.model.Account;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.model.RechargeRecord;
import com.nts.iot.modules.miniApp.service.AccountService;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.miniApp.service.RechargeRecordService;
import com.nts.iot.modules.miniApp.util.DateUtils;
import com.nts.iot.modules.miniApp.util.SHA1Util;
import com.nts.iot.modules.miniApp.util.ToolUtil;
import com.nts.iot.modules.miniApp.dto.MaRequestBody;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("ma")
public class MaPayController {


    @Autowired
    SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    AccountService accountService;

    @Autowired
    RechargeRecordService rechargeRecordService;

    @Autowired
    SHA1Util sHA1Util;

    @Autowired
    WxPayConfiguration wxPayConfiguration;

    @Autowired
    WxPayService wxPayService;

    @Autowired
    MaUserService userService;

    @Value("${rechargeNotifyUrl}")
    private String rechargeNotifyUrl;

    @Value("${wechat.miniapp.appId}")
    private String maAppId;



    /* 支付相关代码 START */

    /**
     * 充值付款
     *
     * @param request     request
     * @param requestBody 请求对象
     * @return 操作结果
     */
    @PostMapping("recharge")
    public Result recharge(HttpServletRequest request, @RequestBody MaRequestBody requestBody) throws WxPayException {
        Result result = new Result();
        Map<String, String> data;
        // 参数非空校验
        if (StringUtils.isEmpty(requestBody.getOpenId())) {
            // 设置结果集
            return setResult(ResultCode.PARAM_NULL, "openId不能为空！", null);
        }
        if (StringUtils.isEmpty(requestBody.getRechargeAmount())) {
            // 设置结果集
            return setResult(ResultCode.PARAM_NULL, "充值金额不能为空！", null);
        }
        // 判断unionId是否能查询到唯一的用户信息
        MaUser user = userService.getUserByWxId(requestBody.getOpenId());
        if (user == null) {
            // 设置结果集
            return setResult(ResultCode.UNIONID_WRONG, "unionId不正确！", null);
        }
        // TODO 通信加密check
        String time = requestBody.getTime();
        String code = requestBody.getOpenId();
        String publicKey = requestBody.getStr();
        String signed = requestBody.getSignedStr();
//        Result checkSha1Result = checkSha1handle(signed, code, time, publicKey);
//        if (checkSha1Result.getCode() != ResultCode.SUCCESS.code()) {
//            return checkSha1Result;
//        }
        String rechargeAmount = requestBody.getRechargeAmount();
        // 充值金额
        BigDecimal recharge = new BigDecimal(rechargeAmount);
        // 赠送金额
        //BigDecimal give = getGive(recharge);
        // 获取终端IP地址
        String ip = ToolUtil.getIpAddress(request);
        // 商户充值记录编号
        String recordNo = ToolUtil.getRandomName();
        // 登录充值记录表
        RechargeRecord rechargeRecord = new RechargeRecord();
        // 商户充值记录编号
        rechargeRecord.setOutRechargeId(recordNo);
        // 用户编号
        rechargeRecord.setUserId(user.getId());
        // 流水编号
        // 充值金额
        rechargeRecord.setChargesCount(recharge);
        // 赠送金额
        rechargeRecord.setLargessCount(null);
        // 实际充值
        rechargeRecord.setActualCount(recharge);
        // 状态(未支付)
        rechargeRecord.setStatus(MiniAppConstants.STATUS_RECORD_UNPAID);
        // 登录
        rechargeRecordService.save(rechargeRecord);
        // 附近数据
        String attach = "rechargeAmount:" + rechargeAmount + ";";
        // 调用微信统一下单接口参数设置
        WxPayUnifiedOrderRequest orderRequest =
                setPayRequest("智能加油站-充值", "充值" + rechargeAmount + "元", attach, recordNo, rechargeAmount, ip,
                        rechargeNotifyUrl, user.getOpenid());
        // 调用统一下单接口
        WxPayUnifiedOrderResult wxPayResult = wxPayService.unifiedOrder(orderRequest);
        // 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        if (WeixinPayConstants.RETURN_CODE_SUCCESS.equals(wxPayResult.getReturnCode())) {
            if (WeixinPayConstants.RETURN_CODE_SUCCESS.equals(wxPayResult.getResultCode())) {
                // 设置发起微信支付参数
                data = setWxData(wxPayResult.getPrepayId());
                // 设置结果集
                return setResult(ResultCode.WX_PAYORDER_SUCCESS, "【账户充值】调用统一下单接口成功！", data);
            } else if (WeixinPayConstants.RETURN_CODE_FAIL.equals(wxPayResult.getResultCode())) {
                // 设置结果集
                return setResult(ResultCode.FAIL, wxPayResult.getErrCodeDes(), wxPayResult.getErrCode());
            }
        } else if (WeixinPayConstants.RETURN_CODE_FAIL.equals(wxPayResult.getReturnCode())) {
            // 设置结果集
            return setResult(ResultCode.FAIL, wxPayResult.getReturnMsg(), null);
        }
        return result;
    }

    /**
     * 充值账户，支付通知回调，更新数据
     *
     * @param request request
     * @throws WxPayException WxPayException
     */
    @ResponseBody
    @RequestMapping("rechargeNotify")
    public void rechargeNotify(HttpServletRequest request) throws WxPayException, IOException {
        String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
        // 解析支付结果通知
        WxPayOrderNotifyResult wxResult = wxPayService.parseOrderNotifyResult(xmlResult);
        // openId
        String openId = wxResult.getOpenid();
        // 判断openId是否能查询到唯一的用户信息
        MaUser user = userService.getUserByWxId(openId);
        if (user == null) {
            // 向WebSocket发生消息
            //webSocketManager.getWebSocket(openId).sendToUser("openId不正确！" + "|" + openId);
            simpMessageSendingOperations.convertAndSendToUser(openId, "/payment", "openId不正确！" + "|" + openId);
            // 返回
            return;
        }
        // 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        if (!WeixinPayConstants.RETURN_CODE_SUCCESS.equals(wxResult.getReturnCode())) {
            // 向WebSocket发生消息
            //webSocketManager.getWebSocket(openId).sendToUser(wxResult.getReturnMsg() + "|" + openId);
            simpMessageSendingOperations.convertAndSendToUser(openId, "/payment", wxResult.getReturnMsg() + "|" + openId);
            // 返回
            return;
        }
        if (!WeixinPayConstants.RETURN_CODE_SUCCESS.equals(wxResult.getResultCode())) {
            // 向WebSocket发生消息
            //webSocketManager.getWebSocket(openId).sendToUser(wxResult.getErrCodeDes() + "|" + openId);
            simpMessageSendingOperations.convertAndSendToUser(openId, "/payment", wxResult.getErrCodeDes() + "|" + openId);
            // 返回
            return;
        }
        // 商户充值记录编号
        String outRechargeNo = wxResult.getOutTradeNo();
        // 查询充值记录表
        List<RechargeRecord> rechargeRecords = rechargeRecordService.list(new QueryWrapper<RechargeRecord>().eq("out_recharge_id", outRechargeNo));
        if (rechargeRecords == null || rechargeRecords.size() == 0) {
            // 向WebSocket发生消息
            simpMessageSendingOperations.convertAndSendToUser(openId, "/payment", "未查到对应充值记录！" + "|" + openId);
            // 返回
            return;
        }
        RechargeRecord rechargeRecord = rechargeRecords.get(0);
        // 因为回调是异步了，为了控制多次调用此方法更新数据，在此做下控制。
        if (MiniAppConstants.STATUS_PAID.equals(rechargeRecord.getStatus())) {
            System.err.println("*********************************************************************************************");
            System.err.println("-------------------------------------充值记录状态已经更新！！！！！！！！");
            System.err.println("*********************************************************************************************");
            // 返回
            return;
        }
        // 支付金额
        int payTotal = wxResult.getTotalFee();
        // 商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。
        if (!Objects.equals(payTotal, BaseWxPayRequest.yuanToFen(String.valueOf(rechargeRecord.getActualCount())))) {
            System.err.println("*********************************************************************************************");
            System.err.println("-------------------------------------返回的订单金额与商户侧的订单金额不一致！！！！！！！！");
            System.err.println("*********************************************************************************************");
            // 向WebSocket发生消息
            //webSocketManager.getWebSocket(openId).sendToUser("返回的订单金额与商户侧的订单金额不一致！" + "|" + openId);
            simpMessageSendingOperations.convertAndSendToUser(openId, "/payment", "返回的订单金额与商户侧的订单金额不一致！" + "|" + openId);

            // 回调失败，充值记录作废【恶意的修改充值金额或者是签名不对】
            rechargeRecord.setStatus(MiniAppConstants.STATUS_RECORD_INVALID);
            // 更新充值记录状态
            rechargeRecordService.updateById(rechargeRecord);
            // 返回
            return;
        }
        String sign = wxResult.getSign();
        // 数据加密
        Map<String, String> parameters = setParameters(wxResult);
        // 生成签名
        String endSign = SignUtils.createSign(parameters, WxPayConstants.SignType.MD5, wxPayConfiguration.config().getMchKey(), false);
        // TODO 验证签名
//        if (!sign.equals(endSign)) {
//            System.err.println("*********************************************************************************************");
//            System.err.println("-------------------------------------充值回调签名验证失败！！！！！！！！");
//            System.err.println("*********************************************************************************************");
//            // 向WebSocket发生消息
//            webSocketManager.getWebSocket(openId).sendToUser("签名验证失败！" + "|" + openId);
//
//            // 回调失败，充值记录作废【恶意的修改充值金额或者是签名不对】
//            rechargeRecord.setStatus(MiniAppConstants.STATUS_RECORD_INVALID);
//            // 更新充值记录状态
//            rechargeRecordService.updateById(rechargeRecord);
//            // 返回
//            return;
//        }
        // 微信流水号
        String weiXinNo = wxResult.getTransactionId();
        // 支付完成时间
        String timeEnd = wxResult.getTimeEnd();
        // 附加数据
        String attach = wxResult.getAttach();
        // 微信支付成功之后，获取信息，调用service，更新充值记录表，登录消费流水表，更新资金账户表
        rechargeRecordService.recharge(rechargeRecord, user, attach, weiXinNo, timeEnd);
        // 向WebSocket发生消息
        //webSocketManager.getWebSocket(openId).sendToUser("回调更新数据成功！" + "|" + openId);

        simpMessageSendingOperations.convertAndSendToUser(openId, "/payment", "回调更新数据成功！" + "|" + openId);

        System.out.println("*********************************************************************************************");
        System.out.println("-------------------------------------充值记录更新成功！！！！！！！！！！！！！！！！！！！！！！！！");
        System.out.println("*********************************************************************************************");
    }


    /**
     * 获取充值记录的信息
     *
     * @param requestBody requestBody
     * @return result result
     */
    @PostMapping("getRechargeRecord")
    public Result getRechargeRecord(@RequestBody MaRequestBody requestBody) {
        Result result = new Result();
        Map<String, Object> data = new HashMap<>();
        // 参数非空校验
        if (StringUtils.isEmpty(requestBody.getOpenId())) {
            // 设置结果集
            return setResult(ResultCode.PARAM_NULL, "openId不能为空！", null);
        }
        // 判断unionId是否能查询到唯一的用户信息
        MaUser user = userService.getUserByWxId(requestBody.getOpenId());
        if (user == null) {
            // 设置结果集
            return setResult(ResultCode.UNIONID_WRONG, "openId不正确！", null);
        }
        // 查询充值记录
        List<RechargeRecord> rechargeRecords = rechargeRecordService.list(new QueryWrapper<RechargeRecord>()
                .eq("user_id", user.getId())
                .eq("status", MiniAppConstants.STATUS_RECORD_PAID)
                .orderByDesc("charges_time"));
        List<Map<String, Object>> records = new ArrayList<>();
        rechargeRecords.forEach(e -> {
            Map<String, Object> re = new HashMap<>();
            // 充值金额
            re.put("charge", e.getChargesCount());
            // 时间转换
            re.put("payTime", DateUtils.longToDate(e.getChargesTime()));
            // 赠送金额
            re.put("largess", e.getLargessCount());
            records.add(re);
        });
        data.put("records", records);
        // 查询用户余额
        List<Account> accountList = accountService.list(new QueryWrapper<Account>().eq("user_id", user.getId()));
        if (accountList != null && accountList.size() > 0) {
            // 获取用户余额
            data.put("balance", accountList.get(0).getBalance());
        }
        // 设置结果集code
        result.setCode(ResultCode.SUCCESS);
        // 设置返回信息
        result.setMessage("获取充值记录成功！");
        // 设置结果集数据
        result.setData(data);
        // 返回结果
        return result;
    }

    /* 支付相关代码 END */

    /**
     * 设置result
     *
     * @param code   结果代码
     * @param msg    结果信息
     * @param object 结果数据
     * @return result 结果集
     */
    private Result setResult(ResultCode code, String msg, Object object) {
        Result result = new Result();
        // 设置结果集code
        result.setCode(code);
        // 设置返回信息
        result.setMessage(msg);
        // 设置结果集数据
        result.setData(object);
        // 返回
        return result;
    }

    /**
     * 验证SHA1
     *
     * @param signed    客户端生成的SHA1
     * @param code      人机端CODE/小程序端u?id
     * @param timestamp 时间戳
     * @param publicKey 公钥
     * @return 是否验证通过
     */

    protected Result checkSha1handle(String signed, String code, String timestamp, String publicKey) {
        Integer state = sHA1Util.checkSign(signed, code, timestamp, publicKey);
        if (code == null || timestamp == null || publicKey == null) {
            return setResult(ResultCode.SIGN_FAIL, "非法访问！", null);
        } else {
            /* 0 成功，1 重复提交 ，2 操作失败*/
            if (state == 0) {
                return setResult(ResultCode.SUCCESS, "操作成功！", null);
            } else if (state == 1) {
                return setResult(ResultCode.SIGN_FAIL, "重复提交！", null);
            } else {
                return setResult(ResultCode.SIGN_FAIL, "操作失败！", null);
            }
        }
    }


    /**
     * 设置通知签名参数
     *
     * @param wxResult wxResult
     * @return parameters parameters
     */
    protected Map<String, String> setParameters(WxPayOrderNotifyResult wxResult) {
        Map<String, String> parameters = new HashMap<>();
        // 数据加密
        //应用ID
        parameters.put("appid", wxResult.getAppid());
        //商家数据包
        parameters.put("attach", wxResult.getAttach());
        //付款银行
        parameters.put("bank_type", wxResult.getBankType());
        //现金支付金额
        parameters.put("cash_fee", Integer.toString(wxResult.getCashFee()));
        parameters.put("device_info", wxResult.getDeviceInfo());
        //货币种类
        parameters.put("fee_type", wxResult.getFeeType());
        //是否关注公众账号
        parameters.put("is_subscribe", wxResult.getIsSubscribe());
        //商户号
        parameters.put("mch_id", wxResult.getMchId());
        //随机字符串
        parameters.put("nonce_str", wxResult.getNonceStr());
        //用户标识
        parameters.put("openid", wxResult.getOpenid());
        // 商户订单号
        parameters.put("out_trade_no", wxResult.getOutTradeNo());
        // 业务结果
        parameters.put("result_code", wxResult.getResultCode());
        // SUCCESS/FAIL
        parameters.put("return_code", wxResult.getReturnCode());
        // 支付完成时间
        parameters.put("time_end", wxResult.getTimeEnd());
        // 获取订单金额
        parameters.put("total_fee", Integer.toString(wxResult.getTotalFee()));
        //交易类型
        parameters.put("trade_type", wxResult.getTradeType());
        //微信支付订单号
        parameters.put("transaction_id", wxResult.getTransactionId());
        return parameters;
    }

    /**
     * 设置WxPayUnifiedOrderRequest
     *
     * @param body       商品描述
     * @param detail     商品详情
     * @param attach     附加数据
     * @param outTradeNo 商户订单号
     * @param amountNum  订单金额
     * @param ip         终端ip地址
     * @param url        通知地址
     * @param openid     用户标识
     * @return orderResquest 请求参数
     */
    private WxPayUnifiedOrderRequest setPayRequest(String body, String detail, String attach, String outTradeNo,
                                                   String amountNum, String ip, String url, String openid) {
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        // 设备号
        orderRequest.setDeviceInfo("WEB");
        // 随机字符串（默认采用时间戳为随机字符串，可以不设置）
        // 签名(可以根据传入参数自动生成签名)
        // 签名类型
        orderRequest.setSignType(WxPayConstants.SignType.MD5);
        // 商品描述
        orderRequest.setBody(body);
        // 商品详情
        orderRequest.setDetail(detail);
        // 附加数据
        orderRequest.setAttach(attach);
        // 商户订单号
        orderRequest.setOutTradeNo(outTradeNo);
        // 标价币种
        orderRequest.setFeeType("CNY");
        // 标价金额 · 元转成分
        orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(amountNum));
        // 终端IP
        orderRequest.setSpbillCreateIp(ip);
        // 交易起始时间
        orderRequest.setTimeStart(DateUtils.getAllTime());
        // 交易结束时间
        orderRequest.setTimeExpire(DateUtils.getTimeExpire());
        // 订单优惠标记
        orderRequest.setGoodsTag(null);
        // 通知地址
        orderRequest.setNotifyUrl(url);
        // 交易类型
        orderRequest.setTradeType(WxPayConstants.TradeType.JSAPI);
        // 商品ID
        orderRequest.setProductId(null);
        // 指定支付方式（上传此参数no_credit--可限制用户不能使用信用卡支付）
        orderRequest.setLimitPay(WxPayConstants.LimitPay.NO_CREDIT);
        // 用户标识
        orderRequest.setOpenid(openid);
        return orderRequest;
    }

    /**
     * 设置微信支付参数
     *
     * @param prepayId 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
     * @return data 请求参数
     */
    private Map<String, String> setWxData(String prepayId) {
        Map<String, String> data = new HashMap<>();
        // 设置发起微信支付参数
        String timeStamp = System.currentTimeMillis() / 1000 + "";
        String nonceStr = System.currentTimeMillis() + "";
        data.put("timeStamp", timeStamp);
        data.put("nonceStr", nonceStr);
        data.put("package", "prepay_id=" + prepayId);
        // 将组合数据再次签名
        // 传送的参数
        Map<String, String> signParam = new HashMap<>();
        // TODO  此处需要传入正确的APPID
        signParam.put("appId", maAppId);
        signParam.put("timeStamp", data.get("timeStamp"));
        signParam.put("nonceStr", data.get("nonceStr"));
        signParam.put("package", data.get("package"));
        signParam.put("signType", "MD5");
        // 生成签名
        String signAgain = SignUtils.createSign(signParam, WxPayConstants.SignType.MD5,
                wxPayConfiguration.config().getMchKey(), false);
        data.put("paySign", signAgain);
        return data;
    }


}
