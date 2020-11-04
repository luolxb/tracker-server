package com.nts.iot.modules.miniApp.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.model.Account;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzm
 * @since 2018-03-05
 */
public interface AccountService extends IService<Account> {

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
     * 支付操作（更新资金账号表、登录流水账号，更新订单表）
     * </p>
     *
     * @param orderId orderId
     * @param amount amount
     * @param account account
     */

    void paymentBybalance(Long orderId, BigDecimal amount, Account account);

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

    void updateByWxBa(Long userId, String orderId, String weiXinNo, String timeEnd, int payTotal);

}
