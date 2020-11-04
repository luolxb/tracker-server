
package com.nts.iot.modules.miniApp.util;
/**
 * Created by dell on 2017/3/29.
 */

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Component
public class FileOperateUtil {

    @Autowired
    private Auth auth;

    @Value("${qiniu.bucketName}")
    private String bucketName;

    @Value("${qiniu.url}")
    private String url;

    public static String LOCAL_FILEDIR = null;
    public static String initLocalFilePath = null;
    public static String initUploadFilePath = null;
    public static String UPLOAD_FILEPATH = null;

    /**
     * 上传
     *
     * @throws IOException
     */
    public static String upload(MultipartFile mFile, String localPath, String uploadPath) throws IOException {
        initPath(localPath, uploadPath);
        if (mFile.getSize() != 0 && !"".equals(mFile.getName())) {
            initFilePath();
            write(mFile.getInputStream(), new FileOutputStream(initLocalFilePath));
        }
        return initUploadFilePath;
    }

    /**
     * 上传
     *
     * @throws IOException
     */
    public static String upload(InputStream in, String localPath, String uploadPath) throws IOException {
        initPath(localPath, uploadPath);
        initFilePath();
        write(in, new FileOutputStream(initLocalFilePath));
        return initUploadFilePath;
    }

    public String upload(MultipartFile file) throws IOException {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + "." + suffix;
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);

        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        Response response = uploadManager.put(file.getBytes(), fileName, auth.uploadToken(bucketName));

        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        String path = url + "/" + putRet.key;
        return path;
    }

    /**
     * path拼接file
     *
     * @return
     */
    private static void initFilePath() {
        UUID uuid = UUID.randomUUID();
        String imgName = String.valueOf(uuid) + String.valueOf(new Date().getTime()) + ".jpg";
        initUploadFilePath = UPLOAD_FILEPATH + imgName;
        initLocalFilePath = LOCAL_FILEDIR + imgName;
    }

    /**
     * 写入数据
     *
     * @param in
     * @param out
     * @throws IOException
     */
    private static void write(InputStream in, OutputStream out) throws IOException {
        try {
            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
            try {
                out.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * 初始化文件存放路径
     */
    private static void initPath(String localPath, String uploadPath) {
        File file = new File(localPath + File.separator + getDateFileName());
        createDir(file.getAbsoluteFile());
        FileOperateUtil.LOCAL_FILEDIR = localPath + File.separator + getDateFileName();
        FileOperateUtil.UPLOAD_FILEPATH = uploadPath + File.separator + getDateFileName();
    }


    /**
     * 如果文件夹不存在，创建文件夹
     *
     * @param file
     */
    private static void createDir(File file) {
        try {
            if (file.getParentFile().exists()) {
                file.mkdir();
            } else {
                createDir(file.getParentFile());
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得当前日期（用于创建目录）
     *
     * @return
     */
    private static String getDateFileName() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        String currentPath = String.valueOf(c.get(Calendar.YEAR))
                + File.separator + String.valueOf(c.get(Calendar.MONTH) + 1)
                + File.separator + String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + File.separator;
        return currentPath;
    }

}
