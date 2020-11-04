package com.nts.iot.modules.miniApp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.MiniAppConstants;
import com.nts.iot.modules.miniApp.dao.AccountMapper;
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
import java.util.Date;
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
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    AccountHistoryService accountHistoryService;


    /**
     * 查询所有
     *
     * @param money
     * @param pageable
     * @return
     */
    @Override
    public Object queryAll(Pageable pageable, String money, Long jurisdiction) {
        Page<Account> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<Account> pageResult = baseMapper.queryAll(page, money, jurisdiction);
        return PageUtil.toPage(pageResult);
    }


    /**
     * <p>
     * 支付操作（更新资金账号表、登录流水账号，更新订单表）
     * </p>
     *
     * @param orderId orderId
     * @param amount amount
     * @param account account
     */
    @Override
    public void paymentBybalance(Long orderId,BigDecimal amount,Account account) {
        // 更新资金账户表
        // 设置当前金额（当前金额 = 当前金额 - 支付余额）
        account.setBalance(account.getBalance().subtract(amount));
        // 设置消费总额（消费总金额 = 消费总金额 + 支付余额）
        account.setTotalExpend(account.getTotalExpend().add(amount));
        // 更新
        this.updateById(account);
        // 登录消费流水表
        AccountHistory accountHistory = new AccountHistory();
        // 账户编号
        accountHistory.setAccountId(account.getId());
        // 账户余额
        accountHistory.setBalance(account.getBalance());
        // 余额支付
        accountHistory.setBalanceAmount(amount);
        // 微信支付
        // 微信流水号
        // 创建时间
        accountHistory.setCreateTime(System.currentTimeMillis());
        // 类型（0:退款; 1:订单支付; 2:充值）
        accountHistory.setType(MiniAppConstants.TYPE_ORDER_PAY);
        accountHistoryService.save(accountHistory);
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
    public void updateByWxBa(Long userId, String orderId, String weiXinNo, String timeEnd, int payTotal) {
        // 查询用户账户信息
        List<Account> accounts= this.list(new QueryWrapper<Account>().eq("user_id",userId));
        Account account = new Account();
        Long accountId = 0L;
        BigDecimal balance = new BigDecimal(0);
        if(accounts != null && accounts.size() > 0){
            account = accounts.get(0);
            accountId = accounts.get(0).getId();
            balance = accounts.get(0).getBalance();
        }
        // 登录消费流水表
        AccountHistory accountHistory = new AccountHistory();
        // 账户编号
        accountHistory.setAccountId(accountId);
        // 账户余额
        accountHistory.setBalance(new BigDecimal(0));
        // 余额支付
        accountHistory.setBalanceAmount(balance);
        // 将分转化为元
        BigDecimal decimal = BigDecimal.valueOf(payTotal).divide(new BigDecimal(100));
        // 微信支付
        accountHistory.setWexinAmount(decimal);
        // 微信流水号
        accountHistory.setWeixinNo(weiXinNo);
        // 创建时间
        accountHistory.setCreateTime(new Date().getTime());
        // 类型（0:退款; 1:订单支付; 2:充值）
        accountHistory.setType(MiniAppConstants.TYPE_ORDER_PAY);
        // 登录
        accountHistoryService.save(accountHistory);

        // 更新资金账户表
        // 设置当前金额（0）
        account.setBalance(new BigDecimal(0));
        // 设置消费总额（消费总金额 = 消费总金额 + 当前金额）
        account.setTotalExpend(account.getTotalExpend().add(balance));
        // 更新
        this.updateById(account);
    }


}
