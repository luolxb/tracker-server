package com.nts.iot.modules.miniApp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.miniApp.dao.PictureMapper;
import com.nts.iot.modules.miniApp.model.Picture;
import com.nts.iot.modules.miniApp.service.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {
    /**
     * 添加文件
     *
     * @param picture
     */
    @Override
    public void saveFile(Picture picture) {
        baseMapper.insert(picture);
    }

    /**
     * 查询文件
     * @param workDiaryId
     * @param type
     * @return
     */
    @Override
    public List<Picture> getFileList(Long workDiaryId, String type) {
        return baseMapper.getPictureList(workDiaryId, type);
    }
}
