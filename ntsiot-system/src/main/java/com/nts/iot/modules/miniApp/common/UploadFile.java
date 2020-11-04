package com.nts.iot.modules.miniApp.common;

import com.nts.iot.modules.miniApp.util.FileOperateUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/uploadFile")
@Log
public class UploadFile {

    @Value("${upload.uploadPath}")
    private String uploadPath;

    @Autowired
    private FileOperateUtil fileOperateUtil;

    /**
     * @param file
     * @return 上传成功返回“success”，上传失败返回“error”
     * @throws IOException
     * @createtime 2017年8月20日17:15:41
     */
    @PostMapping("/img")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        String url;
        try {
            // 上传到本地tomcat后显示图片的路径
            System.out.println("============图片上传=============");
            url = fileOperateUtil.upload(file);
            System.out.println("url = " + url);
        } catch (Exception e) {
            url = "ERROR";
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 上传视频和音频
     *
     * @param file
     * @return
     */
    @PostMapping("/video")
    public String video(@RequestParam(value = "file", required = false) MultipartFile file) {
        String url;
        try {
            System.out.println("============视频上传=============");
            url = fileOperateUtil.upload(file);
            System.out.println("url = " + url);
        } catch (Exception e) {
            url = "ERROR";
            log.info("==================上传失败================");
            e.printStackTrace();
        }
        return url;
    }

}
