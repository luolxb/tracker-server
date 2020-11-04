package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.IconDto;
import com.nts.iot.modules.system.model.CheckPoint;
import com.nts.iot.modules.system.model.TaskPoint;
import com.nts.iot.modules.system.model.DictDetail;
import com.nts.iot.modules.system.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CheckPointService extends IService<CheckPoint> {

    /**
     * 查询所有必到点分页
     *
     * @param name
     * @param pageable
     * @return
     */
    Object queryAll(String name, Pageable pageable,List<Long> jurisdiction);

    /**
     * create
     *
     * @param checkPoint
     * @return
     */
    Integer create(CheckPoint checkPoint, User user);

    /**
     * update
     *
     * @param checkPoint
     */
    void update(CheckPoint checkPoint, User user);

    /**
     * delete
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 获得所有必到点信息
     *
     * @return
     */
    void getCheckPointAll();

    List<TaskPoint> getCheckPointsByDept(Long deptId);
//    List<CheckPoint> getCheckPointsByDept(Long deptId);


    /**
     * 查询图标
     */
    List<IconDto> selectIcon(Long jurisdiction);


    /**
     * 查询字典
     */
    List<DictDetail> selectTasKType();
}
