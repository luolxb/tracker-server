package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dao.DictDetailMapper;
import com.nts.iot.modules.system.dao.DictMapper;
import com.nts.iot.modules.system.dto.DictDetailDTO;
import com.nts.iot.modules.system.model.Dict;
import com.nts.iot.modules.system.model.DictDetail;
import com.nts.iot.modules.system.service.DictDetailService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author jie
* @date 2019-04-10
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictDetailServiceImpl extends ServiceImpl<DictDetailMapper, DictDetail> implements DictDetailService {


    @Autowired
    private DictMapper dictMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDetail create(DictDetailDTO resources) {
        DictDetail detail = new DictDetail();
        detail.setDictId(resources.getDictId());
        detail.setLabel(resources.getLabel());
        detail.setValue(resources.getValue());
        detail.setSort(resources.getSort());
        baseMapper.insert(detail);
        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictDetailDTO resources) {
        DictDetail detail = new DictDetail();
        detail.setId(resources.getId());
        detail.setDictId(resources.getDictId());
        detail.setLabel(resources.getLabel());
        detail.setValue(resources.getValue());
        detail.setSort(resources.getSort());
        baseMapper.updateById(detail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

    /**
     * queryAll
     *
     * @param dictDetail
     * @return
     */
    @Override
    public Object queryAll(DictDetailDTO dictDetail, Pageable pageable) {
        Page<DictDetail> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        Dict dict = findDict(dictDetail.getDictName());
        if (dict==null){
            throw new BadRequestException("do not have ID");
        }
        QueryWrapper<DictDetail> dictDetailWrapper = new QueryWrapper<>();
        dictDetailWrapper.eq("dict_id",dict.getId());
        if (StrUtil.isNotEmpty(dictDetail.getLabel())) {
            dictDetailWrapper.like("label",dictDetail.getLabel());
        }
        IPage<DictDetail> pageResult = baseMapper.selectPage(page, dictDetailWrapper);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public List<DictDetail>  queryAll(DictDetailDTO dictDetail) {
        Dict dict = findDict(dictDetail.getDictName());
        if (dict==null){
            throw new BadRequestException("do not have ID");
        }
        QueryWrapper<DictDetail> dictDetailWrapper = new QueryWrapper<>();
        dictDetailWrapper.eq("dict_id",dict.getId());
        List<DictDetail> pageResult = baseMapper.selectList(dictDetailWrapper);
        return pageResult;
    }

    // 根据字典名查询字典字段
    @Override
    public Dict findDict(String dictName){
        QueryWrapper<Dict> dictWrapper = new QueryWrapper<>();
        dictWrapper.eq("name",dictName);
        List<Dict> dictList = dictMapper.selectList(dictWrapper);
        if (dictList ==null || dictList.size() == 0){
            return null;
        }else {
            return dictList.get(0);
        }
    }



}