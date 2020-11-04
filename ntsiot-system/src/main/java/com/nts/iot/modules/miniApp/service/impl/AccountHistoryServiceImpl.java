package com.nts.iot.modules.miniApp.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.MiniAppConstants;
import com.nts.iot.modules.miniApp.dao.AccountHistoryMapper;
import com.nts.iot.modules.miniApp.model.Account;
import com.nts.iot.modules.miniApp.model.AccountHistory;
import com.nts.iot.modules.miniApp.service.AccountHistoryService;
import com.nts.iot.modules.miniApp.service.AccountService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzm
 * @since 2018-03-05
 */
@Service
@Transactional
public class AccountHistoryServiceImpl extends ServiceImpl<AccountHistoryMapper, AccountHistory> implements AccountHistoryService {


    @Autowired
    AccountService accountService;

    /**
     * 查询所有
     *
     * @param money
     * @param pageable
     * @return
     */
    @Override
    public Object queryAll(Pageable pageable, String money, Long jurisdiction) {
        Page<AccountHistory> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<AccountHistory> pageResult = baseMapper.queryAll(page, money, jurisdiction);
        return PageUtil.toPage(pageResult);
    }

    /**
     * <p>
     * 支付操作（登录流水账号，更新订单表）
     * </p>
     *
     * @param userId   userId
     * @param orderId  orderId
     * @param weiXinNo weiXinNo
     * @param timeEnd  timeEnd
     * @param payTotal payTotal
     */
    @Override
    public void paymentByWx(Long userId, String orderId, String weiXinNo, String timeEnd, int payTotal) {
        // 查询用户账户信息
        List<Account> accounts= accountService.list(new QueryWrapper<Account>().eq("user_id",userId));
        Long accountId = 0L;
        BigDecimal balance = new BigDecimal(0);
        if(accounts != null && accounts.size() > 0){
            accountId = accounts.get(0).getId();
            balance = accounts.get(0).getBalance();
        }
        // 登录消费流水表
        AccountHistory accountHistory = new AccountHistory();
        // 账户编号
        accountHistory.setAccountId(accountId);
        // 账户余额
        accountHistory.setBalance(balance);
        // 余额支付
        // 将分转化为元
        BigDecimal decimal = BigDecimal.valueOf(payTotal).divide(new BigDecimal(100));
        // 微信支付
        accountHistory.setWexinAmount(decimal);
        // 微信流水号
        accountHistory.setWeixinNo(weiXinNo);
        // 创建时间
        accountHistory.setCreateTime(System.currentTimeMillis());
        // 类型（0:退款; 1:订单支付; 2:充值）
        accountHistory.setType(MiniAppConstants.TYPE_ORDER_PAY);
        // 登录
        this.save(accountHistory);
        // TODO 更新订单表
    }

    /**
     * <p>
     * 游客支付操作（登录流水账号，更新订单表）
     * </p>
     *
     * @param outTradeNo  outTradeNo
     * @param weiXinNo weiXinNo
     * @param payTotal payTotal
     */
    @Override
    public void paymentByCustomer(String outTradeNo, String weiXinNo, int payTotal) {
        // 登录消费流水表
        AccountHistory accountHistory = new AccountHistory();
        // 账户编号（临时未注册用户设置为空）
        accountHistory.setAccountId(null);
        // 账户余额
        accountHistory.setBalance(new BigDecimal(0));
        // 将分转化为元
        BigDecimal decimal = BigDecimal.valueOf(payTotal).divide(new BigDecimal(100));
        // 微信支付
        accountHistory.setWexinAmount(decimal);
        // 微信流水号
        accountHistory.setWeixinNo(weiXinNo);
        // 创建时间
        accountHistory.setCreateTime(System.currentTimeMillis());
        // 类型（0:退款; 1:订单支付; 2:充值）
        accountHistory.setType(MiniAppConstants.TYPE_ORDER_PAY);
        // 更新流水表
        this.save(accountHistory);
        // 流水编号
        // TODO 更新订单表
    }


}
