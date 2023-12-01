package com.example.talk.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * {@code @author:} iumyxF
 **/
@RestController
@RequestMapping("/image")
public class ImageUpload {

    @Value("${upload.filePath}")
    private String basePath;

    @PostMapping("/upload")
    public BaseResponse<String> upload(MultipartFile file) {
        //获取与原来文件名
        String filename = file.getOriginalFilename();
        //截取文件结尾格式
        String tail = null;
        if (filename != null) {
            tail = filename.substring(filename.lastIndexOf("."));
        }
        //使用uuid组装新文件名
        String random = UUID.randomUUID().toString();
        //将文件转存在设置的地址
        String newName = random + tail;
        //创建一个目录对象
        File dir = new File(basePath);
        //如果文件夹不存在则创建文件夹
        if (!dir.exists()) {
            boolean mkdir = dir.mkdir();
            if (!mkdir) {
                return ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }
        }
        try {
            file.transferTo(new File(basePath + newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtils.success(newName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try (FileInputStream inputStream = new FileInputStream(basePath + name);
             ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("image/jpeg");
            byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) != -1) {
                outputStream.write(bytes);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
