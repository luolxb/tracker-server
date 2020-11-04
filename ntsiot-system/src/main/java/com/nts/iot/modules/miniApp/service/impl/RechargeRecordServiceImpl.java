package com.nts.iot.modules.miniApp.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.MiniAppConstants;
import com.nts.iot.modules.miniApp.dao.RechargeRecordMapper;
import com.nts.iot.modules.miniApp.model.Account;
import com.nts.iot.modules.miniApp.model.AccountHistory;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.model.RechargeRecord;
import com.nts.iot.modules.miniApp.service.AccountHistoryService;
import com.nts.iot.modules.miniApp.service.AccountService;
import com.nts.iot.modules.miniApp.service.RechargeRecordService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class RechargeRecordServiceImpl extends ServiceImpl<RechargeRecordMapper, RechargeRecord> implements RechargeRecordService {

    @Autowired
    RechargeRecordService rechargeRecordService;

    @Autowired
    AccountHistoryService accountHistoryService;

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
        Page<RechargeRecord> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<RechargeRecord> pageResult = baseMapper.queryAll(page, money, jurisdiction);
        return PageUtil.toPage(pageResult);
    }

    /**
     * <p>
     * 充值操作（更新充值记录表，登录消费流水表，更新资金账户表）
     * </p>
     *  @param rechargeRecord rechargeRecord
     *  @param user user
     *  @param attach attach
     *  @param timeEnd timeEnd
     */
    @Override
    public void recharge(RechargeRecord rechargeRecord , MaUser user , String attach , String transactionId, String timeEnd) {


        // 更新资金账户表
        List<Account> accounts = accountService.list(new QueryWrapper<Account>().eq("user_id",user.getId()));
        Account account = new Account();
        if(accounts != null && accounts.size() > 0){
            account = accounts.get(0);
        }

        //附加数据拆分
        String[] att = attach.split(";");
        String[] att1 = att[0].split(":");
        String[] att2 = att[1].split(":");
        // 充值金额
        BigDecimal rechargeAmount = new BigDecimal(att1[1]);
        // 赠送金额
        BigDecimal give = new BigDecimal(att2[1]);

        // 充值金额（充值金额 + 赠送金额）
        BigDecimal amount = rechargeAmount.add(give);

        account.setBalance(account.getBalance().add(amount));

        // 更新
        accountService.updateById(account);


        // 登录消费流水表
        AccountHistory accountHistory = new AccountHistory();
        // 账户编号
        accountHistory.setAccountId(account.getId());
        // 账户余额
        accountHistory.setBalance(account.getBalance());
        // 余额支付
        // 微信支付
        accountHistory.setWexinAmount(rechargeRecord.getChargesCount());
        // 微信流水号
        accountHistory.setWeixinNo(transactionId);
        // 创建时间
        accountHistory.setCreateTime(new Date().getTime());
        // 类型
        accountHistory.setType(MiniAppConstants.TYPE_PAY);
        // 登录
        accountHistoryService.save(accountHistory);


        // 更新充值记录表
        //  状态
        rechargeRecord.setStatus(MiniAppConstants.STATUS_RECORD_PAID);
        // 流水编号
        rechargeRecord.setHistoryId(accountHistory.getId());
        // 充值时间
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date date = sdf.parse(timeEnd);
            rechargeRecord.setChargesTime(date.getTime());
        }catch (ParseException e){
            e.printStackTrace();
            // 当前时间
            rechargeRecord.setChargesTime(new Date().getTime());
        }
        // 更新
        this.updateById(rechargeRecord);

    }
}
