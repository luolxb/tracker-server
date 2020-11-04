package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.NewDict;
import com.nts.iot.modules.system.model.vo.NewDictRq;
import com.nts.iot.modules.system.model.vo.NewDictVo;

import java.util.List;

/**
 * @PackageName: com.nts.iot.modules.system.service
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-07 15:11
 **/
public interface NewDictService extends IService<NewDict> {

    List<NewDict> newDict(Long pId);
}
