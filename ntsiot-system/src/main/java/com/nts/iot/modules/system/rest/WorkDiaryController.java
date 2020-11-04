package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.miniApp.model.Picture;
import com.nts.iot.modules.miniApp.model.WorkDiary;
import com.nts.iot.modules.miniApp.service.PictureService;
import com.nts.iot.modules.miniApp.service.WorkDiaryService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.config.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作日志
 */
@RestController
@RequestMapping("api")
public class WorkDiaryController {
    @Autowired
    private WorkDiaryService workDiaryService;

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
    @GetMapping("/workDiary/list")
    public ResponseEntity queryAll(String startTime, String endTime, Pageable pageable) {
        // 数据权限
        List<Long> list = new ArrayList(dataScope.getDeptIds());
        return new ResponseEntity(workDiaryService.queryAll(pageable, startTime, endTime,list), HttpStatus.OK);
    }

    /**
     * 查询日志详细
     *
     * @return
     */
    @Log("查询日志详细")
    @GetMapping(value = "/workDiary/findById/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        WorkDiary workDiary = workDiaryService.getById(id);
        if (workDiary != null) {
            // 图片
            List<Picture> pictureList = pictureService.getFileList(id, "WORK_DIARY_IMG");
            if (pictureList != null && pictureList.size() > 0) {
                List<String> pathList = new ArrayList<>();
                for (int i = 0; i < pictureList.size(); i++) {
                    pathList.add(pictureList.get(i).getPath());
                }
                workDiary.setSourceData(pathList);
            }

            // 视频
            List<Picture> videoList = pictureService.getFileList(id, "WORK_DIARY_VIDEO");
            if (videoList != null && videoList.size() > 0) {
                List<String> vdList = new ArrayList<>();
                for (int i = 0; i < videoList.size(); i++) {
                    vdList.add(videoList.get(i).getPath());
                }
                workDiary.setVideoList(vdList);
            }

            // 音频
            List<Picture> mp3List = pictureService.getFileList(id, "WORK_DIARY_MP3");
            if (mp3List != null && mp3List.size() == 1) {
                workDiary.setFrequency(mp3List.get(0).getPath());
            }
        }

        return new ResponseEntity(workDiary, HttpStatus.OK);
    }
}
