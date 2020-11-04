package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.BorrowingReturningMapper;
import com.nts.iot.modules.system.dto.BorrowingReturningDto;
import com.nts.iot.modules.system.dto.LoanCountDto;
import com.nts.iot.modules.system.model.ArticleNumber;
import com.nts.iot.modules.system.model.BorrowingReturning;
import com.nts.iot.modules.system.service.ArticleNumberService;
import com.nts.iot.modules.system.service.ArticleService;
import com.nts.iot.modules.system.service.BorrowingReturningService;
import com.nts.iot.modules.system.service.MessageService;
import com.nts.iot.util.ExcelExportUtil;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class BorrowingReturningServiceImpl extends ServiceImpl<BorrowingReturningMapper, BorrowingReturning> implements BorrowingReturningService {

    @Autowired
    BorrowingReturningService borrowingReturningService;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleNumberService articleNumberService;

    @Autowired
    MessageService messageService;


    @Override
    public Object queryAll(Pageable pageable, String username, List<Long> jurisdiction) {
        Page<BorrowingReturning> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<BorrowingReturning> pageResult = baseMapper.selectByPage(page, username, jurisdiction);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public BorrowingReturningDto getBorrowingReturningDto(Long id, Long jurisdiction) {
        return baseMapper.getBorrowingReturningDto(id, jurisdiction);
    }

    @Override
    public BorrowingReturning getBorrowingReturningInfo(String openId, Long articleId) {
        return baseMapper.getBorrowingReturningInfo(openId, articleId);
    }

    @Override
    public List<BorrowingReturning> getBorrowingReturningDtoCount(Long articleId, String openId) {
        return baseMapper.getBorrowingReturningDtoCount(articleId, openId);
    }

    @Override
    public Object detailBorrowingReturning(Pageable pageable, String id, Long jurisdiction) {
        Page<BorrowingReturning> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<BorrowingReturning> pageResult = baseMapper.detailBorrowingReturning(page, id, jurisdiction);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public void exportLoanCount(HttpServletResponse res, String path, String username, Long jurisdiction) {

        String templateFilePath = path + File.separator + "borrowingReturning.xlsx";
        List<LoanCountDto> loanCountDtoList = baseMapper.exportLoanCount(username, jurisdiction);
        if (loanCountDtoList != null && loanCountDtoList.size() > 0) {
            ExcelExportUtil excel = new ExcelExportUtil();
            try {
                excel.writeData(templateFilePath, null, 0);
                List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
                for (int i = 0; i < loanCountDtoList.size(); i++) {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, loanCountDtoList.get(i).getUsername());
                    data.put(2, loanCountDtoList.get(i).getName());
                    data.put(3, loanCountDtoList.get(i).getNumber());
                    data.put(4, loanCountDtoList.get(i).getGetTime());
                    data.put(5, getState(
                            loanCountDtoList.get(i).getIsRepay(),
                            loanCountDtoList.get(i).getNumber(),
                            loanCountDtoList.get(i).getGiveNumber()));
                    data.put(6, loanCountDtoList.get(i).getGiveTime());
                    datalist.add(data);
                }
                String[] heads = new String[]{"A3", "B3", "C3", "D3", "E3", "F3"};
                excel.writeDateList(templateFilePath, heads, datalist, 0, res, "物品领用");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> addBorrowingReturning(BorrowingReturning borrowingReturning) {
        List<String> ids = new ArrayList<>();
        // 假设拼接字符串为 物品 articleId 借出数量 number  是否需要归还 isRepay
        String[] arrays = borrowingReturning.getCode().split(";");
        if (borrowingReturning.getCode().length() > 0) {
            borrowingReturning.setGetTime(System.currentTimeMillis());
            borrowingReturning.setCreateTime(System.currentTimeMillis());
            borrowingReturningService.save(borrowingReturning);
            // 获得主键
            Long pk = borrowingReturning.getId();

            for (String array : arrays) {
                BorrowingReturningDto borrowingReturningDto = borrowingReturningService.getBorrowingReturningDto(Long.parseLong(array.split(",")[0]), borrowingReturning.getJurisdiction());
                if (borrowingReturningDto != null) {
                    // 总借出数
                    long loanNumber = 0L;
                    if (borrowingReturningDto.getLoanNumber() != null && !"".equals(borrowingReturningDto.getLoanNumber())) {
                        loanNumber = Long.parseLong(borrowingReturningDto.getLoanNumber());
                    }

                    // 总数 - 总借出数 = 可以外借数
                    long count = Long.parseLong(borrowingReturningDto.getTotal()) - loanNumber;
                    if (count >= Long.parseLong(array.split(",")[1])) {
                        try {
                            if (!"0".equals(array.split(",")[2])) {
                                articleService.saveLoanCount(pk, array.split(",")[0], array.split(",")[1], "0");
                            } else {
                                articleService.saveLoanCount(pk, array.split(",")[0], array.split(",")[1], array.split(",")[1]);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        ids.add(array.split(",")[0]);
                        messageService.sendMsg(Long.valueOf(borrowingReturning.getUserId()),"borrowing",null,"领用失败，物品数量不足");
                        return ids;
                    }
                }
            }
            // 如果借用的只是不需归还 则添加归还时间
            if (articleService.checkBorrowingReturning(pk)) {
                BorrowingReturning bri = borrowingReturningService.getById(pk);
                bri.setRepayTime(System.currentTimeMillis());
                borrowingReturningService.updateById(bri);
            }
            if (ids.size() > 0) {
                borrowingReturningService.removeById(pk);
                articleService.delLCByBorrowingReturningId(pk);
            }

            BorrowingReturning bri = borrowingReturningService.getById(pk);

            if (bri != null) {
                List<LoanCountDto> loanCountDtoList = articleService.selectLoanC(bri.getId());
                if (loanCountDtoList != null && loanCountDtoList.size() > 0) {
                    for (LoanCountDto loanCountDto : loanCountDtoList) {
                        // 更改数量表
                        ArticleNumber articleNumber = articleNumberService.getById(loanCountDto.getId());
                        long num;
                        if (articleNumber.getLoanNumber() != null && !"".equals(articleNumber.getLoanNumber())) {
                            num = Long.parseLong(loanCountDto.getNumber()) + Long.parseLong(articleNumber.getLoanNumber());
                        } else {
                            num = Long.parseLong(loanCountDto.getNumber());
                        }
                        articleNumber.setLoanNumber(String.valueOf(num));
                        articleNumberService.updateById(articleNumber);
                    }
                }
            }
        }
        // 发送领用成功消息
        messageService.sendMsg(Long.valueOf(borrowingReturning.getUserId()),"borrowing",null,"领用成功");
        return ids;
    }

    private String getState(String isRepay, String number, String giveNumber) {
        if (!"0".equals(isRepay)) {
            if (giveNumber != null && !"".equals(giveNumber)) {
                Integer num = Integer.parseInt(number);
                Integer giveNum = Integer.parseInt(giveNumber);
                String msg = "";

                if (num - giveNum == 0) {
                    msg = "已归还";
                }
                if (num - giveNum == num) {
                    msg = "未归还";
                }

                if (num - giveNum > 0 && num - giveNum < num) {
                    msg = "部分归还";
                }

                return msg;
            } else {
                return "未归还";
            }
        } else {
            return "无需归还";
        }
    }

}
