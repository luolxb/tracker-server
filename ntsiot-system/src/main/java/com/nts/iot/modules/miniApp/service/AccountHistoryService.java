package com.nts.iot.modules.miniApp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.model.AccountHistory;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzm
 * @since 2018-03-05
 */
public interface AccountHistoryService extends IService<AccountHistory> {

    /**
     * 查询所有
     * @param pageable 分页
     * @param userName 用户名
     * @param jurisdiction 辖区号
     * @return
     */
    Object queryAll(Pageable pageable, String userName, Long jurisdiction);

    /**
     * <p>
     * 支付操作（登录流水账号，更新订单表）
     * </p>
     *
     * @param userId userId
     * @param orderId orderId
     * @param weiXinNo weiXinNo
     * @param timeEnd timeEnd
     * @param payTotal payTotal
     */

    void paymentByWx(Long userId, String orderId, String weiXinNo, String timeEnd, int payTotal);



    /**
     * <p>
     * 游客支付操作（登录流水账号，更新订单表）
     * </p>
     *
     * @param outTradeNo outTradeNo
     * @param weiXinNo weiXinNo
     * @param payTotal payTotal
     */
    void paymentByCustomer(String outTradeNo, String weiXinNo, int payTotal);

}
