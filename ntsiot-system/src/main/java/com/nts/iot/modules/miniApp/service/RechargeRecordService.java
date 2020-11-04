package com.nts.iot.modules.miniApp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.model.RechargeRecord;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzm
 * @since 2018-03-05
 */
public interface RechargeRecordService extends IService<RechargeRecord> {

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
     * 充值操作（更新充值记录表，登录消费流水表，更新资金账户表）
     * </p>
     * @param rechargeRecord rechargeRecord
     * @param user user
     * @param attach attach
     * @param transactionId transactionId
     * @param timeEnd timeEnd
     *
     */
    void recharge(RechargeRecord rechargeRecord, MaUser user, String attach, String transactionId, String timeEnd);

}
