package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.miniApp.dto.HoseOwnerInfoDto;
import com.nts.iot.modules.miniApp.dto.HoseTenantDto;
import com.nts.iot.modules.miniApp.dto.IllegalStatisticsDTO;
import com.nts.iot.modules.miniApp.dto.Serie;
import com.nts.iot.modules.system.dao.HousingOwnerRecordMapper;
import com.nts.iot.modules.system.dto.HouseRecordDTO;
import com.nts.iot.modules.system.model.HousingOwnerRecord;
import com.nts.iot.modules.system.model.HousingTenantRecord;
import com.nts.iot.modules.system.service.HouseRecordSerivice;
import com.nts.iot.modules.system.service.HousingTenantRecordService;
import com.nts.iot.util.ExcelExportUtil;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/9/16 15:12
 * @Description:
 */
@Service
public class HouseRecordSeriviceImpl extends ServiceImpl<HousingOwnerRecordMapper, HousingOwnerRecord> implements HouseRecordSerivice {

    @Autowired
    HousingTenantRecordService housingTenantRecordService;

    @Override
    public Map queryAll(String owner, String phone, List<String> depts, Pageable pageable) {
        Page<HouseRecordDTO> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<HouseRecordDTO> pageResult = baseMapper.selectByPage(page, owner, phone, depts);
        return PageUtil.toPage(pageResult);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveForm(HoseOwnerInfoDto formData) {
        HousingOwnerRecord newRecord = new HousingOwnerRecord();
        // newRecord.setId();
        newRecord.setOwner(formData.getName());
        newRecord.setOwnerPhone(formData.getPhone());
        if (formData.getDeptId()!=null){
            newRecord.setDeptId(Long.valueOf(formData.getDeptId()));
        }
        newRecord.setAddress(formData.getAddress());
        newRecord.setCrateTime(System.currentTimeMillis());
        // 插入数据库
        if (this.save(newRecord)){
            if (formData.getTenantList()!=null && formData.getTenantList().size() > 0){
                Long houseRecordId = newRecord.getId();
                List<HousingTenantRecord> tenantRecords = new ArrayList<>();
                for (HoseTenantDto hoseTenantDto :formData.getTenantList()){
                    HousingTenantRecord housingTenantRecord = new HousingTenantRecord();
//                    housingTenantRecord.setId();
                    housingTenantRecord.setTenant(hoseTenantDto.getName());
                    housingTenantRecord.setTenantPhone(hoseTenantDto.getPhone());
                    housingTenantRecord.setTenantIdcard(hoseTenantDto.getIdCard());
                    housingTenantRecord.setHouseId(houseRecordId);
                    housingTenantRecord.setCrateTime(System.currentTimeMillis());
                    tenantRecords.add(housingTenantRecord);
                }
                housingTenantRecordService.saveBatch(tenantRecords);
            }
        }
    }

    @Override
    public Map<String, Object> getStatisticsByType(Long startTime, Long endTime, List<String> depts, String dateType) {
        Map<String, Object> result = new HashMap<>();

        List<String> xAxis = new ArrayList<>();
        List<String> legend = new ArrayList<>();
        List<Serie> series = new ArrayList<>();
        List<String> data = new ArrayList<>();

        legend.add("备案数量");
        Serie serie = new Serie();

        List<IllegalStatisticsDTO> list = baseMapper.getStatisticsByType(startTime, endTime, depts, dateType);
        for (IllegalStatisticsDTO dto : list) {
            // 日期
            xAxis.add(dto.getTime());
            // 备案数
            data.add(dto.getCnt());
        }
        serie.setType("bar");
        serie.setName("备案数量");
        serie.setData(data);
        series.add(serie);

        result.put("legend", legend);
        result.put("series", series);
        result.put("xAxis", xAxis);

        return result;
    }

    @Override
    public void exportHousingRentalReport(HttpServletResponse res, String path, String owner, String phone, List<String> depts) {
        List<HouseRecordDTO> list = baseMapper.exportHousingRentalReport(owner, phone, depts);
        String templateFilePath = path + File.separator + "housingRentalReport.xlsx";
        if (list != null && list.size() > 0) {
            ExcelExportUtil excel = new ExcelExportUtil();
            try {
                excel.writeData(templateFilePath, null, 0);
                List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
                for (HouseRecordDTO dto : list) {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, dto.getOwner());
                    data.put(2, dto.getOwnerPhone());
                    data.put(3, dto.getAddress());
                    data.put(4, dto.getTenant());
                    data.put(5, dto.getTenantPhone());
                    data.put(6, dto.getTenantIdcard());
                    data.put(7, dto.getInputTime());
                    datalist.add(data);
                }
                String[] heads = new String[]{"A2", "B2", "C2", "D2", "E2", "F2", "G2"};
                excel.writeDateList(templateFilePath, heads, datalist, 0, res, "房屋备案记录");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
