package com.nts.iot.modules.miniApp.common;

/**
 * <pre>
 * 微信支付相关常量
 * </pre>
 * @author zzm
 */
public class WeixinPayConstants {


  public static final String RETURN_CODE_SUCCESS = "SUCCESS";

  public static final String RETURN_CODE_FAILURE = "FAILURE";


  public static final String RETURN_CODE_FAIL = "FAIL";

  /**
   * 支付类型 （1：完全使用余额支付 , 2：完全使用微信支付,3：部分余额支付）
   */
  public static final String PAY_FULL_BALANCE  = "1";
  public static final String PAY_FULL_WEIXIN  = "2";
  public static final String PAY_PART_BALANCE  = "3";

}
