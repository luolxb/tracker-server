package com.nts.iot.modules.system.rest;


import com.nts.iot.modules.miniApp.model.InternalInformation;
import com.nts.iot.modules.miniApp.model.Picture;
import com.nts.iot.modules.miniApp.service.InternalInformationService;
import com.nts.iot.modules.miniApp.service.PictureService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.config.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *  内部信息上报
 */
@RestController
@RequestMapping("api")
public class InternalInformationController {

    @Autowired
    private InternalInformationService internalInformationService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private DataScope dataScope;

    /**
     * 查看工作日志列表
     *
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     */
    @Log("工作日志查询")
    @GetMapping("/internalInformation/list")
    @PreAuthorize("hasAnyRole('ADMIN','INTERNAL_INFORMATION_ALL','INTERNAL_INFORMATION_SELECT')")
    public ResponseEntity queryAll(String startTime, String endTime, Pageable pageable) {
        Long jurisdiction = null;
        // 数据权限
        List<Long> list = new ArrayList(dataScope.getDeptIds());
//        if (list != null && list.size() > 0) {
//            jurisdiction = list.get(0);
//        }
        return new ResponseEntity(internalInformationService.queryAll(pageable, startTime, endTime,list), HttpStatus.OK);
    }

    /**
     * 查询日志详细
     *
     * @return
     */
    @Log("查询日志详细")
    @GetMapping(value = "/internalInformation/findById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','INTERNAL_INFORMATION_ALL','INTERNAL_INFORMATION_DETAIL')")
    public ResponseEntity getById(@PathVariable Long id) {
        InternalInformation internalInformation = internalInformationService.getById(id);
        if (internalInformation != null) {
            // 图片
            List<Picture> pictureList = pictureService.getFileList(id, "INTERNAL_INFORMATION_IMG");
            if (pictureList != null && pictureList.size() > 0) {
                List<String> pathList = new ArrayList<>();
                for (int i = 0; i < pictureList.size(); i++) {
                    pathList.add(pictureList.get(i).getPath());
                }
                internalInformation.setSourceData(pathList);
            }


            // 视频
            List<Picture> videoList = pictureService.getFileList(id, "INTERNAL_INFORMATION_VIDEO");
            if (videoList != null && videoList.size() > 0) {
                List<String> pathList = new ArrayList<>();
                for (int i = 0; i < videoList.size(); i++) {
                    pathList.add(videoList.get(i).getPath());
                }
                internalInformation.setVideoList(pathList);
            }
        }

        return new ResponseEntity(internalInformation, HttpStatus.OK);
    }
}
