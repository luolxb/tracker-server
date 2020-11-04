package com.nts.iot.modules.miniApp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.model.Picture;

import java.util.List;

public interface PictureService extends IService<Picture> {
    /**
     * 添加工作日志上报 文件
     *
     * @param picture
     */
    void saveFile(Picture picture);

    List<Picture> getFileList(Long workDiaryId, String type);
}
