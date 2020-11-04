package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.miniApp.common.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api")
public class PicturesController {

    @Autowired
    private UploadFile uploadFile;

    /**
     * @param file
     * @return 上传成功返回“success”，上传失败返回“error”
     * @throws IOException
     * @createtime 2017年8月20日17:15:41
     */
    @PostMapping("/pictures")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        String url;
        try {
            url = uploadFile.upload(file);
            System.out.println("url = " + url);
        } catch (Exception e) {
            url = "ERROR";
            e.printStackTrace();
        }
        return url;
    }
}
